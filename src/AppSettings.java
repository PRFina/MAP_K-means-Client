import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class AppSettings {

    private Properties settings;

    AppSettings(String fileName) {
        settings = new Properties();

        try (FileInputStream in = new FileInputStream(fileName)) {

            settings.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String getSetting(String name) {
        return settings.getProperty(name);
    }
}
