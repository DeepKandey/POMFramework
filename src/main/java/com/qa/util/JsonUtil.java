package com.qa.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtil {

  public static JSONObject getTestClassJsonObject(String filePath) {
    try {
      JSONParser parser = new JSONParser();
      return (JSONObject) parser.parse(new FileReader(filePath));
    } catch (FileNotFoundException e) {
      LoggerUtil.log("JSON file not found at location" + filePath);
    } catch (IOException e) {
      LoggerUtil.log("Unable to find json file: " + filePath);
    } catch (ParseException e) {
      LoggerUtil.log("Unable to parse json file: " + filePath);
    }
    return null;
  }
}
