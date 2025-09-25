
package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Singleton configuration for the app (demonstrates Singleton pattern).
 */
public final class AppConfig {
    private static AppConfig instance;
    private Path dataFolder;

    private AppConfig() {
        this.dataFolder = Paths.get("./data");
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public Path getDataFolder() { return dataFolder; }
    public void setDataFolder(Path p) { this.dataFolder = p; }
}
