package club.frozed.gkit.provider.edition;

import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.items.WoolColors;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/26/2020 @ 13:39
 */
public class KitManagerColorSelectionMenu extends Menu {

    private Kit kit;

    @Getter @Setter private static boolean italic, bold = false;

    public KitManagerColorSelectionMenu(Kit kit) {
        this.kit = kit;
    }

    @Override
    public String getTitle(Player player) {
        return Color.translate("&b&lEditing Kit &f▶ " + kit.getColor() + kit.getName());
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {

        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new ColorSelectionButton(kit, ChatColor.RED));
        buttons.put(1, new ColorSelectionButton(kit, ChatColor.GOLD));
        buttons.put(2, new ColorSelectionButton(kit, ChatColor.YELLOW));
        buttons.put(3, new ColorSelectionButton(kit, ChatColor.DARK_GREEN));
        buttons.put(4, new ColorSelectionButton(kit, ChatColor.GREEN));
        buttons.put(5, new ColorSelectionButton(kit, ChatColor.AQUA));
        buttons.put(6, new ColorSelectionButton(kit, ChatColor.DARK_AQUA));
        buttons.put(7, new ColorSelectionButton(kit, ChatColor.BLUE));
        buttons.put(8, new ColorSelectionButton(kit, ChatColor.LIGHT_PURPLE));
        buttons.put(9, new ColorSelectionButton(kit, ChatColor.DARK_PURPLE));
        buttons.put(10, new ColorSelectionButton(kit, ChatColor.WHITE));
        buttons.put(11, new ColorSelectionButton(kit, ChatColor.GRAY));
        buttons.put(12, new ColorSelectionButton(kit, ChatColor.DARK_GRAY));
        buttons.put(13, new ColorSelectionButton(kit, ChatColor.BLACK));

        buttons.put(16, new ColorBold());
        buttons.put(17, new ColorItalic());

        return buttons;
    }

    private class ColorSelectionButton extends Button {

        Kit kitManager;
        ChatColor color;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.WOOL)
                    .setName(color + WordUtils.capitalize(color.name().replace("_", "").toLowerCase()))
                    .setDurability(WoolColors.convertChatColorToWoolData(color))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (isItalic() && isBold() || isBold() && isItalic()) {
                kitManager.setColor("&" + color.getChar() + "&l&o");
            } else if (isItalic()) {
                kitManager.setColor("&" + color.getChar() + "&o");
            } else if (isBold()) {
                kitManager.setColor("&" + color.getChar() + "&l");
            } else {
                kitManager.setColor("&" + color.getChar());
            }
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
            kitManager.saveKit(player);
            new KitManagerEditionMenu(kitManager).openMenu(player);
        }

        public ColorSelectionButton(Kit kit, ChatColor color) {
            this.kitManager = kit;
            this.color = color;
        }
    }

    private class ColorBold extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.CLAY_BALL)
                    .setName("&f&lBold")
                    .setLore(Collections.singletonList((isBold() ? "&7 ▶ &aEnabled" : "&7 ▶ &cDisabled")))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playSound(player, !isBold());
            setBold(!isBold());
        }

        private void playSound(Player player, boolean b) {
            if (b) {
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2F, 2F);
            } else {
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2F, 2F);
            }
        }
    }

    private class ColorItalic extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.FEATHER)
                    .setName("&f&oItalic")
                    .setLore(Collections.singletonList((isItalic() ? "&7 ▶ &aEnabled" : "&7 ▶ &cDisabled")))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            playSound(player, !isItalic());
            setItalic(!isItalic());
        }

        private void playSound(Player player, boolean b) {
            if (b) {
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 2F, 2F);
            } else {
                player.playSound(player.getLocation(), Sound.ITEM_BREAK, 2F, 2F);
            }
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * 2;
    }
}
