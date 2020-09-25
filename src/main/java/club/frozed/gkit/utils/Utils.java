package club.frozed.gkit.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 18:04
 */
public class Utils {

    public static String formatTime(int timer) {
        int hours = timer / 3600;
        int secondsLeft = timer - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";

        if (hours > 0) {
            if (hours < 10)
                formattedTime += "0";
            formattedTime += hours + ":";
        }

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }


    public static boolean isInventoryEmpty(Inventory inv) {
        ItemStack[] contents;
        for (int length = (contents = inv.getContents()).length, i = 0; i < length; ++i) {
            final ItemStack item = contents[i];
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInteger(String in) {
        try {
            Integer.parseInt(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
