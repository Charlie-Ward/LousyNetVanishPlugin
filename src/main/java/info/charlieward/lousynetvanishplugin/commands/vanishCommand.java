package info.charlieward.lousynetvanishplugin.commands;

import info.charlieward.lousynetvanishplugin.LousyNetVanishPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class vanishCommand implements CommandExecutor {

    LousyNetVanishPlugin plugin;

    public vanishCommand (LousyNetVanishPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("LousyNetVanish.Vanish")) {
                String playerName = player.getDisplayName();

                int place = 0;
                String newList = "";
                Boolean alreadyVanished = false;
                String vanishedPlayers = plugin.jedis.get("VanishPlayers");
                String[] vanishPlayersIndividual = vanishedPlayers.split(",");

                for (int i = 0; i < vanishPlayersIndividual.length; i++) {
                    if (Objects.equals(playerName, vanishPlayersIndividual[i])) {
                        alreadyVanished = true;
                        place = i;
                    }
                }
                if (alreadyVanished) {
                    player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.WHITE + "You are no longer vanished");
                    for (int i = 0; i < vanishPlayersIndividual.length; i++) {
                        if (i != place) {
                            newList = newList + vanishPlayersIndividual[i] + ",";
                        }
                    }
                    newList = newList.substring(0, newList.length()-1);
                    plugin.jedis.set("VanishPlayers", newList);
                    /* Turn off vanish code */
                } else {
                    player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.WHITE + "You are now vanished");
                    if (Objects.equals(vanishPlayersIndividual[0], "")) {
                        vanishedPlayers = playerName;
                    } else {
                        vanishedPlayers = vanishedPlayers + "," + playerName;
                    }
                    System.out.println(vanishedPlayers);
                    plugin.jedis.set("VanishPlayers", vanishedPlayers);
                    /* Turn on vanish code */
                }

            } else {
                player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.RED + "You do not have the correct permission to use this plugin.");
                return false;
            }
        }

        return false;
    }
}
