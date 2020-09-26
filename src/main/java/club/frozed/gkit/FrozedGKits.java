package club.frozed.gkit;

import club.frozed.gkit.commands.FrozedGkitsCommand;
import club.frozed.gkit.commands.GkitCommand;
import club.frozed.gkit.commands.ManageCommand;
import club.frozed.gkit.managers.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.command.CommandFramework;
import club.frozed.gkit.utils.config.FileConfig;
import club.frozed.gkit.utils.menu.ButtonListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public final class FrozedGKits extends JavaPlugin {

    @Getter private static FrozedGKits instance;
    private CommandFramework commandFramework;

    private FileConfig pluginConfig, kitsConfig;
    private KitManager kitManager;

    @Override
    public void onEnable() {
        instance = this;

        if (!this.getDescription().getAuthors().contains("Elb1to") || !this.getDescription().getAuthors().contains("Ryzeon") ||
                !this.getDescription().getAuthors().contains("FrozedClubDevelopment") || !this.getDescription().getWebsite().contains("www.frozed.club") ||
                !this.getDescription().getName().equals("FrozedGKits")) {
            Bukkit.getPluginManager().disablePlugins();
        }

        commandFramework = new CommandFramework(this);
        pluginConfig = new FileConfig(this, "config.yml");
        kitsConfig = new FileConfig(this, "kits.yml");

        KitManager.loadKits();
        Bukkit.getPluginManager().registerEvents(new ButtonListener(), this);

        new GkitCommand();
        new ManageCommand();
        new FrozedGkitsCommand();

        this.getServer().getConsoleSender().sendMessage(Color.CHAT_BAR);
        this.getServer().getConsoleSender().sendMessage(Color.translate("&b&lFrozedGKits &8- &7v") + getDescription().getVersion());
        this.getServer().getConsoleSender().sendMessage(Color.translate("&b ▶ &aLoaded files: config.yml, kits.yml"));
        this.getServer().getConsoleSender().sendMessage(Color.translate("&b ▶ &aCommands, Listeners & Managers registered."));
        this.getServer().getConsoleSender().sendMessage(Color.CHAT_BAR);
    }

    @Override
    public void onDisable() {
        KitManager.saveKits();
        instance = null;
    }
}
