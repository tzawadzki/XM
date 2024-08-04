package com.xm.service;

import java.io.InputStream;

public class TestUtils {

    public static final String FILE_BTC = "files/BTC_values.csv";
    public static final String FILE_DOGE = "files/DOGE_values.csv";
    public static final String FILE_ETH = "files/ETH_values.csv";
    public static final String FILE_LTC = "files/LTC_values.csv";
    public static final String FILE_XRP = "files/XRP_values.csv";

    public static InputStream getFileInputStream(String fileName) {
        return TestUtils.class.getClassLoader().getResourceAsStream(fileName);
    }
}
