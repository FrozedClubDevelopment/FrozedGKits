package club.frozed.gkit.utils.items;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedGKits
 * Date: 22/10/2020 @ 16:46
 */

public class InventoryUtils {

    public static Inventory toInventory(FileConfiguration config, String path) {
        Inventory in = Bukkit.createInventory(null, 36);
        for (int i = 0; i < 36 ;i++) {
            if (config.isItemStack(path + "." + i)) {
                in.setItem(i, config.getItemStack(path + "." + i));
            }
        }
        return in;
    }

    public static void saveInventory(Inventory inv, FileConfiguration config, String path) {
        for (int i = 0; i<inv.getSize();i++) {
            if (inv.getItem(i)!=null) {
                config.set(path + "." + i, inv.getItem(i));
            } else {
                if (config.isItemStack(path + "." + i)) {
                    config.set(path + "." + i, null);
                }
            }
        }
    }

    public static ItemStack[] toItemStack(FileConfiguration config, String path) {
        ItemStack[] itemStacks = new ItemStack[4];
        for (int i = 0; i<27;i++) {
            if (config.isItemStack(path + "." + i)) {
                itemStacks[i] = config.getItemStack(path + "." + i);
            }
        }
        return itemStacks;
    }

    public static void saveItemStacks(ItemStack[] itemStacks, FileConfiguration config, String path) {
        for (int i = 0; i< itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                config.set(path + "." + i, itemStacks[i]);
            } else {
                if (config.isItemStack(path + "." + i)) {
                    config.set(path + "." + i, null);
                }
            }
        }
    }

    public static boolean hasAvailableSlot(Player player) {
        Inventory inv = player.getInventory();
        for (ItemStack item : inv.getContents()) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

}
