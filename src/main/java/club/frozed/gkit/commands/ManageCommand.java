package club.frozed.gkit.commands;

import club.frozed.gkit.FrozedGKits;
import club.frozed.gkit.kit.Kit;
import club.frozed.gkit.kit.KitManager;
import club.frozed.gkit.provider.selection.KitManagerSelectionMenu;
import club.frozed.gkit.utils.chat.Clickable;
import club.frozed.gkit.utils.chat.Color;
import club.frozed.gkit.utils.command.BaseCommand;
import club.frozed.gkit.utils.command.Command;
import club.frozed.gkit.utils.command.CommandArgs;
import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            player.sendMessage(Color.CHAT_BAR);
            player.sendMessage(Color.translate("&b&lFrozedGKits &8- &7v" + FrozedGKits.getInstance().getDescription().getVersion()));
            player.sendMessage(Color.CHAT_BAR);
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager create <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager delete <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager edit <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager save <kitName>"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager list"));
            player.sendMessage(Color.translate("&7 ▶ &b/kitmanager menu"));
            player.sendMessage(Color.CHAT_BAR);
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
                if (Kit.kitExists(kitName)) {
                    player.sendMessage(Color.translate("&aThat kit already exists"));
                    return;
                }

                new Kit(kitName, new ItemStack(Material.ENCHANTED_BOOK), 0, "&b", true, player.getInventory().getArmorContents(), player.getInventory());
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
                player.sendMessage(Color.translate("&aSuccessfully deleted &f" + kitName + " &akit"));
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

                Kit kitManager = Kit.getKitByName(kitName);
                if (kitManager == null) {
                    player.sendMessage(Color.translate("&cCouldn't find kit"));
                    return;
                }

                player.setGameMode(GameMode.CREATIVE);
                player.getInventory().setContents(kitManager.getInventory().getContents());
                player.getInventory().setArmorContents(kitManager.getArmor());

                Clickable clickable = new Clickable(
                        "&8[&bClick to Save&8]",
                        "&7Click to save kit", "/kitmanager save " + kitName
                );
                clickable.sendToPlayer(player);
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

                Kit kits = Kit.getKitByName(kitName);
                if (kits == null) {
                    player.sendMessage(Color.translate("&cCouldn't find kit"));
                    return;
                }

                KitManager.saveKit(kitName, player);
                player.sendMessage(Color.translate("&aSuccessfully saved &f" + kitName + " &akit."));
                //data.getInventory().clear();
                break;
            case "list":
                List<String> list = new ArrayList<>();

                list.add(Color.SB_BAR);
                list.add(Color.translate("&aAvailable Kits:"));

                for (Kit kit : KitManager.getKits()) {
                    list.add(Color.translate("&b ▶ &7{&f" + kit.getName() + "&7} &7▶ " + (kit.isEnabled() ? "&aenabled." : "&cdisabled.")));
                }

                list.add(Color.SB_BAR);
                player.sendMessage(StringUtils.join(list, "\n"));
                break;
            case "menu":
                new KitManagerSelectionMenu().openMenu(player);
                break;
        }
    }
}
