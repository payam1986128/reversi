package ir.payam1986128.reversi.service;

import ir.payam1986128.reversi.model.MoveDetails;
import ir.payam1986128.reversi.model.PlayRoom;
import ir.payam1986128.reversi.model.Player;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/24 - 12:47 AM
 */
@Service
public class PlayRoomService {

    private static PlayRoom playRoom = new PlayRoom();

    public PlayRoom registerPlayer(String username) {
        if (playRoom.getPlayerA() == null) {
            playRoom.setPlayerA(new Player(username, 1));
        } else if (playRoom.getPlayerB() == null) {
            playRoom.setPlayerB(new Player(username, -1));
        }
        return playRoom;
    }

    public PlayRoom resetGame() {
        return playRoom = new PlayRoom();
    }

    public PlayRoom move(MoveDetails details) throws Exception {
        if (!isItPlayersTurn(details)) {
            throw new Exception("It's not your turn");
        }
        if (!isMoveValid(details)) {
            throw new Exception("It's not a valid movement");
        }
        doMove(playRoom.getBoard(), details);
        setTurn(details);
        playRoom.setNext(calcNextMoves(playRoom.getTurn()));
        return playRoom;
    }

    private boolean doMove(int[][] board, MoveDetails details) {
        boolean changed = false;
        for (int i = 0; i < details.getX(); i++) {
            if (board[i][details.getY()] == details.getPlayer()) {
                for (int j = i+1; j < details.getX(); j++) {
                    if (board[j][details.getY()] != -details.getPlayer()) {
                        break;
                    }
                }
                for (int j = i+1; j < details.getX(); j++) {
                    board[j][details.getY()] = details.getPlayer();
                    changed = true;
                }
            }
        }
        for (int i = 7; i > details.getX(); i--) {
            if (board[i][details.getY()] == details.getPlayer()) {
                for (int j = i-1; j > details.getX(); j--) {
                    if (board[j][details.getY()] != -details.getPlayer()) {
                        break;
                    }
                }
                for (int j = i-1; j > details.getX(); j--) {
                    board[j][details.getY()] = details.getPlayer();
                    changed = true;
                }
            }
        }
        for (int i = 0; i < details.getY(); i++) {
            if (board[details.getX()][i] == details.getPlayer()) {
                for (int j = i+1; j < details.getY(); j++) {
                    if (board[details.getX()][j] != -details.getPlayer()) {
                        break;
                    }
                }
                for (int j = i+1; j < details.getY(); j++) {
                    board[details.getX()][j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        for (int i = 7; i > details.getY(); i--) {
            if (board[details.getX()][i] == details.getPlayer()) {
                for (int j = i-1; j > details.getY(); j--) {
                    if (board[details.getX()][j] != -details.getPlayer()) {
                        break;
                    }
                }
                for (int j = i-1; j > details.getY(); j--) {
                    board[details.getX()][j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        int startI = details.getX() - Math.min(details.getX(), details.getY()),
                startJ = details.getY() - Math.min(details.getX(), details.getY());
        if (board[startI][startJ] == details.getPlayer()) {
            for (int i = 1; i < Math.min(details.getX(), details.getY()); i++) {
                if (board[startI + i][startJ + i] != -details.getPlayer()) {
                    break;
                }
            }
            for (int i = 1; i < Math.min(details.getX(), details.getY()); i++) {
                board[startI + i][startJ + i] = details.getPlayer();
                changed = true;
            }
        }
        int endI = details.getX() + (7 - Math.max(details.getX(), details.getY())),
                endJ = details.getY() + (7 - Math.max(details.getX(), details.getY()));
        if (board[endI][endJ] == details.getPlayer()) {
            for (int i = 1; i < (7 - Math.max(details.getX(), details.getY())); i++) {
                if (board[endI - i][endJ - i] != -details.getPlayer()) {
                    break;
                }
            }
            for (int i = 1; i < (7 - Math.max(details.getX(), details.getY())); i++) {
                board[endI - i][endJ - i] = details.getPlayer();
                changed = true;
            }
        }
        startI = Math.min(7, details.getX() + details.getY());
        startJ = details.getX() + details.getY() - startI;
        if (board[startI][startJ] == details.getPlayer()) {
            for (int i = 1; i < (startI - details.getX()); i++) {
                if (board[startI - i][startJ + i] != -details.getPlayer()) {
                    break;
                }
            }
            for (int i = 1; i < (startI - details.getX()); i++) {
                board[startI - i][startJ + i] = details.getPlayer();
                changed = true;
            }
        }
        endI = startJ;
        endJ = startI;
        if (board[endI][endJ] == details.getPlayer()) {
            for (int i = 1; i < (endJ - details.getY()); i++) {
                if (board[endI + i][endJ - i] != -details.getPlayer()) {
                    break;
                }
            }
            for (int i = 1; i < (endJ - details.getY()); i++) {
                board[endI + i][endJ - i] = details.getPlayer();
                changed = true;
            }
        }
        return changed;
    }

    private int[][] calcNextMoves(int player) {
        int[][] next = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] board = playRoom.getBoard().clone();
                if (board[i][j] == 0 && doMove(board, new MoveDetails(player, i, j))) {
                    next[i][j] = 2*player;
                }
            }
        }
        return next;
    }

    private boolean isMoveValid(MoveDetails details) {
        int[][] nextMoves = calcNextMoves(details.getPlayer());
        return nextMoves[details.getX()][details.getY()] == 2*details.getPlayer();
    }

    private boolean isItPlayersTurn(MoveDetails details) {
        return playRoom.getTurn() == details.getPlayer();
    }

    private boolean canOtherPlayerMove(int player) {
        int otherPlayer = -player;
        int[][] nextMoves = calcNextMoves(otherPlayer);
        return Arrays.stream(nextMoves).flatMapToInt(Arrays::stream).anyMatch(item -> item == (2*otherPlayer));
    }

    private void setTurn(MoveDetails details) {
        if (canOtherPlayerMove(details.getPlayer())) {
            playRoom.setTurn(-playRoom.getTurn());
        }
    }
}
