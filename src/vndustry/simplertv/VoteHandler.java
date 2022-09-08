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

public class VoteHandler {
    public static Seq<String>[] votes = new Seq[Vars.maps.customMaps().size];
    public static double ratio = 0.6;

    public static void reset(){
        votes = new Seq[Vars.maps.customMaps().size];
    }

    public static void vote(Player player, int map_id){
        if (votes[map_id] == null){
            votes[map_id] = new Seq<>();
        }
        votes[map_id].add(player.uuid());
    }

    public static void removeVote(Player player, int map_id){
        if (votes[map_id] == null){
            return;
        }
        votes[map_id].remove(player.uuid());
    }

    public static boolean isVoted(Player player, int map_id){
        if (votes[map_id] == null){
            return false;
        }
        return votes[map_id].contains(player.uuid());
    }

    public static int getRequire(){
        return (int) Math.ceil(ratio * Groups.player.size());
    }

    public static int getVoteCount(int map_id){
        if (votes[map_id] == null){
            return 0;
        }
        return votes[map_id].size;
    }

    public static void check(int map_id){
        if (getVoteCount(map_id) >= getRequire()){
            Seq<Map> maps = new Seq<>();
            maps.addAll(Vars.maps.customMaps());
            Call.sendMessage("[red]RTV: [green]Bỏ phiếu thành công. Tiến hành chuyển map...");
            Seq<ApplicationListener> listeners = Core.app.getListeners();
            for (ApplicationListener listener: listeners) {
                if (listener instanceof ServerControl){
                    ((ServerControl) listener).setNextMap(maps.get(map_id));
                    Events.fire(new EventType.GameOverEvent(Team.crux));
                    return;
                }
            }
        }
    }

    public static boolean isBeingVoting(int map_id){
        return votes[map_id] != null;
    }
}
