package club.frozed.gkit.provider.edition;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.provider.selection.KitManagerSelectionMenu;
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

import java.util.*;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/26/2020 @ 13:49
 */
public class KitManagerSlotSelectionMenu extends Menu {

    private Kit kitManager;

    public KitManagerSlotSelectionMenu(Kit kitManager){
        this.kitManager = kitManager;
    }

    private final FrozedGKits plugin = FrozedGKits.getInstance();

    @Override
    public String getTitle(Player player) {
        return Color.translate(FrozedGKits.getInstance().getPluginConfig().getConfig().getString("KIT-MANAGER-MENU.INVENTORY-TITLE"));
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Kit kitManager : KitManager.getKits()) {
            buttons.put(kitManager.getSlotPosition(), new SlotButton(false,kitManager));
        }

        setPlaceholder(true);
        setPlaceholderButton(new SlotButton(true,kitManager));

        return buttons;
    }

    private static class SlotButton extends Button{

        private boolean xd;

        private final Kit kitSlot;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemCreator(Material.STAINED_GLASS_PANE).setName(Color.translate(xd ? "&aAvailable" : "&cNot available")).setDurability(xd ? 5 : 14).get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!xd) return;
            kitSlot.setSlotPosition(slot);
            playSuccess(player);
            new KitManagerEditionMenu(kitSlot).openMenu(player);
        }

        public SlotButton(boolean xd, Kit kitSlot) {
            this.xd = xd;
            this.kitSlot = kitSlot;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * plugin.getPluginConfig().getConfig().getInt("KIT-SELECTION-MENU.INVENTORY-SIZE");
    }
}
