package club.frozed.gkit.provider.selection;

import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryzeon
 * Project: FrozedGKits
 * Date: 28/09/2020 @ 12:29
 */

public class KitPreviewMenu extends Menu {

    private final Kit kit;

    public KitPreviewMenu(Kit kit) {
        this.kit = kit;
    }

    @Override
    public String getTitle(Player player) {
        return Color.translate(kit.getColor() + kit.getName() + " &fKit preview");
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();
        int slot = 0;

        for (ItemStack itemStacks : kit.getInventory()) {
            if (itemStacks != null) {
                buttons.put(slot, new ItemButton(itemStacks));
                slot++;
            }
        }

        ItemStack[] armor = kit.getArmor();
        if (armor[3] == null || armor[3].getType().equals(Material.AIR)) {
            buttons.put(47, new NullArmorButton("Helmet"));
        } else {
            buttons.put(47, new ArmorButton(armor[3]));
        }
        if (armor[2] == null || armor[2].getType().equals(Material.AIR)) {
            buttons.put(48, new NullArmorButton("Chestplate"));
        } else {
            buttons.put(48, new ArmorButton(armor[2]));
        }

        buttons.put(49, new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemCreator(Material.REDSTONE).setName("&cBack").get();
            }

            @Override
            public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
                new KitSelectionMenu().openMenu(player);
            }
        });

        if (armor[1] == null || armor[1].getType().equals(Material.AIR)) {
            buttons.put(50, new NullArmorButton("Leggings"));
        } else {
            buttons.put(50, new ArmorButton(armor[1]));
        }

        if (armor[0] == null || armor[0].getType().equals(Material.AIR)) {
            buttons.put(51, new NullArmorButton("Boots"));
        } else {
            buttons.put(51, new ArmorButton(armor[0]));
        }

        return buttons;
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    private class ItemButton extends Button {

        private final ItemStack itemStack;

        public ItemButton(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            if (itemStack == null) {
                return new ItemStack(Material.STAINED_GLASS);
            }
            return itemStack;
        }
    }

    private class ArmorButton extends Button {

        private final ItemStack itemStack;

        public ArmorButton(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return itemStack;
        }
    }

    private class NullArmorButton extends Button {

        String armor;

        public NullArmorButton(String string) {
            this.armor = string;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.FIREBALL).setName("&7[&4!&7] &cNo " + armor).get();
        }
    }
}
