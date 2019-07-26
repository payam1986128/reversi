package ir.payam1986128.reversi.model;

import lombok.Data;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/23 - 11:34 PM
 */
@Data
public class PlayRoom {

    private Player playerA;
    private Player playerB;
    private int[][] board = new int[8][8];
    private int[][] next = new int[8][8];
    private int turn = 1;
    private boolean finished = false;

    private PlayRoom() {
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
    }

    public void reset() {
        playerA = null;
        playerB = null;
        board = new int[8][8];
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
        next = new int[8][8];
        turn = 1;
        finished = false;
    }

    public static PlayRoom getInstance() {
        return PlayRoomHolder.instance;
    }

    private static class PlayRoomHolder {
        public static final PlayRoom instance = new PlayRoom();
    }

}