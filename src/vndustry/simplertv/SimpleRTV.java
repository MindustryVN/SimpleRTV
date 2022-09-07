package vndustry.simplertv;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.mod.Plugin;
import vndustry.simplertv.command.Maps;
import vndustry.simplertv.command.RTV;

import java.util.LinkedList;

public class SimpleRTV extends Plugin {

    @Override
    public void init() {
        Events.on(EventType.PlayerLeave.class, event -> {
            Player player = event.player;
            for (LinkedList<String> vote : VoteHandler.votes){
                vote.remove(player.uuid());
            }
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("rtv", "<map_id>", "Vote to change map (map id in /maps)", RTV::new);
        handler.register("maps", "Display available maps", Maps::new);
    }
}
