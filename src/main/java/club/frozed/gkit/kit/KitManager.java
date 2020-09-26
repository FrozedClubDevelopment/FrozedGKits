package club.frozed.gkit.kit;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.config.ConfigCursor;
import club.frozed.gkit.utils.items.InventoryUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
public class KitManager {

    @Getter
    private static final List<KitManager> kits = new ArrayList<>();

    private final String name;
    private final String icon;
    private final int slotPosition;
    private final ChatColor color;
    private final boolean enabled;

    private final ItemStack[] armor;
    private final ItemStack[] inventory;

    public KitManager(String name, String icon, int slotPosition, ChatColor color, boolean enabled, ItemStack[] armor, ItemStack[] itemStacks) {
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
            String icon = FrozedGKits.getInstance().getKitsConfig().getConfig().getString(configCursor + kit + ".ICON");
            int slotPosition = FrozedGKits.getInstance().getKitsConfig().getConfig().getInt(configCursor + kit + ".SLOT");
            ChatColor color = ChatColor.valueOf(FrozedGKits.getInstance().getKitsConfig().getConfig().getString(configCursor + kit + ".COLOR"));
            boolean enabled = FrozedGKits.getInstance().getKitsConfig().getConfig().getBoolean(configCursor + kit + ".ENABLED");
            ItemStack[] armor = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".ARMOR"));
            ItemStack[] inventory = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".INVENTORY"));

            new KitManager(kit, icon, slotPosition, color, enabled, armor, inventory);
        }

        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccessfully loaded &b" + KitManager.getKits().size() + " &akits."));
    }

    public static void saveKit(String name, Player player) {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        configCursor.set(name + ".ICON", configCursor.getString(name + ".ICON"));
        configCursor.set(name + ".SLOT", configCursor.getInt(name + ".SLOT"));
        configCursor.set(name + ".COLOR", configCursor.getString(name + ".COLOR"));
        configCursor.set(name + ".ENABLED", configCursor.getBoolean(name + ".ENABLED"));
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
                configCursor.set(kit.getName() + ".ICON", configCursor.getString(kit.getIcon()));
                configCursor.set(kit.getName() + ".SLOT", FrozedGKits.getInstance().getKitsConfig().getConfig().getInt(kit.getSlotPosition()));
                configCursor.set(kit.getName() + ".COLOR", FrozedGKits.getInstance().getKitsConfig().getConfig().getString(kit.getColor()));
                configCursor.set(kit.getName() + ".ENABLED", FrozedGKits.getInstance().getKitsConfig().getConfig().getBoolean(kit.getColor()));
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
