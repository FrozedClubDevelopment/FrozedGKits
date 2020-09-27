package club.frozed.gkit.provider.edition;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/26/2020 @ 13:49
 */
public class KitManagerSlotSelectionMenu extends Menu {

    private Kit kitManager;

    public KitManagerSlotSelectionMenu(Kit kitManager) {
        this.kitManager = kitManager;
    }

    private final FrozedGKits plugin = FrozedGKits.getInstance();

    @Override
    public String getTitle(Player player) {
        return Color.translate("&b&lEditing Kit &f▶ " + kitManager.getColor() + kitManager.getName());
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Kit kitManager : KitManager.getKits()) {
            buttons.put(kitManager.getSlotPosition(), new SlotButton(false, kitManager));
        }

        setPlaceholder(true);
        setPlaceholderButton(new SlotButton(true, kitManager));

        return buttons;
    }

    private static class SlotButton extends Button {

        private boolean isAvailable;
        private final Kit kitSlot;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.STAINED_GLASS_PANE)
                    .setName(Color.translate(isAvailable ? "&aAvailable" : "&cNot Available"))
                    .setDurability(isAvailable ? 5 : 14)
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            kitSlot.getColor() + kitSlot.getName() + " &7is assigned to this slot [&b" + kitSlot.getSlotPosition() + "&7]",
                            Color.MENU_BAR))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!isAvailable) return;
            kitSlot.setSlotPosition(slot);
            playSuccess(player);
            new KitManagerEditionMenu(kitSlot).openMenu(player);
        }

        public SlotButton(boolean isAvailable, Kit kitSlot) {
            this.isAvailable = isAvailable;
            this.kitSlot = kitSlot;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * plugin.getPluginConfig().getConfig().getInt("KIT-SELECTION-MENU.INVENTORY-SIZE");
    }
}
