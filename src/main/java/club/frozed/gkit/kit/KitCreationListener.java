package club.frozed.gkit.kit;

import club.frozed.gkit.provider.edition.KitManagerEditionMenu;
import club.frozed.gkit.utils.DateUtils;
import club.frozed.gkit.utils.Utils;
import club.frozed.gkit.utils.chat.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/26/2020 @ 14:08
 */
public class KitCreationListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerKitCreation(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (KitManager.getKitNameState().contains(player.getUniqueId())) {
            event.setCancelled(true);

            Kit kit = new Kit(event.getMessage(), "ENCHANTED_BOOK", 0, "&b", true, player.getInventory().getArmorContents(), player.getInventory());
            player.sendMessage(Color.translate("&aSuccessfully created &f" + event.getMessage() + " &akit"));
            kit.saveKit(player);
            KitManager.getKitNameState().remove(player.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCooldownCreation(AsyncPlayerChatEvent event) {
        if (KitManagerEditionMenu.cooldownProcessPlayer.containsKey(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            Kit kit = KitManagerEditionMenu.cooldownProcessPlayer.get(event.getPlayer().getUniqueId());
            long duration;
            try {
                duration = DateUtils.getDuration(event.getMessage());
            } catch (Exception e) {
                event.getPlayer().sendMessage(Color.translate("&cThe duration isn't valid."));
                return;
            }
            kit.setCooldown(duration);
            event.getPlayer().sendMessage(Color.translate("&aSuccess! &7You have updated the &a" + kit.getName() + " GKit &7cooldown to &a" + Utils.formatTimeMillis(duration)));
            KitManagerEditionMenu.cooldownProcessPlayer.remove(event.getPlayer().getUniqueId());
            kit.saveKit(event.getPlayer());
            new KitManagerEditionMenu(kit).openMenu(event.getPlayer());
        }
    }
}
