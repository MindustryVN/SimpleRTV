package vndustry.simplertv.command;

import mindustry.gen.Player;

public abstract class BaseCommand {

    public BaseCommand(String[] args, Player player){
        execute(args, player);
    }

    public abstract void execute(String[] args, Player player);
}
