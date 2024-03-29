package club.frozed.gkit.provider.data;

import club.frozed.gkit.provider.edition.KitManagerEditionMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Ryzeon
 * Project: FrozedGKits
 * Date: 26/09/2020 @ 23:40
 */
public class PlayerLoadListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        PlayerData playerData = PlayerData.getPlayerData(e.getUniqueId());
        if (playerData == null) {
            playerData = new PlayerData(e.getName(), e.getUniqueId());
        }
    }

    private void handledSaveDate(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        playerData.deletePlayer();
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        handledSaveDate(e.getPlayer());
        KitManagerEditionMenu.cooldownProcessPlayer.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent e) {
        handledSaveDate(e.getPlayer());
        KitManagerEditionMenu.cooldownProcessPlayer.remove(e.getPlayer().getUniqueId());
    }
}

