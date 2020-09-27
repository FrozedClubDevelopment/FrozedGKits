package club.frozed.gkit.provider.player;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.utils.DateUtils;
import club.frozed.gkit.utils.config.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

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

    public PlayerData(String string){
        this.name = string;
        playersData.add(this);
    }


    public static PlayerData getByName(String name){
        return PlayerData.getPlayersData().stream().filter(playerData -> playerData.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
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

    public void deletePlayer(){
        playersData.remove(this);
    }

    public void saveCooldown(Long addedDate, Kit kit){

        FileConfig playerConfig = FrozedGKits.getInstance().getDataConfig();

        playerConfig.getConfig().set(this.name + ".COOLDOWNS." +kit.getName()+".ADDED-DATE", addedDate);
        playerConfig.save();
    }

    public Long getAddedCooldownDate(Kit kit){
        return FrozedGKits.getInstance().getDataConfig().getConfig().getLong(this.name+".COOLDOWNS."+kit.getName() +".ADDED-DATE");
    }
}
