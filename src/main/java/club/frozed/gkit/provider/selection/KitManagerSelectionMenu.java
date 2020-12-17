package club.frozed.gkit.provider.selection;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.provider.edition.KitManagerEditionMenu;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import org.apache.commons.lang.StringUtils;
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

        int slot = 0;
        for (Kit kitManager : KitManager.getKits()) {
            buttons.put(slot, new KitButton(kitManager));
            slot++;
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
                            "&a ▶ &lLEFT-CLICK-HERE &ato create new a kit",
                            "&e ▶ &lRIGHT-CLICK &eto edit an existing kit",
                            "&c ▶ &lMIDDLE-CLICK &cto delete an existing kit",
                            Color.MENU_BAR))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            player.closeInventory();
            if (clickType.isLeftClick()) {
                if (!KitManager.getKitNameState().contains(player.getUniqueId())) {
                    playSuccess(player);
                    player.sendMessage(Color.translate("&8[&b&l!&8] &aType the name of the new kit..."));
                    KitManager.getKitNameState().add(player.getUniqueId());
                } else {
                    playFail(player);
                    player.sendMessage(Color.translate("&8[&4&l!&8] &cYou are already creating a new kit! Please type the name..."));
                }
            }
        }
    }

    public static class KitButton extends Button {

        Kit kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.valueOf(kitManager.getIcon()))
                    .setName(Color.translate(kitManager.getColor() + StringUtils.capitalize(kitManager.getName())))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            kitManager.getColor() + "&l" + kitManager.getName() + " &bKit",
                            "&7Click here to edit this kit.",
                            Color.MENU_BAR))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (clickType == ClickType.LEFT) {
                return;
            }
            switch (clickType) {
                case RIGHT:
                    new KitManagerEditionMenu(kitManager).openMenu(player);
                    player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
                    break;
                case MIDDLE:
                    KitManager.deleteKit(kitManager);
                    player.sendMessage(Color.translate("&8[&b&l!&8] &7You have deleted the kit: &a" + kitManager.getName()));
                    player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F);
                    break;
            }
        }

        public KitButton(Kit kit) {
            this.kitManager = kit;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * plugin.getPluginConfig().getConfig().getInt("KIT-MANAGER-MENU.INVENTORY-SIZE");
    }
}
