package club.frozed.gkit.provider.selection;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.provider.data.PlayerData;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 19:22
 */
public class KitSelectionMenu extends Menu {

    private final FrozedGKits plugin = FrozedGKits.getInstance();

    @Override
    public String getTitle(Player player) {
        return Color.translate(FrozedGKits.getInstance().getPluginConfig().getConfig().getString("KIT-SELECTION-MENU.INVENTORY-TITLE"));
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {

        Map<Integer, Button> buttons = new HashMap<>();

        for (Kit kit : KitManager.getKits()) {
            if (kit != null && kit.isEnabled()) {
                buttons.put(kit.getSlotPosition(), new GKitButton(kit));
            }
        }

        return buttons;
    }

    public static class GKitButton extends Button {

        Kit kit;

        @Override
        public ItemStack getButtonItem(Player player) {
            ItemCreator itemCreator = new ItemCreator(kit.getIcon());
            itemCreator.setName(kit.getColor() + kit.getName());
            List<String> lore = new ArrayList<>();
            PlayerData playerData = PlayerData.getByName(player.getName());
            if (player.hasPermission("frozedgkit." + kit.getName())) {
                if (playerData.hasExpired(kit)) {
                    FrozedGKits.getInstance().getPluginConfig().getConfig().getStringList("KIT-SELECTION-MENU.ITEM-LORE.AVAILABLE").forEach(text -> lore.add(Color.translate(text).replace("<kit>", kit.getName())));
                } else {
                    FrozedGKits.getInstance().getPluginConfig().getConfig().getStringList("KIT-SELECTION-MENU.ITEM-LORE.TIME-LEFT").forEach(text -> lore.add(Color.translate(text).replace("<time-left>", playerData.getNiceExpire(kit))));
                }
            } else {
                FrozedGKits.getInstance().getPluginConfig().getConfig().getStringList("KIT-SELECTION-MENU.ITEM-LORE.NO-PERMS").forEach(text -> lore.add(Color.translate(text)));
            }
            itemCreator.setLore(lore);
            return itemCreator.get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            switch (clickType) {
                case RIGHT:
                case LEFT:
                    PlayerData playerData = PlayerData.getByName(player.getName());
                    if (!player.hasPermission("frozedgkit." + kit.getName())) return;
                    if (playerData.hasExpired(kit)) {
                        player.closeInventory();
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(null);
                        player.getInventory().setContents(kit.getInventory());
                        player.getInventory().setArmorContents(kit.getArmor());
                        playerData.saveCooldown(System.currentTimeMillis(), kit);
                    } else {
                        player.closeInventory();
                        player.sendMessage(Color.translate(FrozedGKits.getInstance().getPluginConfig()
                                .getConfig().getString("KIT-SELECTION-MENU.COOLDOWN-MSG").replace("<time-left>", playerData.getNiceExpire(kit))));
                    }
                    break;
                default:
                    break;
            }
        }

        public GKitButton(Kit kit) {
            this.kit = kit;
        }
    }

    @Override
    public int size(Map<Integer, Button> buttons) {
        return 9 * plugin.getPluginConfig().getConfig().getInt("KIT-SELECTION-MENU.INVENTORY-SIZE");
    }
}
