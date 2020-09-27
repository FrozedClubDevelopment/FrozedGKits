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
            ItemStack item = contents[i];
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

    public static String formatTimeMillis(long millis) {
        long seconds = millis / 1000L;
        if (seconds <= 0L)
            return "0 seconds";
        long minutes = seconds / 60L;
        seconds %= 60L;
        long hours = minutes / 60L;
        minutes %= 60L;
        long day = hours / 24L;
        hours %= 24L;
        long years = day / 365L;
        day %= 365L;
        StringBuilder time = new StringBuilder();
        if (years != 0L)
            time.append(years).append((years == 1L) ? " year " : " years ");
        if (day != 0L)
            time.append(day).append((day == 1L) ? " day " : " days ");
        if (hours != 0L)
            time.append(hours).append((hours == 1L) ? " hour " : " hours ");
        if (minutes != 0L)
            time.append(minutes).append((minutes == 1L) ? " minute " : " minutes ");
        if (seconds != 0L)
            time.append(seconds).append((seconds == 1L) ? " second " : " seconds ");
        return time.toString().trim();
    }
}
