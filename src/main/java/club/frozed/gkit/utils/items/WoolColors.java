package club.frozed.gkit.utils.items;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/26/2020 @ 17:01
 */
public class WoolColors {

    public static final ArrayList<ChatColor> woolColors = new ArrayList<>(
            Arrays.asList(
                    ChatColor.WHITE,
                    ChatColor.GOLD,
                    ChatColor.LIGHT_PURPLE,
                    ChatColor.AQUA,
                    ChatColor.YELLOW,
                    ChatColor.GREEN,
                    ChatColor.LIGHT_PURPLE,
                    ChatColor.DARK_GRAY,
                    ChatColor.GRAY,
                    ChatColor.DARK_AQUA,
                    ChatColor.DARK_PURPLE,
                    ChatColor.BLUE,
                    ChatColor.RESET,
                    ChatColor.DARK_GREEN,
                    ChatColor.RED,
                    ChatColor.BLACK));

    public static int convertChatColorToWoolData(ChatColor color) {
        if (color == ChatColor.DARK_RED) color = ChatColor.RED;
        if (color == ChatColor.DARK_BLUE) color = ChatColor.BLUE;
        return woolColors.indexOf(color);
    }
}
