package vndustry.simplertv;

import arc.ApplicationListener;
import arc.Core;
import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;
import mindustry.server.ServerControl;

import java.util.LinkedList;

public class VoteHandler {
    public static LinkedList<String>[] votes;
    public static double ratio = 0.6;

    public static void reset(){
        for (int i = 0; i < votes.length; i++){
            votes[i] = new LinkedList<>();
        }
    }

    public static void vote(Player player, int map_id){
        votes[map_id].push(player.uuid());
    }

    public static void removeVote(Player player, int map_id){
        votes[map_id].remove(player.uuid());
    }

    public static boolean isVoted(Player player, int map_id){
        return votes[map_id].contains(player.uuid());
    }

    public static int getRequire(){
        return (int) Math.ceil(ratio * Groups.player.size());
    }

    public static int getVoteCount(int map_id){
        return votes[map_id].size();
    }

    public static void check(int map_id){
        if (getVoteCount(map_id) >= getRequire()){
            Seq<Map> maps = new Seq<>();
            maps.addAll(Vars.maps.customMaps());
            Call.sendMessage("[red]RTV: [green]Vote passed! Changing map...");
            ApplicationListener[] listeners = Core.app.getListeners().toArray();
            for (ApplicationListener listener: listeners) {
                if (listener instanceof ServerControl){
                    ((ServerControl) listener).setNextMap(maps.get(map_id));
                    reset();
                    Events.fire(new EventType.GameOverEvent(Team.crux));
                    return;
                }
            }
        }

    }
}
