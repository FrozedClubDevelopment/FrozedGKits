package club.frozed.gkit.provider.edition;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
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
 * Date: 09/26/2020 @ 13:39
 */
public class KitManagerColorSelectionMenu  extends Menu {

    private final FrozedGKits plugin = FrozedGKits.getInstance();

    @Override
    public String getTitle(Player player) {
        return Color.translate(FrozedGKits.getInstance().getPluginConfig().getConfig().getString("KIT-MANAGER-MENU.INVENTORY-TITLE"));
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();


        for (KitManager kitManager : KitManager.getKits()) {

        }

        return buttons;
    }

    public static class ColorSelectionButton extends Button {

        KitManager kitManager;

        @Override
        public ItemStack getButtonItem(Player player) {
            int i = 0;
            return new ItemCreator(Material.STAINED_GLASS_PANE)
                    .setDurability(0)
                    .setName(Color.translate("&b&lSlot #" + i))
                    .setLore(Arrays.asList(
                            Color.MENU_BAR,
                            "&7This is slot is free",
                            "&7Click to set the slot position",
                            "&7for the " + kitManager.getColor() + kitManager.getName() + "&7 GKit here.",
                            Color.MENU_BAR))
                    .get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            kitManager.setColor("agarrar color ekisde");
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
        }

        public ColorSelectionButton(KitManager kit) {
            this.kitManager = kit;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * plugin.getPluginConfig().getConfig().getInt("KIT-MANAGER-MENU.INVENTORY-SIZE");
    }
}
