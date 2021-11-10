package com.qa.util;

import java.io.IOException;
import java.net.*;

public class HttpConnectUtil {

  public static int getResponseCode(String address) throws IOException {
    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(address).openConnection();
    httpURLConnection.setConnectTimeout(8000);
    return httpURLConnection.getResponseCode();
  }
}
