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

    private Kit kit;

    public KitPreviewMenu(Kit kit){
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
        for (ItemStack itemStacks : kit.getInventory()){
            buttons.put(slot,new itemButton(itemStacks));
            slot++;
        }
        ItemStack[] armor = kit.getArmor();
        if (armor[3] == null || armor[3].getType().equals(Material.AIR)){
            buttons.put(47,new nullArmorButton("Helmet"));
        } else {
            buttons.put(47,new armorButton(armor[3]));
        }
        if (armor[2] == null || armor[2].getType().equals(Material.AIR)){
            buttons.put(48,new nullArmorButton("Chestplate"));
        } else {
            buttons.put(48,new armorButton(armor[2]));
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

        if (armor[1] == null || armor[1].getType().equals(Material.AIR)){
            buttons.put(50,new nullArmorButton("Leggings"));
        } else {
            buttons.put(50,new armorButton(armor[1]));
        }

        if (armor[0] == null || armor[0].getType().equals(Material.AIR)){
            buttons.put(51,new nullArmorButton("Boots"));
        } else {
            buttons.put(51,new armorButton(armor[0]));
        }
        return buttons;
    }

    private class itemButton extends Button {

        private ItemStack itemStack;

        @Override
        public ItemStack getButtonItem(Player player) {
            return itemStack;
        }
        public itemButton(ItemStack itemStack){
            this.itemStack = itemStack;
        }
    }

    private class armorButton extends Button{
        private ItemStack itemStack;

        @Override
        public ItemStack getButtonItem(Player player) {
            return itemStack;
        }

        public armorButton(ItemStack itemStack) {
            this.itemStack = itemStack;
        }
    }

    private class nullArmorButton extends Button {

        String armor;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.FIREBALL).setName("&7[&4!&7] &cNo " + armor).get();
        }

        public nullArmorButton(String string){
            this.armor = string;
        }

    }

    @Override
    public int getSize() {
        return 9*6;
    }
}
