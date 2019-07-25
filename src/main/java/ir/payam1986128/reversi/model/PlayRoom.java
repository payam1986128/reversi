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

    public PlayRoom() {
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
    }

}