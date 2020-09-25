package club.frozed.gkit.commands;

import club.frozed.gkit.utils.command.BaseCommand;
import club.frozed.gkit.utils.command.Command;
import club.frozed.gkit.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 18:09
 */
public class ManageCommand extends BaseCommand {
    @Command(name = "managekits", aliases = {"managegkits", "kitsmanager", "gkitsmanager", "gkitmanager"}, permission = "frozedgkits.admin")

    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();
    }
}
