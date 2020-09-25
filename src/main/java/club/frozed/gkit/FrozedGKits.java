package club.frozed.gkit;

import club.frozed.gkit.commands.GkitCommand;
import club.frozed.gkit.commands.ManageCommand;
import club.frozed.gkit.utils.command.CommandFramework;
import club.frozed.gkit.utils.config.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public final class FrozedGKits extends JavaPlugin {

    @Getter private static FrozedGKits instance;
    private CommandFramework commandFramework;

    private FileConfig pluginConfig, kitsConfig;

    @Override
    public void onEnable() {
        instance = this;
        commandFramework = new CommandFramework(this);

        pluginConfig = new FileConfig(this, "config.yml");
        kitsConfig = new FileConfig(this, "kits.yml");

        new GkitCommand();
        new ManageCommand();
    }

    @Override
    public void onDisable() {
        
    }
}
