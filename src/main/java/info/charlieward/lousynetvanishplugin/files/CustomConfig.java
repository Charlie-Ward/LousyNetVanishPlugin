package info.charlieward.lousynetvanishplugin.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private static File file;
    private static FileConfiguration customFile;
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("LousyNet-PlayerCount-Utils").getDataFolder(), "lousynet-playercount-utils-config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e){
                System.out.println("ERROR - LousyNet-PlayerCount-Utils: Failed to create config file");
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get() {
        return customFile;
    }
    public static void save() {
        try {
            customFile.save(file);
            System.out.println("LousyNet-PlayerCount-Utils: Config File Saved");
        } catch (IOException e) {
            System.out.println("ERROR - LousyNet-PlayerCount-Utils: Failed to save config file");
        }
    }
    public static void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
