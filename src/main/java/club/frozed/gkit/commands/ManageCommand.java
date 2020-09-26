package club.frozed.gkit.commands;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.managers.KitManager;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.command.BaseCommand;
import club.frozed.gkit.utils.command.Command;
import club.frozed.gkit.utils.command.CommandArgs;
import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elb1to
 * Project: FrozedGKits
 * Date: 09/25/2020 @ 18:09
 */
public class ManageCommand extends BaseCommand {
    @Command(name = "managekits", aliases = {"managegkits", "kitsmanager", "kitmanager", "gkitsmanager", "gkitmanager"}, permission = "frozedgkits.admin")

    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            player.sendMessage(Color.SB_BAR);
            player.sendMessage(Color.translate("&b&lFrozedGKits &8- &7v" + FrozedGKits.getInstance().getDescription().getVersion()));
            player.sendMessage(Color.SB_BAR);
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager create <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager delete <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager edit <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager save <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager list"));
            player.sendMessage(Color.SB_BAR);
            return;
        }

        String kitName;
        switch (args[0]) {
            case "create":
                if (args.length < 2) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                kitName = args[1];
                if (kitName == null) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }
                if (KitManager.kitExists(kitName)) {
                    player.sendMessage(Color.translate("&aThat kit already exists"));
                    return;
                }
                new KitManager(kitName, player.getInventory().getContents(), player.getInventory().getArmorContents());
                player.sendMessage(Color.translate("&aSuccessfully created &f" + kitName + " &akit"));
                break;
            case "delete":
                if (args.length < 2) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                kitName = args[1];
                if (kitName == null) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                KitManager.deleteKit(kitName);
                break;
            case "edit":
                if (args.length < 2) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                kitName = args[1];
                if (kitName == null) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                KitManager kitManager = KitManager.getKitByName(kitName);
                if (kitManager == null) {
                    player.sendMessage(Color.translate("&cCouldn't find kit"));
                    return;
                }

                player.setGameMode(GameMode.CREATIVE);
                player.getInventory().setContents(kitManager.getInventory());
                player.getInventory().setArmorContents(kitManager.getArmor());
                player.sendMessage(Color.translate("&aUse /kit save <kit> to save kit"));
                break;
            case "save":
                if (args.length < 2) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                kitName = args[1];
                if (kitName == null) {
                    player.sendMessage(Color.translate("&cPlease specify a kit name"));
                    return;
                }

                KitManager kits = KitManager.getKitByName(kitName);
                if (kits == null) {
                    player.sendMessage(Color.translate("&cCouldn't find kit"));
                    return;
                }

                KitManager.saveKit(kitName, player);
                player.sendMessage(Color.translate("&aSuccessfully saved &f" + kitName + " &akit."));
                player.getInventory().clear();
                break;
            case "list":
                List<String> list = new ArrayList<>();

                list.add(Color.SB_BAR);
                list.add(Color.translate("&aAvailable Kits:"));

                for (KitManager kit : KitManager.getKits()) {
                    list.add(Color.translate("&b ▶ &7{&f" + kit.getName() + "&7}"));
                }

                list.add(Color.SB_BAR);
                player.sendMessage(StringUtils.join(list, "\n"));
                break;
        }
    }
}
