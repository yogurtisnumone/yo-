package src.Board;

import src.Position.Position;
import java.util.HashMap;
import java.util.Map;

public class HexBoard {
    private final Map<Position, String> board;

    public HexBoard() {
        this.board = new HashMap<>();
    }

    public void setHex(Position position, String value) {
        board.put(position, value);
    }

    public String getHex(Position position) {
        return board.getOrDefault(position, ".");
    }

    public void printBoard(int rows, int cols) {
        for (int c = 0; c < cols; c++) {
            if (c % 2 == 0) System.out.print("    ");

            for (int r = rows - 1; r >= 0; r--) {
                Position pos = new Position(r, c);
                System.out.print(getHex(pos) + "        ");
            }
            System.out.println();
        }
    }
}