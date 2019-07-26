package ir.payam1986128.reversi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Payam Mostafaei
 * Creation Time: 2019/Jul/24 - 12:08 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveDetails {

    private int player;
    private int x;
    private int y;
}
