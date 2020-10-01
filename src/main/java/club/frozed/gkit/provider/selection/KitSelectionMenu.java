package club.frozed.gkit.provider.selection;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.provider.data.PlayerData;
import club.frozed.gkit.utils.Utils;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.items.ItemCreator;
import club.frozed.gkit.utils.menu.Button;
import club.frozed.gkit.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.World;
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
                    FrozedGKits.getInstance().getPluginConfig().getConfig().getStringList("KIT-SELECTION-MENU.ITEM-LORE.AVAILABLE")
                            .forEach(text -> lore.add(Color.translate(text)
                                    .replace("<kit>", kit.getName())
                                    .replace("<kit-cooldown>", Utils.formatTimeMillis(kit.getCooldown()))));
                } else {
                    FrozedGKits.getInstance().getPluginConfig().getConfig().getStringList("KIT-SELECTION-MENU.ITEM-LORE.TIME-LEFT")
                            .forEach(text -> lore.add(Color.translate(text)
                                    .replace("<time-left>", playerData.getNiceExpire(kit))
                                    .replace("<kit-cooldown>", Utils.formatTimeMillis(kit.getCooldown()))));
                }
            } else {
                FrozedGKits.getInstance().getPluginConfig().getConfig().getStringList("KIT-SELECTION-MENU.ITEM-LORE.NO-PERMS").forEach(text -> lore.add(Color.translate(text)));
            }
            itemCreator.setLore(lore);
            return itemCreator.get();
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            PlayerData playerData = PlayerData.getByName(player.getName());
            switch (clickType) {
                case LEFT:
                    if (player.hasPermission("frozedgkit." + kit.getName())) {
                        if (playerData.hasExpired(kit)) {
                            dropKitPlayer(kit, player);
                            playerData.saveCooldown(System.currentTimeMillis(), kit);
                        } else {
                            player.closeInventory();
                            player.sendMessage(Color.translate(FrozedGKits.getInstance().getPluginConfig().getConfig().getString("KIT-SELECTION-MENU.COOLDOWN-MSG").replace("<time-left>", playerData.getNiceExpire(kit))));
                        }
                    }
                    break;
                case RIGHT:
                    new KitPreviewMenu(kit).openMenu(player);
                    break;
                default:
                    break;
            }

        }

        private void dropKitPlayer(Kit kit, Player player) {
            World world = player.getWorld();
            if (getInventoryAvailableSlots(player) > kit.getInventory().getContents().length) {
                for (ItemStack itemStack : kit.getInventory().getContents()) {
                    world.dropItemNaturally(player.getLocation(), itemStack);
                }
            } else {
                player.getInventory().clear();
                player.getInventory().setContents(kit.getInventory().getContents());
            }
            if (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType() == Material.AIR) {
                player.getInventory().setHelmet(kit.getArmor()[3]);
            } else {
                world.dropItemNaturally(player.getLocation(), kit.getArmor()[3]);
            }
            if (player.getInventory().getChestplate() == null || player.getInventory().getChestplate().getType() == Material.AIR) {
                player.getInventory().setChestplate(kit.getArmor()[2]);
            } else {
                world.dropItemNaturally(player.getLocation(), kit.getArmor()[2]);
            }
            if (player.getInventory().getLeggings() == null || player.getInventory().getLeggings().getType() == Material.AIR) {
                player.getInventory().setLeggings(kit.getArmor()[1]);
            } else {
                world.dropItemNaturally(player.getLocation(), kit.getArmor()[1]);
            }
            if (player.getInventory().getBoots() == null || player.getInventory().getBoots().getType() == Material.AIR) {
                player.getInventory().setBoots(kit.getArmor()[1]);
            } else {
                world.dropItemNaturally(player.getLocation(), kit.getArmor()[0]);
            }
        }

        private int getInventoryAvailableSlots(Player player) {
            int slots = 0;
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack.getType() != Material.AIR || itemStack != null) {
                    slots++;
                }
            }
            return slots;
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
