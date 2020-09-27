package club.frozed.gkit.provider.edition;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/26/2020 @ 01:38
 */
public class KitManagerEditionMenu extends Menu {

    private final Kit kitManager;

    @Getter public static Map<String, Kit> cooldownProcessPlayer = new HashMap<>();

    @Override
    public String getTitle(Player player) {
        return Color.translate(FrozedGKits.getInstance().getPluginConfig().getConfig().getString("KIT-MANAGER-MENU.INVENTORY-TITLE"));
    }

    public KitManagerEditionMenu(Kit kit){
        this.kitManager = kit;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {

        Map<Integer, Button> buttons = new HashMap<>();
            buttons.put(0, new ActiveKitButton(kitManager));
            buttons.put(1, new KitIconButton(kitManager));
            buttons.put(2, new KitSlotPositionButton(kitManager));
            buttons.put(5, new KitToggleButton(kitManager));
            buttons.put(6, new KitCooldownButton(kitManager));
            buttons.put(7, new KitColorButton(kitManager));
            buttons.put(8, new SaveKitButton(kitManager));


        return buttons;
    }

    public static class ActiveKitButton extends Button {

        Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.NETHER_STAR)
                    .setName(Color.translate("&b&lActive Kit &f▶ &r" + kitManager.getColor() + kitManager.getName()))
                    .setLore(Arrays.asList(Color.MENU_BAR, "&7You're editing the kit: &f" + kitManager.getName(), Color.MENU_BAR))
                    .get();
        }

        public ActiveKitButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    public static class KitIconButton extends Button {

        private final Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(kitManager.getIcon())
                    .setName(Color.translate("&b&lKit Icon"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7Drag and drop new kit icon here.",
                            Color.MENU_BAR)
                    ).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (player.getItemOnCursor().getType() == null || player.getItemOnCursor().getType() == Material.AIR) {
                return;
            }
            kitManager.setIcon(new ItemStack(player.getItemOnCursor().getType()));
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
        }

        public KitIconButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    public static class KitSlotPositionButton extends Button {

        private final Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.PAINTING)
                    .setName(Color.translate("&b&lKit Slot"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7Click here to increase or decrease",
                            "&7the slot position of the kit.",
                            Color.MENU_BAR)
                    ).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new KitManagerSlotSelectionMenu(kitManager).openMenu(player);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
        }

        public KitSlotPositionButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    public static class KitToggleButton extends Button {

        Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.REDSTONE_TORCH_ON)
                    .setName(Color.translate("&b&lToggle Kit"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7Click here to enable or disable this kit.",
                            " ",
                            "&7Current State&f: " + (kitManager.isEnabled() ? "&aenabled" : "&cdisabled"),
                            Color.MENU_BAR)
                    ).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            Kit.getKitByName(kitManager.getName()).setEnabled(!kitManager.isEnabled());
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
        }

        public KitToggleButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    public static class KitCooldownButton extends Button {

        Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.WATCH)
                    .setName(Color.translate("&b&lKit Cooldown"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7Click here to input ",
                            "&7the kit cooldown period.",
                            Color.MENU_BAR)
                    ).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            player.closeInventory();
            cooldownProcessPlayer.put(player.getName(),kitManager);
            player.sendMessage(Color.translate("&aEnter kit cooldown duration."));
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
        }

        public KitCooldownButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    public static class KitColorButton extends Button {

        Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.WOOL).setDurability(1)
                    .setName(Color.translate("&b&lKit Color"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7Click here to",
                            "&7select the kit color.",
                            Color.MENU_BAR)
                    ).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            new KitManagerColorSelectionMenu(kitManager).openMenu(player);
        }

        public KitColorButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    public static class SaveKitButton extends Button {

        Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.CHEST)
                    .setName(Color.translate("&b&lSave Kit"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7Click here to",
                            "&7save the new kit inventory.",
                            Color.MENU_BAR)
                    ).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            KitManager.saveKit(kitManager.getName(), player);
            player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
            player.closeInventory();
        }

        public SaveKitButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9;
    }
}
