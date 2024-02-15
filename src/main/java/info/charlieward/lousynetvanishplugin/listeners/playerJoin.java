package info.charlieward.lousynetvanishplugin.listeners;

import info.charlieward.lousynetvanishplugin.LousyNetVanishPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class playerJoin implements Listener {

    LousyNetVanishPlugin plugin;

    public playerJoin (LousyNetVanishPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerJoins(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("LousyNetVanish.Vanish")) {
            String playerName = player.getDisplayName();
            String vanishedPlayers = plugin.jedis.get("VanishPlayers");
            String[] vanishPlayersIndividual = vanishedPlayers.split(",");
            for (int i = 0; i < vanishPlayersIndividual.length; i++) {
                if (Objects.equals(playerName, vanishPlayersIndividual[i])) {
                    for (Player people : Bukkit.getOnlinePlayers()){
                        if(!people.hasPermission("LousyNetVanish.Vanish")){
                            people.hidePlayer(plugin, player);
                        }
                    }
                    event.setJoinMessage(null);
                    player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.WHITE + "You are currently vanished");
                    player.setGameMode(GameMode.SPECTATOR);
                }
            }
        }
    }
}
