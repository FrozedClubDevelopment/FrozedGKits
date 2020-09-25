package club.frozed.gkit.managers;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.config.ConfigCursor;
import club.frozed.gkit.utils.items.InventoryUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
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

    @Getter private static final List<KitManager> kits = new ArrayList<>();

    private final ItemStack[] inventory;
    private final ItemStack[] armor;
    private final String name;

    public KitManager(String name, ItemStack[] itemStacks, ItemStack[] armor) {
        this.name = name;
        this.inventory = itemStacks;
        this.armor = armor;
        kits.add(this);
    }

    public static void loadKits() {
        kits.clear();
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "kits");
        for (String kit : FrozedGKits.getInstance().getKitsConfig().getConfig().getConfigurationSection("kits").getKeys(false)) {
            ItemStack[] items = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".items"));
            ItemStack[] armor = InventoryUtils.deserializeInventory(configCursor.getString(kit + ".armor"));
            new KitManager(kit, items, armor);
        }
        Bukkit.getConsoleSender().sendMessage(Color.translate("&aSuccessfully loaded &b" + KitManager.getKits().size() + " &akits."));
    }

    public static void saveKit(String name, Player player) {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "kits");
        configCursor.set(name + ".items", InventoryUtils.serializeInventory(player.getInventory().getContents()));
        configCursor.set(name + ".armor", InventoryUtils.serializeInventory(player.getInventory().getArmorContents()));
        configCursor.save();
        loadKits();
    }

    public static void deleteKit(String name) {
        kits.remove(getKitByName(name));
    }

    public static void saveKits() {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "kits");
        if (kits.isEmpty()) {
            FrozedGKits.getInstance().getKitsConfig().getConfig().set("kits", null);
        } else {
            kits.forEach(kit -> {
                configCursor.set(kit.getName() + ".items", InventoryUtils.serializeInventory(kit.getInventory()));
                configCursor.set(kit.getName() + ".armor", InventoryUtils.serializeInventory(kit.getArmor()));
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
