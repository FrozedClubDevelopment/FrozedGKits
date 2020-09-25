package club.frozed.gkit;

import club.frozed.gkit.utils.command.CommandFramework;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public final class FrozedGKits extends JavaPlugin {

    @Getter private static FrozedGKits instance;
    private CommandFramework commandFramework;

    @Override
    public void onEnable() {
        instance = this;
        commandFramework = new CommandFramework(this);
    }

    @Override
    public void onDisable() {
        
    }
}
