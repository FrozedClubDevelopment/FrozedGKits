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
@Getter @Setter
public class KitManager {

    @Getter private static List<KitManager> kits = new ArrayList<>();

    private String name;
    private ItemStack icon;
    private int slotPosition;
    private String color;
    private boolean enabled;

    private ItemStack[] armor;
    private ItemStack[] inventory;

    public KitManager(String name, ItemStack icon, int slotPosition, String color, boolean enabled, ItemStack[] armor, ItemStack[] itemStacks) {
        this.name = name;
        this.icon = icon;
        this.slotPosition = slotPosition;
        this.color = color;
        this.enabled = enabled;

        this.armor = armor;
        this.inventory = itemStacks;

        kits.add(this);
    }

    public static void loadKits() {
        kits.clear();

        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");
        for (String kit : FrozedGKits.getInstance().getKitsConfig().getConfig().getConfigurationSection("KITS").getKeys(false)) {
            ItemStack icon = new ItemStack(Material.valueOf(FrozedGKits.getInstance().getKitsConfig().getConfig().getString("KITS." + kit + ".ICON")));
            int slotPosition = FrozedGKits.getInstance().getKitsConfig().getConfig().getInt("KITS." + kit + ".SLOT");
            String color = FrozedGKits.getInstance().getKitsConfig().getConfig().getString("KITS." + kit + ".COLOR");
            boolean enabled = FrozedGKits.getInstance().getKitsConfig().getConfig().getBoolean("KITS." + kit + ".ENABLED");

            ItemStack[] armor = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".ARMOR"));
            ItemStack[] inventory = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".INVENTORY"));

            new KitManager(kit, icon, slotPosition, color, enabled, armor, inventory);
        }

        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccessfully loaded &b" + KitManager.getKits().size() + " &akits."));
    }

    public static void saveKit(String name, Player player) {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        configCursor.set(name + ".ICON", getKitByName(name).getIcon().getType().name());
        configCursor.set(name + ".SLOT", getKitByName(name).getSlotPosition());
        configCursor.set(name + ".COLOR", getKitByName(name).getColor());
        configCursor.set(name + ".ENABLED", getKitByName(name).isEnabled());
        configCursor.set(name + ".ARMOR", InventoryUtils.serializeInventory(player.getInventory().getArmorContents()));
        configCursor.set(name + ".INVENTORY", InventoryUtils.serializeInventory(player.getInventory().getContents()));
        configCursor.save();

        loadKits();
    }

    public static void deleteKit(String name) {
        kits.remove(getKitByName(name));
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
                configCursor.set(kit.getName() + ".ENABLED", kit.isEnabled());
                configCursor.set(kit.getName() + ".ARMOR", InventoryUtils.serializeInventory(kit.getArmor()));
                configCursor.set(kit.getName() + ".INVENTORY", InventoryUtils.serializeInventory(kit.getInventory()));
            });
        }
    }

    public static KitManager getKitByName(String name) {
        return kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static boolean kitExists(String kit) {
        return kits.contains(getKitByName(kit));
    }
}
