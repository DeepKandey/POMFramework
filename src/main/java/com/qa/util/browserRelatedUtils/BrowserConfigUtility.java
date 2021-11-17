package com.qa.util.browserRelatedUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BrowserConfigUtility {

    private String browserVersion;
    private HashMap<String, String> browserOptionsMap;
    private String browserType;
    private JSONObject localConfig;
    private HashMap<String, HashMap<String, String>> experimentalOptions;

    String extension = "";
    String currentDirectory = System.getProperty("user.dir");

    public BrowserConfigUtility(String browser) throws IOException, ParseException {
        browserType = browser;
        browserOptionsMap = new HashMap<>();
        experimentalOptions = new HashMap<String, HashMap<String, String>>();
        setLocalConfig();
        parseBrowserOptionsAndVersion();
    }

    private HashMap<String, String> getBrowserOptionsMap() {
        return browserOptionsMap;
    }

    private HashMap<String, HashMap<String, String>> getExperimentalOptions() {
        return experimentalOptions;
    }


    private void setLocalConfig() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        localConfig = (JSONObject)
                parser.parse((new FileReader(currentDirectory + "/src/test/resources/localBrowserConfig.json")));
    }

    private void parseBrowserOptionsAndVersion() {
        JSONArray configBrowsers = (JSONArray) localConfig.get("browsers");

        for (Object configBrowser : configBrowsers) {
            if (((JSONObject) configBrowser).get("browser").equals(browserType)) {
                JSONObject browserOptions = (JSONObject) ((JSONObject) configBrowser).get("options");
                if (browserOptions != null) {
                    for (Object o : browserOptions.entrySet()) {
                        Map.Entry<String, String> pair = (Map.Entry<String, String>) o;
                        browserOptionsMap.put(pair.getKey(), pair.getValue());
                    }
                }
                browserVersion = ((JSONObject) configBrowser).get("browser_version").toString();

                if (((JSONObject) configBrowser).get("experimentalOptions") != null) {
                    JSONObject experimentalOptionsJsonObj = (JSONObject) ((JSONObject) configBrowser).get("experimentalOptions");
                    for (Object o : experimentalOptionsJsonObj.entrySet()) {
                        Map.Entry<Object, Object> pair = (Map.Entry<Object, Object>) o;
                        JSONObject optionsJson = (JSONObject) pair.getValue();
                        HashMap<String, String> expOptionsValueMap = new HashMap<>();
                        if (optionsJson != null) {
                            for (Object o1 : optionsJson.entrySet()) {
                                Map.Entry<String, String> dataPair = (Map.Entry<String, String>) o1;
                                experimentalOptionsJsonObj.put(dataPair.getKey(), dataPair.getValue());
                            }
                        }
                        experimentalOptions.put(((Map.Entry<String, String>) o).getKey(), expOptionsValueMap);
                    }
                }
                break;
            }
        }
    }

    public MutableCapabilities getOptions() {
        MutableCapabilities options = null;
        Iterator it;
        switch (browserType.toUpperCase()) {
            case "FIREFOX" -> {
                options = new FirefoxOptions();
                extension = getSpecialDriverExtension();
                System.setProperty("webdriver.gecko.driver", currentDirectory + "/webdriver/geckodriver" + extension);
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    ((FirefoxOptions) options).addArguments(pair.getValue().toString());
                }
            }
            case "CHROME" -> {
                options = new ChromeOptions();
                extension = getSpecialDriverExtension();
                System.setProperty("webdriver.chrome.driver", currentDirectory + "/webdriver/chromedriver" + extension);
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    ((ChromeOptions) options).addArguments(pair.getValue().toString());
                }

                Iterator<Map.Entry<String, HashMap<String, String>>> iterator = getExperimentalOptions().entrySet().iterator();
                while (iterator.hasNext()) {
                    ((ChromeOptions) options).setExperimentalOption(iterator.next().getKey(), iterator.next().getValue());
                }
            }
            case "SAFARI" -> {
                options = new SafariOptions();
                extension = getSpecialDriverExtension();
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    options.setCapability(pair.getKey().toString(), pair.getValue().toString());
                }
            }
            case "Edge" -> {
                options = new EdgeOptions();
                extension = getSpecialDriverExtension();
                System.setProperty("webdriver.edge.driver", currentDirectory + "/webdriver/msedgedriver" + extension);
            }
            case "IE" -> {
                options = new InternetExplorerOptions();
                extension = getSpecialDriverExtension();
                System.setProperty("webdriver.ie.driver", currentDirectory + "/webdriver/IEDriverServer" + extension);
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (pair.getValue().toString().equalsIgnoreCase("true")
                            || pair.getValue().toString().equalsIgnoreCase("false")) {
                        options.setCapability(pair.getKey().toString(), Boolean.parseBoolean(pair.getValue().toString()));
                    } else {
                        options.setCapability(pair.getKey().toString(), pair.getValue().toString());
                    }
                }
            }
        }
        return options;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public static String getSpecialDriverExtension() {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.contains("win")) {
            return ".exe";
        } else return "";
    }
}
