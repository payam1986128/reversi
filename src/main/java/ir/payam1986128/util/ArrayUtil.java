package ir.payam1986128.util;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/26 - 05:05 PM
 */
public class ArrayUtil {

    public static int[][] copy(int[][] from) {
        int[][] to = new int[from.length][from[0].length];
        for(int i = 0; i < from.length; i++) {
            System.arraycopy(from[i], 0, to[i], 0, from[i].length);
        }
        return to;
    }

    public static void copy(int[][] from, int[][] to) {
        for(int i = 0; i < from.length; i++) {
            System.arraycopy(from[i], 0, to[i], 0, from[i].length);
        }
    }
}
