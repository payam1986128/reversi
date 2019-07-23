package ir.payam1986128.reversi.model;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/24 - 12:01 AM
 */
public enum PieceColor {

    WHITE(1), BLACK(-1);

    PieceColor(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
