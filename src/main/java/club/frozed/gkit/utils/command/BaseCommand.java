package club.frozed.gkit.utils.command;

import club.frozed.gkit.FrozedGKits;

public abstract class BaseCommand {

    public FrozedGKits main = FrozedGKits.getInstance();

    public BaseCommand() {
        main.getCommandFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs command);

}
