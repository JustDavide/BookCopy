package me.dovide.bookcopy.commands;

import me.dovide.bookcopy.CopyMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CopyAdmin implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            return true;

        Plugin plugin = CopyMain.getInstance();
        CopyMain main = new CopyMain();

        String reload = plugin.getConfig().getString("admin.reload"),
                perm = plugin.getConfig().getString("admin.perm"),
                usage = plugin.getConfig().getString("admin.usage"),
                noperm = plugin.getConfig().getString("admin.noperm");


        Player player = (Player) sender;

        if(args.length != 1){
            player.sendMessage(usage);
            return true;
        }

        if(!(player.hasPermission(perm))){
            player.sendMessage(noperm);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            main.reloadCustomConfig();
            player.sendMessage(reload);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if(args.length == 1){
            List<String> firstArg = new ArrayList<>();
            firstArg.add("reload");
            return firstArg;
        }

        return null;
    }
}
