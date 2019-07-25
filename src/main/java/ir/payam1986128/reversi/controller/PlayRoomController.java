package ir.payam1986128.reversi.controller;

import ir.payam1986128.reversi.model.MoveDetails;
import ir.payam1986128.reversi.model.PlayRoom;
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

    @MessageMapping("/register")
    @SendTo("/topic/lets-play")
    public PlayRoom register(String username) {
        return playRoomService.registerPlayer(username);
    }

    @MessageMapping("/play-again")
    @SendTo("/topic/lets-play")
    public PlayRoom playAgain() {
        return playRoomService.resetGame();
    }

    @MessageMapping("/move")
    @SendTo("/topic/move")
    public PlayRoom move(MoveDetails details) throws Exception {
        return playRoomService.move(details);
    }
}
