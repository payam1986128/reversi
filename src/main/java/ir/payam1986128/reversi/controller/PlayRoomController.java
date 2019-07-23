package ir.payam1986128.reversi.controller;

import ir.payam1986128.reversi.model.MoveDetails;
import ir.payam1986128.reversi.model.PlayRoom;
import ir.payam1986128.reversi.model.Player;
import ir.payam1986128.reversi.service.PlayRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/23 - 08:33 PM
 */
@Controller
public class PlayRoomController {

    @Autowired
    private PlayRoomService playRoomService;

    private static PlayRoom playRoom = new PlayRoom();

    @MessageMapping("/register")
    @SendTo("/topic/lets-play")
    public PlayRoom register(String username) {
        if (playRoom.getPlayerA() == null) {
            playRoom.setPlayerA(new Player(username, 1));
        } else if (playRoom.getPlayerB() == null) {
            playRoom.setPlayerB(new Player(username, -1));
        }
        return playRoom;
    }

    @MessageMapping("/move")
    @SendTo("/topic/move")
    public PlayRoom move(MoveDetails details) throws Exception {
        if (!playRoom.isItYourTurn(details.getPlayer())) {
            throw new Exception("It's not your turn");
        }
        playRoom.setTurn(-playRoom.getTurn());
        return playRoom;
    }
}
