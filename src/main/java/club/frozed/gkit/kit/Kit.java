package club.frozed.gkit.kit;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.utils.config.ConfigCursor;
import club.frozed.gkit.utils.items.InventoryUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Ryzeon
 * Project: FrozedGKits
 * Date: 26/09/2020 @ 18:07
 */
@Getter
@Setter
public class Kit {

    private String name;
    private ItemStack icon;
    private int slotPosition;
    private String color;
    private boolean enabled;
    private long cooldown = 0L;

    private ItemStack[] armor;
    private Inventory inventory;

    public Kit(String name, ItemStack icon, int slotPosition, String color, boolean enabled, ItemStack[] armor, Inventory inventory) {
        this.name = name;
        this.icon = icon;
        this.slotPosition = slotPosition;
        this.color = color;
        this.enabled = enabled;

        this.armor = armor;
        this.inventory = inventory;

        KitManager.getKits().add(this);
    }

    public static Kit getKitByName(String name) {
        return KitManager.getKits().stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void saveKit(Player player) {
        ConfigCursor configCursor = new ConfigCursor(FrozedGKits.getInstance().getKitsConfig(), "KITS");

        configCursor.set(this.name + ".ICON", getIcon().getType().name());
        configCursor.set(this.name  + ".SLOT", getSlotPosition());
        configCursor.set(this.name  + ".COLOR", getColor());
        configCursor.set(this.name  + ".COOLDOWN", getCooldown());
        configCursor.set(this.name  + ".ENABLED", isEnabled());
        configCursor.set(name + ".ARMOR", InventoryUtils.itemStackArrayToBase64(player.getInventory().getArmorContents()));
        configCursor.set(name + ".INVENTORY", InventoryUtils.toBase64(player.getInventory()));
        configCursor.save();

        KitManager.loadKits();
    }

    public static boolean kitExists(String kit) {
        return KitManager.getKits().contains(getKitByName(kit));
    }
}
