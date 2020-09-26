package club.frozed.gkit.provider;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 21:31
 */
public class KitManagerSelectionMenu extends Menu {

    private final FrozedGKits plugin = FrozedGKits.getInstance();

    @Override
    public String getTitle(Player player) {
        return Color.translate(FrozedGKits.getInstance().getPluginConfig().getConfig().getString("KIT-MANAGER-MENU.INVENTORY-TITLE"));
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (KitManager kitManager : KitManager.getKits()) {
            buttons.put(kitManager.getSlotPosition(), new KitButton(kitManager));
        }

        buttons.put(plugin.getPluginConfig().getConfig().getInt("KIT-MANAGER-MENU.KIT-SELECTION-ACTION-BUTTON-SLOT"), new KitSelectionActionButton());

        return buttons;
    }

    public static class KitSelectionActionButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.NETHER_STAR)
                    .setName(Color.translate("&b&lKit Selection Actions"))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&a ▶ &lLEFT-CLICK &ato create new kit",
                            "&e ▶ &lRIGHT-CLICK &ato edit an existing kit",
                            "&c ▶ &lSHIFT-CLICK &ato delete an existing kit",
                            Color.MENU_BAR)
                    )
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            switch (clickType) {
                case LEFT:
                    if (player.getGameMode() != GameMode.SURVIVAL) {
                        player.sendMessage(Color.translate("&cTo create a kit you need to be on Survival Mode."));
                        return;
                    }
                    Bukkit.getServer().broadcastMessage("Clicking here will create a new kit and open the kit edition menu right after.");
                    break;
                case RIGHT:
                case MIDDLE:
                    Bukkit.getServer().broadcastMessage("Click here doesn't do anything");
                    break;
            }
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
        }
    }

    public static class KitButton extends Button {

        KitManager kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.valueOf(kitManager.getIcon()))
                    .setName(Color.translate(kitManager.getColor() + StringUtils.capitalize(kitManager.getName())))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            kitManager.getColor() + "&l" + kitManager.getName() + " &bKit",
                            "&7Click here to edit this kit.",
                            Color.MENU_BAR)
                    )
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (clickType == ClickType.LEFT) {
                return;
            }
            switch (clickType) {
                case RIGHT:
                    new KitManagerEditionMenu().openMenu(player);
                    Bukkit.getServer().broadcastMessage("Click here will open the kit edition menu.");
                    break;
                case MIDDLE:
                    Bukkit.getServer().broadcastMessage("Click here will delete the kit that gets clicked.");
                    break;
            }
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
        }

        public KitButton(KitManager kit) {
            this.kitManager = kit;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * plugin.getPluginConfig().getConfig().getInt("KIT-MANAGER-MENU.INVENTORY-SIZE");
    }
}
