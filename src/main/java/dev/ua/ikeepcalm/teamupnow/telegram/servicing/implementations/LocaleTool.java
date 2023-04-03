package dev.ua.ikeepcalm.teamupnow.telegram.servicing.implementations;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LocaleTool {
    private final Properties properties;

    public LocaleTool(String propertiesFileName) {
        properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage(String key) {
        return properties.getProperty(key);
    }
}
