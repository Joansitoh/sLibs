package club.skilldevs.utils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

@Getter
public class FileConfig {

    @Getter
    private static final List<FileConfig> fileConfigs = new ArrayList<>();

    private final File file;

    private String header;
    private FileConfiguration configuration;

    private boolean updateHeader;

    public static void reloadAllConfig() {
        fileConfigs.forEach(FileConfig::reloadConfig);
    }

    public FileConfig(JavaPlugin plugin, String fileName) {
        this.file = new File(plugin.getDataFolder(), fileName);
        this.updateHeader = false;

        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            if (plugin.getResource(fileName) == null) {
                try {
                    this.file.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().warning("[" + plugin.getName() + "] (sLibs) Failed to create new file " + fileName);
                }
            } else {
                plugin.saveResource(fileName, false);
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.file);
        fileConfigs.add(this);
    }


    public void setDefaultIfNotSet(String path, Object value) {
        if (!configuration.contains(path)) configuration.set(path, value);
    }

    public void reloadConfig() {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public void setHeader(String... header) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        StringBuilder builder = new StringBuilder();

        for (int x = 0; x < header.length; x++) {
            builder.append(header[x]);
            if (x + 1 != header.length) builder.append("\n");
        }

        yaml.options().header(builder.toString());

        try { yaml.save(file); } catch (IOException e) { e.printStackTrace(); }
        this.header = builder.toString();
    }

    public ConfigurationSection getSection(String path) {
        if (configuration.isConfigurationSection(path)) return configuration.getConfigurationSection(path);
        return configuration.createSection(path);
    }

    public double getDouble(String path) {
        if (configuration.contains(path)) return configuration.getDouble(path);
        return 0;
    }

    public int getInt(String path) {
        if (configuration.contains(path)) return configuration.getInt(path);
        return 0;
    }

    public boolean getBoolean(String path) {
        if (configuration.contains(path)) return configuration.getBoolean(path);
        return false;
    }

    public long getLong(String path) {
        if (configuration.contains(path)) return configuration.getLong(path);
        return 0L;
    }

    public String getString(String path) {
        if (configuration.contains(path))
            return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
        return null;
    }

    public String getString(String path, String callback, boolean colorize) {
        if (configuration.contains(path)) {
            if (colorize) return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
            else return configuration.getString(path);
        }
        return callback;
    }

    public List<String> getReversedStringList(String path) {
        List<String> list = getStringList(path);
        if (list != null) {
            int size = list.size();
            List<String> toReturn = new ArrayList<>();
            for (int i = size - 1; i >= 0; i--) toReturn.add(list.get(i));
            return toReturn;
        }

        return Collections.singletonList("ERROR: STRING LIST NOT FOUND!");
    }

    public List<String> getStringList(String path) {
        if (configuration.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : configuration.getStringList(path))
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            return strings;
        }

        return Collections.singletonList("ERROR: STRING LIST NOT FOUND!");
    }

    public List<String> getStringListOrDefault(String path, List<String> toReturn) {
        if (configuration.contains(path)) {
            ArrayList<String> strings = new ArrayList<>();
            for (String string : configuration.getStringList(path))
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            return strings;
        }
        return toReturn;
    }

    public void save() {
        try {
            this.configuration.save(this.file);
            if (updateHeader && header != null) setHeader(header);
        } catch (IOException e) {
            Bukkit.getLogger().warning("[" + sLoader.INSTANCE.getName() + "] (sLibs) &cCould not save config file " + this.file.toString());
        }
    }
}
