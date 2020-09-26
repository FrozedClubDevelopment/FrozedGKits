package club.frozed.gkit.kit;

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
        if (KitManager.getKitNameState().contains(event.getPlayer().getName())) {
            event.setCancelled(true);

            new KitManager(event.getMessage(), new ItemStack(Material.ENCHANTED_BOOK), 0, "&b", true, player.getInventory().getContents(), player.getInventory().getArmorContents());
            player.sendMessage(Color.translate("&aSuccessfully created &f" + event.getMessage() + " &akit"));

            KitManager.getKitNameState().remove(player.getName());
        }
    }
}
