
package com.yourplugin.invsee;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class InvSeePlugin extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        this.getCommand("invsee").setExecutor(this);
        getLogger().info("InvSeePlugin aktif!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Hanya pemain yang bisa menjalankan perintah ini.");
            return true;
        }

        Player admin = (Player) sender;

        if (!admin.hasPermission("invsee.use")) {
            admin.sendMessage("§cKamu tidak memiliki izin!");
            return true;
        }

        if (args.length != 1) {
            admin.sendMessage("§eGunakan: /invsee <nama_player>");
            return true;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);

        if (target != null && target.isOnline()) {
            Inventory inv = Bukkit.createInventory(null, 45, "Inventory " + targetName);
            inv.setContents(target.getInventory().getContents());
            for (int i = 0; i < target.getEnderChest().getSize(); i++) {
                inv.setItem(i + 36, target.getEnderChest().getItem(i));
            }
            admin.openInventory(inv);
            admin.sendMessage("§aMembuka inventory dan ender chest pemain online: " + targetName);
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetName);
            if (!offlinePlayer.hasPlayedBefore()) {
                admin.sendMessage("§cPemain tidak ditemukan.");
                return true;
            }
            admin.sendMessage("§cPemain sedang offline, fitur offline player terbatas dalam contoh ini.");
        }
        return true;
    }
}
