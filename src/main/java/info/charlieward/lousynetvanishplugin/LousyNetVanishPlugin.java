package info.charlieward.lousynetvanishplugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import info.charlieward.lousynetvanishplugin.commands.vanishCommand;
import info.charlieward.lousynetvanishplugin.files.CustomConfig;
import info.charlieward.lousynetvanishplugin.listeners.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import redis.clients.jedis.Jedis;

public final class LousyNetVanishPlugin extends JavaPlugin {

    public Jedis jedis = new Jedis();

    private static LousyNetVanishPlugin plugin;
    public String servername;

    @Override
    public void onEnable() {

        plugin = this;

        getConfig().options(). copyDefaults();
        saveDefaultConfig();

        CustomConfig.setup();
        CustomConfig.get().addDefault("Choose the gamemode when leaving vanish", "adventure,survival,creative");
        CustomConfig.get().addDefault("gamemode","");
        CustomConfig.get().options().copyDefaults(true);
        CustomConfig.save();

        getLogger().info("LousyNet-VanishPlugin v." + this.getDescription().getVersion() + " has loaded.");


        jedis.set("VanishPlayers", "");


        getCommand("vanish").setExecutor(new vanishCommand(this));

        getServer().getPluginManager().registerEvents(new playerJoin(this), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("LousyNet-VanishPlugin v." + this.getDescription().getVersion() + " has been disabled.");

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public static LousyNetVanishPlugin getPlugin() {
        return plugin;
    }
}
