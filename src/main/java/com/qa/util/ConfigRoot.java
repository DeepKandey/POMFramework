package com.qa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigRoot {

    public Boolean configFileExists(String fileToRead) {
        String currentDirectory = System.getProperty("user.dir");
        try (InputStream inputStream = new FileInputStream(currentDirectory + "/src/test/resources/" + fileToRead)) {
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String readFromConfig(String fileToRead, String propToRead) {
        String propToReturn = "";
        String currentDirectory = System.getProperty("user.dir");
        try (InputStream inputStream = new FileInputStream(currentDirectory + "/src/test/resources/" + fileToRead)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            propToReturn = properties.getProperty(propToRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propToReturn;
    }
}
