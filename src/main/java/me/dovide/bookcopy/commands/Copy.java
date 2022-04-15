package me.dovide.bookcopy.commands;

import me.dovide.bookcopy.CopyMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class Copy implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        Plugin plugin = CopyMain.getInstance();

        String perm = plugin.getConfig().getString("copy.perm"),
                usage = plugin.getConfig().getString("copy.usage"),
                noperm = plugin.getConfig().getString("copy.noperm"),
                nohold = plugin.getConfig().getString("copy.hold"),
                item1 = plugin.getConfig().getString("item-allow.item1"),
                item2 = plugin.getConfig().getString("item-allow.item2"),
                success = plugin.getConfig().getString("copy.success"),
                actionbar = plugin.getConfig().getString("copy.actionbar");

        int amount = plugin.getConfig().getInt("copy.amount"),
                delay = plugin.getConfig().getInt("copy.delay");

        delay = delay * 20;


        ItemStack hold = player.getInventory().getItemInMainHand();

        if (args.length != 0) {
            player.sendMessage(usage);
            return true;
        }

        if (!(hold.getType().toString().equals(item1) || hold.getType().toString().equals(item2)) || hold.getType().equals(Material.AIR)) {
            player.sendMessage(nohold);
            return true;
        }


        if (player.hasPermission(perm)) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(actionbar));
            new BukkitRunnable() {

                @Override
                public void run() {
                    BookMeta oldMeta = (BookMeta) hold.getItemMeta();
                    ItemStack newBook = new ItemStack(hold.getType());
                    newBook.setAmount(amount);
                    if(oldMeta.hasGeneration())
                        oldMeta.setGeneration(BookMeta.Generation.COPY_OF_COPY);
                    else
                        oldMeta.setGeneration(BookMeta.Generation.COPY_OF_ORIGINAL);
                    player.sendMessage();
                    newBook.setItemMeta(oldMeta);
                    player.getInventory().addItem(newBook);
                    player.sendMessage(success);
                }
            }.runTaskLater(plugin, delay);
        }else
            player.sendMessage(noperm);

        return true;
    }

}
