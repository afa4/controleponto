package com.example.controleponto.util;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SqlReaderUtil {

    public static String read(String sqlName) {
        var inputStream = ClassLoader.getSystemResourceAsStream("sql/" + sqlName);

        if (inputStream != null) {
            try {
                var bytes = inputStream.readAllBytes();
                return new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException ignored) { }
        }
        throw new RuntimeException();
    }
}
