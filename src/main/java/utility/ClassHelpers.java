package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ClassHelpers {

    public static final String CHECKMARK_EMOTE = "U+2705"; // CHECK
    public static final String CROSSMARK_EMOTE = "U+274C"; // X MARK

    public static String getProperty(String property)
    {
        Properties config = getPropertiesFile();

        return config.getProperty(property);
    }

    public static Properties getPropertiesFile() {
        Properties prop = null;
        try (InputStream input = ClassHelpers.class.getClassLoader().getResourceAsStream("config.properties")) {

            prop = new Properties();
            // load a properties file
            prop.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }



}
