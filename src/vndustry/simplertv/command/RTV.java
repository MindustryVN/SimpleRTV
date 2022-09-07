package vndustry.simplertv.command;

import arc.struct.Seq;
import mindustry.Vars;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.maps.Map;
import vndustry.simplertv.VoteHandler;

public class RTV extends BaseCommand {

    public RTV(String[] args, Player player) {
        super(args, player);
        //Why Java add this shit in my class ...
    }

    @Override
    public void execute(String[] args, Player player) {
        if (args.length != 1) {
            return;
        }
        int map_id;
        try {
            map_id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("[red]Map id must be a number");
            return;
        }
        Seq<Map> maps = new Seq<>();
        maps.addAll(Vars.maps.customMaps());
        if (map_id < 0 || map_id > (maps.size - 1)) {
            player.sendMessage("[red]Invalid map id");
            return;
        }
        if (VoteHandler.isVoted(player, map_id)){
            Call.sendMessage("[red]RTV: " + player.name + " [accent]removed their vote for [yellow]" + maps.get(map_id).name());
            VoteHandler.removeVote(player, map_id);
            return;
        }
        VoteHandler.vote(player, map_id);
        Call.sendMessage("[red]RTV: [accent]" + player.name() + " [white]Want to change map to [yellow]" + maps.get(map_id).name());
        Call.sendMessage("[red]RTV: [white]Current Vote for [yellow]" + maps.get(map_id).name() + "[white]: [green]" + VoteHandler.getVoteCount(map_id) + "/" + VoteHandler.getRequire());
        Call.sendMessage("[red]RTV: [white]Use [yellow]/rtv " + map_id + " [white]to add your vote to this map !");
        VoteHandler.check(map_id);
    }
}
