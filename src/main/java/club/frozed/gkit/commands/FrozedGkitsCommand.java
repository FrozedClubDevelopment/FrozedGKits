package club.frozed.gkit.commands;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.command.BaseCommand;
import club.frozed.gkit.utils.command.Command;
import club.frozed.gkit.utils.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 19:23
 */
public class FrozedGkitsCommand extends BaseCommand {
    @Command(name = "frozedgkits", aliases = {"frozedgkit", "frozedkits", "frozedclub", "fcd", "elb1to", "ryzeon"})

    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        player.sendMessage(Color.CHAT_BAR);
        player.sendMessage(Color.translate("&b&lFrozedGKits &8- &7v" + FrozedGKits.getInstance().getDescription().getVersion()));
        player.sendMessage(Color.CHAT_BAR);
        player.sendMessage(Color.translate("&7 ▶ &bDevelopers&8: &fElb1to, Ryzeon"));
        player.sendMessage(Color.translate("&7 ▶ &bWebsite&8: &fwww.frozed.club"));
        player.sendMessage(Color.translate("&7 ▶ &bDiscord&8: &fdiscord.frozed.club"));
        player.sendMessage(Color.CHAT_BAR);
    }
}
