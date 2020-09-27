package club.frozed.gkit.provider.data;

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
        PlayerData playerData = PlayerData.getByName(e.getName());
        if (playerData == null) {
            playerData = new PlayerData(e.getName());
        }
    }

    private void handledSaveDate(Player player) {
        PlayerData playerData = PlayerData.getByName(player.getName());
        playerData.deletePlayer();
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        handledSaveDate(e.getPlayer());
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent e) {
        handledSaveDate(e.getPlayer());
    }
}

