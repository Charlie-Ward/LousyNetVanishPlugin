package info.charlieward.lousynetvanishplugin.commands;

import info.charlieward.lousynetvanishplugin.LousyNetVanishPlugin;
import jdk.tools.jlink.internal.Platform;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class getServerName implements CommandExecutor {

    LousyNetVanishPlugin plugin;

    public getServerName (LousyNetVanishPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            LousyNetVanishPlugin.getServerName();
            player.sendMessage(ChatColor.BLUE + "[LousyNet-VanishPlugin] " + ChatColor.WHITE + "The plugin is now set up and ready to use");
        }
        return false;
    }
}
