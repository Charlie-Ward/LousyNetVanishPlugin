package info.charlieward.lousynetvanishplugin.commands;

import info.charlieward.lousynetvanishplugin.LousyNetVanishPlugin;
import info.charlieward.lousynetvanishplugin.files.CustomConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    for (int i = 0; i < vanishPlayersIndividual.length; i++) {
                        if (i != place) {
                            newList = newList + vanishPlayersIndividual[i] + ",";
                        }
                    }
                    if (!newList.isEmpty()) {
                        newList = newList.substring(0, newList.length()-1);
                    }
                    plugin.jedis.set("VanishPlayers", newList);
                    for (Player people : Bukkit.getOnlinePlayers()){
                        if(!people.hasPermission("LousyNetVanish.Vanish")){
                            people.showPlayer(plugin, player);
                        }
                        if (Objects.equals(CustomConfig.get().getString("join/leave messages - True or False"), "true")) {
                            people.sendMessage(ChatColor.YELLOW + playerName + " has joined the game");
                        }
                    }

                    switch (CustomConfig.get().getString("gamemode")){
                        case "survival":
                            player.setGameMode(GameMode.SURVIVAL);
                            break;
                        case "creative":
                            player.setGameMode(GameMode.CREATIVE);
                            break;
                        case "adventure":
                            player.setGameMode(GameMode.ADVENTURE);
                            break;
                        default:
                            player.setGameMode(GameMode.SURVIVAL);
                    }
                } else {
                    player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.WHITE + "You are now vanished");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000000, 1));
                    if (Objects.equals(vanishPlayersIndividual[0], "")) {
                        vanishedPlayers = playerName;
                    } else {
                        vanishedPlayers = vanishedPlayers + "," + playerName;
                    }
                    plugin.jedis.set("VanishPlayers", vanishedPlayers);
                    player.setGameMode(GameMode.SPECTATOR);
                    for (Player people : Bukkit.getOnlinePlayers()){
                        if(!people.hasPermission("LousyNetVanish.Vanish")){
                            people.hidePlayer(plugin, player);
                        }
                        if (Objects.equals(CustomConfig.get().getString("join/leave messages - True or False"), "true")) {
                            people.sendMessage(ChatColor.YELLOW + playerName + " has left the game");
                        }
                    }
                }

            } else {
                player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.RED + "You do not have the correct permission to use this plugin.");
                return false;
            }
        }

        return true;
    }
}
