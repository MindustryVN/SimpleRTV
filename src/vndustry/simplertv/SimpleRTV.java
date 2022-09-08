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
        Events.on(EventType.GameOverEvent.class, event -> {
            VoteHandler.reset();
        });
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        handler.register("rtv", "<map_id>", "Bỏ phiếu để thay đổi bản đồ (map id nằm trong lệnh /maps)", RTV::new);
        handler.register("maps", "[trang]", "Hiển thị các bản đồ hiện có", Maps::new);
    }
}
