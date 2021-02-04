package com.example.controleponto.util;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SqlReaderUtil {

    public static String read(String sqlName) throws IOException {
        var inputStream = ClassLoader.getSystemResourceAsStream("sql/" + sqlName);

        if (inputStream != null) {
            var bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        }
        throw new IOException("Null Input Stream");
    }
}
