import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class AppConfig {

    private Properties settings;

    AppConfig(String fileName) {
        settings = new Properties();

        //try to load custom config.properties
        try (InputStream customFile = new FileInputStream(fileName)) {
            settings.load(customFile);

        } catch (IOException e) {
            e.printStackTrace();
            // try to load default config.properties
            try (InputStream in = getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName)) {

                settings.load(in);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    String getSetting(String name) {
        return settings.getProperty(name);
    }

}
