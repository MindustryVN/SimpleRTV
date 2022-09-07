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
        Seq<Map> maps = new Seq<>();
        maps.addAll(Vars.maps.customMaps());
        int page = 1;
        int max_page = (maps.size / 5);
        if (args.length == 1){
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("[red]Page must be a number");
                return;
            }
        }
        if (page < 1 || page > max_page){
            player.sendMessage("[red]Invalid page");
            return;
        }
        player.sendMessage("[green]Available maps: [white](" + page + "/" + max_page + ")");
        for (int i = 0; i < 5; i++){
            int map_id = (page - 1) * 5 + i;
            if (map_id > maps.size - 1){
                break;
            }
            player.sendMessage("[green]" + map_id + " [white]- [yellow]" + maps.get(map_id).name());
        }
    }
}
