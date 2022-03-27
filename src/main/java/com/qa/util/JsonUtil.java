package com.qa.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtil {

  public static JSONObject getTestClassJsonObject(String filePath) {
    LoggerUtil loggerUtil = new LoggerUtil(JsonUtil.class);
    try {
      JSONParser parser = new JSONParser();
      return (JSONObject) parser.parse(new FileReader(filePath));
    } catch (FileNotFoundException e) {
      loggerUtil.error("JSON file not found at location" + filePath);
    } catch (IOException e) {
      loggerUtil.error("Unable to find json file: " + filePath);
    } catch (ParseException e) {
      loggerUtil.error("Unable to parse json file: " + filePath);
    }
    return null;
  }
}
