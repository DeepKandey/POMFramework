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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class BrowserConfigUtility {

    private String browserVersion;
    private final HashMap<String, String> browserOptionsMap;
    private final String browserType;
    private JSONObject localConfig;
    private final HashMap<String, HashMap<String, String>> experimentalOptions;

    public BrowserConfigUtility(String browser) throws IOException, ParseException {
        browserType = browser;
        browserOptionsMap = new HashMap<>();
        experimentalOptions = new HashMap<>();
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
                parser.parse(new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/localBrowserConfig.json")))));
    }
    @SuppressWarnings("unchecked")
    private void parseBrowserOptionsAndVersion() {
        JSONArray configBrowsers = (JSONArray) localConfig.get("browsers");

        for (Object configBrowser : configBrowsers) {
            if (((JSONObject) configBrowser).get("browser").toString().equalsIgnoreCase(browserType)) {
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
        Iterator<Map.Entry<String, String>> it;

        switch (browserType.toUpperCase()) {
            case "FIREFOX" -> {
                options = new FirefoxOptions();
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pair = it.next();
                    ((FirefoxOptions) options).addArguments(pair.getValue());
                }
            }
            case "CHROME" -> {
                options = new ChromeOptions();
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pair = it.next();
                    ((ChromeOptions) options).addArguments(pair.getValue());
                }

                Iterator<Map.Entry<String, HashMap<String, String>>> iterator = getExperimentalOptions().entrySet().iterator();
                while (iterator.hasNext()) {
                    ((ChromeOptions) options).setExperimentalOption(iterator.next().getKey(), iterator.next().getValue());
                }
            }
            case "SAFARI" -> {
                options = new SafariOptions();
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pair = it.next();
                    options.setCapability(pair.getKey(), pair.getValue());
                }
            }
            case "EDGE" -> options = new EdgeOptions();
            case "IE" -> {
                options = new InternetExplorerOptions();
                it = getBrowserOptionsMap().entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pair = it.next();
                    if (pair.getValue().equalsIgnoreCase("true")
                            || pair.getValue().equalsIgnoreCase("false")) {
                        options.setCapability(pair.getKey(), Boolean.parseBoolean(pair.getValue()));
                    } else {
                        options.setCapability(pair.getKey(), pair.getValue());
                    }
                }
            }
        }
        return options;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

}
