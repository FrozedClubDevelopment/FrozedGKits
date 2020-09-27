package club.frozed.gkit.commands;

import club.frozed.gkit.provider.selection.KitSelectionMenu;
import club.frozed.gkit.utils.command.BaseCommand;
import club.frozed.gkit.utils.command.Command;
import club.frozed.gkit.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 18:06
 */
public class GkitCommand extends BaseCommand {
    @Command(name = "gkit", aliases = {"kits", "gkits"}, permission = "frozedgkits.use")

    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        new KitSelectionMenu().openMenu(player);
    }
}
