package vndustry.simplertv.command;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.maps.Map;

public class Maps extends BaseCommand{

    public Maps(String[] args, Player player) {
        super(args, player);
    }

    @Override
    public void execute(String[] args, Player player) {
        player.sendMessage("[green]List maps:");
        Seq<Map> maps = new Seq<>();
        maps.addAll(Vars.maps.customMaps());
        for (int i = 0; i < maps.size; i++){
            player.sendMessage("[white]" + i + " - [yellow] " + maps.get(i).name());
        }
    }
}
