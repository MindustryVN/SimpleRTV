package vndustry.simplertv;

import arc.Events;
import arc.struct.Seq;
import arc.util.CommandHandler;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import vndustry.simplertv.command.Maps;
import vndustry.simplertv.command.RTV;

public class SimpleRTV extends Plugin {

    @Override
    public void init() {
        Events.on(EventType.PlayerLeave.class, event -> {
            Player player = event.player;
            for (Seq<String> vote : VoteHandler.votes){
                if (vote != null){
                    vote.remove(player.uuid());
                }
            }
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("rtv", "<map_id>", "Vote to change map (map id in /maps)", RTV::new);
        handler.register("maps", "[page]", "Display available maps", Maps::new);
    }
}
