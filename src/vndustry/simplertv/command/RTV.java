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
            player.sendMessage("[red]ID của bản đồ phải là một số");
            return;
        }
        Seq<Map> maps = new Seq<>();
        maps.addAll(Vars.maps.customMaps());
        if (map_id < 0 || map_id > (maps.size - 1)) {
            player.sendMessage("[red]ID của bản đồ không hợp lệ");
            return;
        }
        if (VoteHandler.isVoted(player, map_id)){
            Call.sendMessage("[red]RTV: " + player.name + " [accent]đã hủy bỏ phiếu cho bản đồ [yellow]" + maps.get(map_id).name() + "[green](" + VoteHandler.getVoteCount(map_id) + "/" + VoteHandler.getRequire() + ")");
            VoteHandler.removeVote(player, map_id);
            return;
        }
        if (VoteHandler.isBeingVoting(map_id)){
            Call.sendMessage("[red]RTV: " + player.name + " [accent]đã bỏ phiếu cho bản đồ [yellow]" + maps.get(map_id).name() + "[green](" + VoteHandler.getVoteCount(map_id) + "/" + VoteHandler.getRequire() + ")");
            player.sendMessage("[red]RTV: [white]Sử dụng /rtv " + map_id + " thêm một lần nữa để hủy bỏ phiếu cho bản đồ này !");
            VoteHandler.vote(player, map_id);
            return;
        }
        VoteHandler.vote(player, map_id);
        Call.sendMessage("[red]RTV: [accent]" + player.name() + " [white]Muốn chuyển bản đồ sang [yellow]" + maps.get(map_id).name());
        Call.sendMessage("[red]RTV: [white]Số lượng phiếu [yellow]" + maps.get(map_id).name() + "[white]: [green]" + VoteHandler.getVoteCount(map_id) + "/" + VoteHandler.getRequire());
        Call.sendMessage("[red]RTV: [white]Sử dụng [yellow]/rtv " + map_id + " [white]để bỏ phiếu cho bản đồ này !");
        player.sendMessage("[red]RTV: [white]Sử dụng /rtv " + map_id + " thêm một lần nữa để hủy bỏ phiếu cho bản đồ này !");
        VoteHandler.check(map_id);
    }
}
