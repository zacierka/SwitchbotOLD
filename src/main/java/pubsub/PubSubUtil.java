package pubsub;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PubSubUtil {

    /**
     * Function to get Redis server password from properties file
     * @return Retrieved Password
     */
    public static String getPassword()
    {
        String pass = null;
        try (InputStream input = PubSubUtil.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            pass = prop.getProperty("SECRET_PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pass;
    }
}
