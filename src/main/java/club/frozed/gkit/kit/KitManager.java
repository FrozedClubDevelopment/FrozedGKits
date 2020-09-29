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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 18:06
 */
@Getter
@Setter
public class KitManager {

    @Getter private static List<Kit> kits = new ArrayList<>();
    @Getter private static List<String> kitNameState = new ArrayList<>();

    public static void loadKits() {
        kits.clear();

        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");
        for (String kit : FrozedGKits.getInstance().getKitsConfig().getConfig().getConfigurationSection("KITS").getKeys(false)) {
            ItemStack icon = new ItemStack(Material.valueOf(FrozedGKits.getInstance().getKitsConfig().getConfig().getString("KITS." + kit + ".ICON")));
            int slotPosition = FrozedGKits.getInstance().getKitsConfig().getConfig().getInt("KITS." + kit + ".SLOT");
            String color = FrozedGKits.getInstance().getKitsConfig().getConfig().getString("KITS." + kit + ".COLOR");
            boolean enabled = FrozedGKits.getInstance().getKitsConfig().getConfig().getBoolean("KITS." + kit + ".ENABLED");
            long cooldown = FrozedGKits.getInstance().getKitsConfig().getConfig().getLong("KITS." + kit + ".COOLDOWN");

            ItemStack[] armor = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".ARMOR"));
            ItemStack[] inventory = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".INVENTORY"));

            Kit newKit = new Kit(kit, icon, slotPosition, color, enabled, armor, inventory);
            newKit.setCooldown(cooldown);
        }

        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccessfully loaded &b" + KitManager.getKits().size() + " &akits."));
    }

    public static void saveKit(String name, Player player) {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        configCursor.set(name + ".ICON", Kit.getKitByName(name).getIcon().getType().name());
        configCursor.set(name + ".SLOT", Kit.getKitByName(name).getSlotPosition());
        configCursor.set(name + ".COLOR", Kit.getKitByName(name).getColor());
        configCursor.set(name + ".COOLDOWN", Kit.getKitByName(name).getCooldown());
        configCursor.set(name + ".ENABLED", Kit.getKitByName(name).isEnabled());
        configCursor.set(name + ".ARMOR", InventoryUtils.serializeInventory(player.getInventory().getArmorContents()));
        configCursor.set(name + ".INVENTORY", InventoryUtils.serializeInventory(player.getInventory().getContents()));
        configCursor.save();

        loadKits();
    }

    public static void deleteKit(Kit name) {
        kits.remove(name);
        FrozedGKits.getInstance().getKitsConfig().getFile().delete();
        FrozedGKits.getInstance().createKitsConfig();
        saveKits();
    }

    public static void saveKits() {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        if (kits.isEmpty()) {
            FrozedGKits.getInstance().getKitsConfig().getConfig().set("KITS", null);
        } else {
            kits.forEach(kit -> {
                configCursor.set(kit.getName() + ".ICON", kit.getIcon());
                configCursor.set(kit.getName() + ".SLOT", kit.getSlotPosition());
                configCursor.set(kit.getName() + ".COLOR", kit.getColor());
                configCursor.set(kit.getName() + ".COOLDOWN", kit.getCooldown());
                configCursor.set(kit.getName() + ".ENABLED", kit.isEnabled());
                configCursor.set(kit.getName() + ".ARMOR", InventoryUtils.serializeInventory(kit.getArmor()));
                configCursor.set(kit.getName() + ".INVENTORY", InventoryUtils.serializeInventory(kit.getInventory()));
            });
        }
    }
}
