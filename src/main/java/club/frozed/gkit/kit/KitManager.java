package club.frozed.gkit.kit;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.config.ConfigCursor;
import club.frozed.gkit.utils.items.InventoryUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 18:06
 */
@Getter @Setter
public class KitManager {

    @Getter private static List<Kit> kits = new ArrayList<>();
    @Getter private static List<UUID> kitNameState = new ArrayList<>();

    public static void loadKits() {
        kits.clear();

        for (String kit : FrozedGKits.getInstance().getKitsConfig().getConfig().getConfigurationSection("KITS").getKeys(false)) {
            String icon = FrozedGKits.getInstance().getKitsConfig().getConfig().getString("KITS." + kit + ".ICON");
            int slotPosition = FrozedGKits.getInstance().getKitsConfig().getConfig().getInt("KITS." + kit + ".SLOT");
            String color = FrozedGKits.getInstance().getKitsConfig().getConfig().getString("KITS." + kit + ".COLOR");
            boolean enabled = FrozedGKits.getInstance().getKitsConfig().getConfig().getBoolean("KITS." + kit + ".ENABLED");
            long cooldown = FrozedGKits.getInstance().getKitsConfig().getConfig().getLong("KITS." + kit + ".COOLDOWN");
            ItemStack[] armor = null;
            Inventory inventory = null;

            try {
                armor = InventoryUtils.toItemStack(FrozedGKits.getInstance().getKitsConfig().getConfig(), "KITS." + kit + ".ARMOR");
//                armor = InventoryUtils.itemStackArrayFromBase64(configCursor.getString(kit + ".ARMOR"));
                inventory = InventoryUtils.toInventory(FrozedGKits.getInstance().getKitsConfig().getConfig(), "KITS." + kit + ".INVENTORY");
//                inventory = InventoryUtils.fromBase64(configCursor.getString(kit + ".INVENTORY"));
            } catch (Exception exception) {
                System.out.println("Error in load kits.");
            }


            Kit newKit = new Kit(kit, icon, slotPosition, color, enabled, armor, inventory);
            newKit.setCooldown(cooldown);
        }

        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccessfully loaded &b" + KitManager.getKits().size() + " &akits."));
    }

    public static void saveKit(String name, Player player) {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        configCursor.set(name + ".ICON", Kit.getKitByName(name).getIcon());
        configCursor.set(name + ".SLOT", Kit.getKitByName(name).getSlotPosition());
        configCursor.set(name + ".COLOR", Kit.getKitByName(name).getColor());
        configCursor.set(name + ".COOLDOWN", Kit.getKitByName(name).getCooldown());
        configCursor.set(name + ".ENABLED", Kit.getKitByName(name).isEnabled());
        InventoryUtils.saveInventory(player.getInventory(), FrozedGKits.getInstance().getKitsConfig().getConfig(), "KITS." + name + ".INVENTORY");
        InventoryUtils.saveItemStacks(player.getInventory().getArmorContents(), FrozedGKits.getInstance().getKitsConfig().getConfig(), "KITS." + name + ".ARMOR");
//        configCursor.set(name + ".ARMOR", InventoryUtils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
//        configCursor.set(name + ".INVENTORY", InventoryUtils.toBase64(player.getInventory()));
        configCursor.save();

        loadKits();
    }

    public static void deleteKit(Kit name) {
//        kits.remove(name);
//        FrozedGKits.getInstance().getKitsConfig().getFile().delete();
//        FrozedGKits.getInstance().createKitsConfig();
//        saveKits();
    }

    public static void saveKits() {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        kits.forEach(kit -> {
            configCursor.set(kit.getName() + ".ICON", kit.getIcon());
            configCursor.set(kit.getName() + ".SLOT", kit.getSlotPosition());
            configCursor.set(kit.getName() + ".COLOR", kit.getColor());
            configCursor.set(kit.getName() + ".COOLDOWN", kit.getCooldown());
            configCursor.set(kit.getName() + ".ENABLED", kit.isEnabled());
            InventoryUtils.saveInventory(kit.getInventory(), FrozedGKits.getInstance().getKitsConfig().getConfig(), "KITS." + kit.getName() + ".INVENTORY");
            InventoryUtils.saveItemStacks(kit.getArmor(), FrozedGKits.getInstance().getKitsConfig().getConfig(), "KITS." + kit.getName() + ".ARMOR");
//                configCursor.set(kit.getName() + ".ARMOR", InventoryUtils.itemStackArrayToBase64(kit.getArmor()));
//                configCursor.set(kit.getName() + ".INVENTORY", InventoryUtils.toBase64(kit.getInventory()));
            configCursor.save();
        });
    }
}
