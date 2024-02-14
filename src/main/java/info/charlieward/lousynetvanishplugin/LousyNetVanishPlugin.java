package info.charlieward.lousynetvanishplugin;

import info.charlieward.lousynetvanishplugin.commands.vanishCommand;
import info.charlieward.lousynetvanishplugin.listeners.playerJoin;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

public final class LousyNetVanishPlugin extends JavaPlugin {

    public Jedis jedis = new Jedis();

    @Override
    public void onEnable() {
        getLogger().info("LousyNet-VanishPlugin v." + this.getDescription().getVersion() + " has loaded.");
        jedis.set("VanishPlayers", "");

        getCommand("vanish").setExecutor(new vanishCommand(this));

        getServer().getPluginManager().registerEvents(new playerJoin(this), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("LousyNet-VanishPlugin v." + this.getDescription().getVersion() + " has been disabled.");
    }
}
