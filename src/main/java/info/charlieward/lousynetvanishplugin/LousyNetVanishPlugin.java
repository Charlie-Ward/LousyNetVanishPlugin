package info.charlieward.lousynetvanishplugin;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import info.charlieward.lousynetvanishplugin.commands.vanishCommand;
import info.charlieward.lousynetvanishplugin.listeners.playerJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public final class LousyNetVanishPlugin extends JavaPlugin implements PluginMessageListener {

    public Jedis jedis = new Jedis();
    private static LousyNetVanishPlugin plugin;

    public String servername;

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        getLogger().info("LousyNet-VanishPlugin v." + this.getDescription().getVersion() + " has loaded.");
        jedis.set("VanishPlayers", "");

        getServerName();
        System.out.println(servername);

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

    public static void getServerName() {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("GetServer");
            Player player = (Player) Bukkit.getOnlinePlayers().toArray()[0];
            player.sendPluginMessage(LousyNetVanishPlugin.getPlugin(), "BungeeCord", b.toByteArray());
        } catch (Exception e){
            System.out.println("Error while getting the server name");
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            String name = in.readUTF();
            LousyNetVanishPlugin.getPlugin().servername = name;
            System.out.println(name);
        }
    }
    }
}
