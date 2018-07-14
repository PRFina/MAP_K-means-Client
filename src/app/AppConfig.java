package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load a configuration file which contains client settings.
 */
class AppConfig {

    private Properties settings;

    /**
     * Construct a new instance with the specified .properties file.
     *
     * <p>
     *     The loading mechanism looks for a custom .property file to load in the
     * current working directory, if no custom file is provided
     * the default one distributed in the .jar archive will be loaded
     * </p>
     *
     * @param fileName the name of the configuration file
     */
    AppConfig(String fileName) throws IOException {
        settings = new Properties();

        //try to load custom config.properties
        try (InputStream customFile = new FileInputStream(fileName)) {
            settings.load(customFile);

        } catch (IOException e) {
                e.printStackTrace();
                // try to load default config.properties
                InputStream in = getClass()
                        .getClassLoader()
                        .getResourceAsStream(fileName);

                settings.load(in);
                }

        }

    /**
     * Retrieve the current property value.
     *
     * @param name name of the property declared int the config file.
     * @return value of the property declared int the config file.
     */
    String getSetting(String name) {
        return settings.getProperty(name);
    }

}
