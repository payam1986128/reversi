package ir.payam1986128.reversi.service;

import ir.payam1986128.reversi.model.MoveDetails;
import ir.payam1986128.reversi.model.PlayRoom;
import ir.payam1986128.reversi.model.Player;
import ir.payam1986128.reversi.util.ArrayUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/24 - 12:47 AM
 */
@Service
public class PlayRoomService {

    private static PlayRoom playRoom = PlayRoom.getInstance();

    public PlayRoom registerPlayer(String username) {
        if (playRoom.getPlayerA() == null) {
            playRoom.setPlayerA(new Player(username, 1));
        } else if (playRoom.getPlayerB() == null) {
            playRoom.setPlayerB(new Player(username, -1));
        }
        return playRoom;
    }

    public void startGame() {
        if (playRoom.isFinished()) {
            playRoom.reset();
        }
        playRoom.setNext(calcNextMoves(playRoom.getTurn()));
    }

    public PlayRoom move(MoveDetails details) throws Exception {
        if (!isItPlayersTurn(details)) {
            throw new Exception("It's not your turn");
        }
        if (!isMoveValid(details)) {
            throw new Exception("It's not a valid movement");
        }
        doMove(playRoom.getBoard(), details);
        if (!isGameFinished()) {
            setTurn(details);
            playRoom.setNext(calcNextMoves(playRoom.getTurn()));
        } else {
            playRoom.setFinished(true);
        }
        return playRoom;
    }

    private boolean doMove(int[][] board, MoveDetails details) {
        int[][] target = ArrayUtil.copy(board);
        boolean changed = false;
        outer: for (int i = 0; i < details.getX(); i++) {
            if (board[i][details.getY()] == details.getPlayer()) {
                for (int j = i+1; j < details.getX(); j++) {
                    if (board[j][details.getY()] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < details.getX(); j++) {
                    target[j][details.getY()] = details.getPlayer();
                    changed = true;
                }
            }
        }
        outer: for (int i = 7; i > details.getX(); i--) {
            if (board[i][details.getY()] == details.getPlayer()) {
                for (int j = i-1; j > details.getX(); j--) {
                    if (board[j][details.getY()] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i-1; j > details.getX(); j--) {
                    target[j][details.getY()] = details.getPlayer();
                    changed = true;
                }
            }
        }
        outer: for (int i = 0; i < details.getY(); i++) {
            if (board[details.getX()][i] == details.getPlayer()) {
                for (int j = i+1; j < details.getY(); j++) {
                    if (board[details.getX()][j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < details.getY(); j++) {
                    target[details.getX()][j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        outer: for (int i = 7; i > details.getY(); i--) {
            if (board[details.getX()][i] == details.getPlayer()) {
                for (int j = i-1; j > details.getY(); j--) {
                    if (board[details.getX()][j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i-1; j > details.getY(); j--) {
                    target[details.getX()][j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        int startI = details.getX() - Math.min(details.getX(), details.getY()),
                startJ = details.getY() - Math.min(details.getX(), details.getY());
        outer: for (int i = 0; i < Math.min(details.getX(), details.getY()); i++) {
            if (board[startI + i][startJ + i] == details.getPlayer()) {
                for (int j = i+1; j < Math.min(details.getX(), details.getY()); j++) {
                    if (board[startI + j][startJ + j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < Math.min(details.getX(), details.getY()); j++) {
                    target[startI + j][startJ + j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        int endI = details.getX() + (7 - Math.max(details.getX(), details.getY())),
                endJ = details.getY() + (7 - Math.max(details.getX(), details.getY()));
        outer: for (int i = 0; i < (7 - Math.max(details.getX(), details.getY())); i++) {
            if (board[endI - i][endJ - i] == details.getPlayer()) {
                for (int j = i+1; j < (7 - Math.max(details.getX(), details.getY())); j++) {
                    if (board[endI - j][endJ - j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < (7 - Math.max(details.getX(), details.getY())); j++) {
                    target[endI - j][endJ - j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        startI = Math.min(7, details.getX() + details.getY());
        startJ = details.getX() + details.getY() - startI;
        outer: for (int i = 0; i < (startI - details.getX()); i++) {
            if (board[startI - i][startJ + i] == details.getPlayer()) {
                for (int j = i+1; j < (startI - details.getX()); j++) {
                    if (board[startI - j][startJ + j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < (startI - details.getX()); j++) {
                    target[startI - j][startJ + j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        endI = startJ;
        endJ = startI;
        outer: for (int i = 0; i < (endJ - details.getY()); i++) {
            if (board[endI + i][endJ - i] == details.getPlayer()) {
                for (int j = i+1; j < (endJ - details.getY()); j++) {
                    if (board[endI + j][endJ - j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < (endJ - details.getY()); j++) {
                    target[endI + j][endJ - j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        if (changed) {
            target[details.getX()][details.getY()] = details.getPlayer();
        }
        ArrayUtil.copy(target, board);
        return changed;
    }

    private int[][] calcNextMoves(int player) {
        int[][] next = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] board = ArrayUtil.copy(playRoom.getBoard());
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

    private boolean isGameFinished() {
        return Arrays.stream(playRoom.getBoard())
                .flatMapToInt(Arrays::stream)
                .noneMatch(item -> item == 0);
    }
}
