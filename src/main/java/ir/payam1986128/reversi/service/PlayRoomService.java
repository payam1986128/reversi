package ir.payam1986128.reversi.service;

import ir.payam1986128.reversi.model.MoveDetails;
import ir.payam1986128.reversi.model.PlayRoom;
import org.springframework.stereotype.Service;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/24 - 12:47 AM
 */
@Service
public class PlayRoomService {

    public PlayRoom calcNextMove(PlayRoom current, MoveDetails moveDetails) {
        current.calcOptions();
        return current;
    }
}
