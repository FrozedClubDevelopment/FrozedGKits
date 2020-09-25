package club.frozed.gkit.utils.items;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 17:58
 */
public class InventoryUtils {

    public static ItemStack[] deepClone(ItemStack[] origin) {
        Preconditions.checkNotNull((Object) origin, "Origin cannot be null");
        ItemStack[] cloned = new ItemStack[origin.length];
        for (int i = 0; i < origin.length; ++i) {
            ItemStack next = origin[i];
            cloned[i] = next == null ? null : next.clone();
        }
        return cloned;
    }

    public static int getSafestInventorySize(int initialSize) {
        return (initialSize + 8) / 9 * 9;
    }

    public static int repairItem(ItemStack item) {
        if (item == null) {
            return 0;
        }
        Material material = Material.getMaterial(item.getTypeId());
        if (material.isBlock() || material.getMaxDurability() < 1) {
            return 0;
        }
        if (item.getDurability() <= 0) {
            return 0;
        }
        item.setDurability((short) 0);
        return 1;
    }

    public static void removeItem(Inventory inventory, Material type, short data, int quantity) {
        ItemStack[] contents = inventory.getContents();
        boolean compareDamage = type.getMaxDurability() == 0;
        block0:
        for (int i = quantity; i > 0; --i) {
            for (ItemStack content : contents) {
                if (content == null || content.getType() != type || compareDamage && content.getData().getData() != data)
                    continue;
                if (content.getAmount() <= 1) {
                    inventory.removeItem(content);
                    continue block0;
                }
                content.setAmount(content.getAmount() - 1);
                continue block0;
            }
        }
    }

    public static int countAmount(Inventory inventory, Material type, short data) {
        ItemStack[] contents = inventory.getContents();
        boolean compareDamage = type.getMaxDurability() == 0;
        int counter = 0;
        for (ItemStack item : contents) {
            if (item == null || item.getType() != type || compareDamage && item.getData().getData() != data) continue;
            counter += item.getAmount();
        }
        return counter;
    }

    public static boolean isEmpty(Inventory inventory) {
        return isEmpty(inventory, true);
    }

    public static boolean isEmpty(Inventory inventory, boolean checkArmour) {
        ItemStack[] contents;
        boolean result = true;
        for (ItemStack content : contents = inventory.getContents()) {
            if (content == null || content.getType() == Material.AIR) continue;
            result = false;
            break;
        }
        if (!result) return false;
        if (checkArmour && inventory instanceof PlayerInventory) {
            for (ItemStack content : contents = ((PlayerInventory) inventory).getArmorContents()) {
                if (content == null || content.getType() == Material.AIR) continue;
                result = false;
                break;
            }
        }
        return result;
    }

    public static boolean clickedTopInventory(InventoryDragEvent event) {
        InventoryView view = event.getView();
        Inventory topInventory = view.getTopInventory();
        if (topInventory == null) {
            return false;
        }
        boolean result = false;
        int size = topInventory.getSize();
        for (Integer entry : event.getNewItems().keySet()) {
            if (entry >= size) continue;
            result = true;
            break;
        }
        return result;
    }

    public static String serializeInventory(ItemStack[] source) {
        StringBuilder builder = new StringBuilder();

        for (ItemStack itemStack : source) {
            builder.append(serializeItemStack(itemStack));
            builder.append(";");
        }

        return builder.toString();
    }

    public static ItemStack[] deserializeInventory(String source) {
        List<ItemStack> items = new ArrayList<>();
        String[] split = source.split(";");

        for (String piece : split) {
            items.add(deserializeItemStack(piece));
        }

        return items.toArray(new ItemStack[items.size()]);
    }

    public static void fillInventory(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                inventory.setItem(i, new ItemCreator(Material.STAINED_GLASS_PANE, 7).setName(" ").get());
            }
        }
    }

    public static String serializeItemStack(ItemStack item) {
        StringBuilder builder = new StringBuilder();

        if (item == null) {
            return "null";
        }

        String isType = String.valueOf(item.getType().getId());
        builder.append("t@").append(isType);

        if (item.getDurability() != 0) {
            String isDurability = String.valueOf(item.getDurability());
            builder.append(":d@").append(isDurability);
        }

        if (item.getAmount() != 1) {
            String isAmount = String.valueOf(item.getAmount());
            builder.append(":a@").append(isAmount);
        }

        Map<Enchantment, Integer> isEnch = item.getEnchantments();

        if (isEnch.size() > 0) {
            for (Map.Entry<Enchantment, Integer> ench : isEnch.entrySet()) {
                builder.append(":e@").append(ench.getKey().getId()).append("@").append(ench.getValue());
            }
        }

        if (item.hasItemMeta()) {
            ItemMeta imeta = item.getItemMeta();

            if (imeta.hasDisplayName()) {
                builder.append(":dn@").append(imeta.getDisplayName());
            }

            if (imeta.hasLore()) {
                builder.append(":l@").append(imeta.getLore());
            }
        }

        return builder.toString();
    }

    public static ItemStack deserializeItemStack(String in) {
        ItemStack item = null;
        ItemMeta meta = null;

        if (in.equals("null")) {
            return new ItemStack(Material.AIR);
        }

        String[] split = in.split(":");

        for (String itemInfo : split) {
            String[] itemAttribute = itemInfo.split("@");
            String s2 = itemAttribute[0];

            switch (s2) {
                case "t": {
                    item = new ItemStack(Material.getMaterial(Integer.parseInt(itemAttribute[1])));
                    meta = item.getItemMeta();
                    break;
                }
                case "d": {
                    if (item != null) {
                        item.setDurability(Short.parseShort(itemAttribute[1]));
                        break;
                    }
                    break;
                }
                case "a": {
                    if (item != null) {
                        item.setAmount(Integer.parseInt(itemAttribute[1]));
                        break;
                    }
                    break;
                }
                case "e": {
                    if (item != null) {
                        item.addEnchantment(
                                Enchantment.getById(Integer.parseInt(itemAttribute[1])),
                                Integer.parseInt(itemAttribute[2])
                        );
                        break;
                    }
                    break;
                }
                case "dn": {
                    if (meta != null) {
                        meta.setDisplayName(itemAttribute[1]);
                        break;
                    }
                    break;
                }
                case "l": {
                    itemAttribute[1] = itemAttribute[1].replace("[", "");
                    itemAttribute[1] = itemAttribute[1].replace("]", "");
                    List<String> lore = Arrays.asList(itemAttribute[1].split(","));

                    for (int x = 0; x < lore.size(); ++x) {
                        String s = lore.get(x);

                        if (s != null) {
                            if (s.toCharArray().length != 0) {
                                if (s.charAt(0) == ' ') {
                                    s = s.replaceFirst(" ", "");
                                }

                                lore.set(x, s);
                            }
                        }
                    }

                    if (meta != null) {
                        meta.setLore(lore);
                        break;
                    }

                    break;
                }
            }
        }

        if (meta != null && (meta.hasDisplayName() || meta.hasLore())) {
            item.setItemMeta(meta);
        }

        return item;
    }
}
