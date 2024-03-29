package club.frozed.gkit.provider.data;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.utils.DateUtils;
import club.frozed.gkit.utils.config.FileConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by Ryzeon
 * Project: FrozedGKits
 * Date: 26/09/2020 @ 23:04
 */

@Getter @Setter
public class PlayerData {

    @Getter public static List<PlayerData> playersData = new ArrayList<>();

    private String name;
    private UUID uuid;

    public PlayerData(String string, UUID uuid) {
        this.name = string;
        this.uuid = uuid;
        playersData.add(this);
    }

    public static PlayerData getPlayerData(String name) {
        return PlayerData.getPlayersData().stream().filter(playerData -> playerData.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static PlayerData getPlayerData(UUID uuid) {
        return PlayerData.getPlayersData().stream().filter(playerData -> playerData.getUuid() == uuid).findFirst().orElse(null);
    }

    public boolean hasExpired(Kit kit) {
        return (System.currentTimeMillis() >= getAddedCooldownDate(kit) + kit.getCooldown());
    }

    public String getNiceExpire(Kit kit) {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime(new Date(System.currentTimeMillis()));
        to.setTime(new Date(getAddedCooldownDate(kit) + kit.getCooldown()));
        return DateUtils.formatDateDiff(from, to);
    }

    public void deletePlayer() {
        playersData.remove(this);
    }

    public void saveCooldown(Long addedDate, Kit kit) {
        FileConfig playerConfig = FrozedGKits.getInstance().getDataConfig();

        playerConfig.getConfig().set(this.uuid + ".COOLDOWNS." + kit.getName() + ".ADDED-DATE", addedDate);
        playerConfig.save();
    }

    public Long getAddedCooldownDate(Kit kit) {
        return FrozedGKits.getInstance().getDataConfig().getConfig().getLong(this.uuid + ".COOLDOWNS." + kit.getName() + ".ADDED-DATE");
    }
}
