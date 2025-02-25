package src.Position;

public record Position(int row, int col) {
    private static int size = 8;

    public Position move(Direction direction) {
        int newRow = row , newCol = col;

        boolean isEvenCol = (col % 2 == 0); // เช็กว่า column เป็นเลขคู่หรือไม่

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case UPLEFT -> {
                newCol--;
                if (!isEvenCol) newRow--; // เลขคี่ต้องขยับแถวขึ้น
            }
            case UPRIGHT -> {
                newCol++;
                if (!isEvenCol) newRow--; // เลขคี่ต้องขยับแถวขึ้น
            }
            case DOWNLEFT -> {
                newCol--;
                if (isEvenCol) newRow++; // เลขคู่ต้องขยับแถวลง
            }
            case DOWNRIGHT -> {
                newCol++;
                if (isEvenCol) newRow++; // เลขคู่ต้องขยับแถวลง
            }
        }
        return new Position(newRow, newCol);
    }

    public boolean isAdjacent(Position other) {
        boolean thisIsEvenCol = (this.col % 2 == 0);

        int dr = this.row - other.row;
        int dc = this.col - other.col;

        return (dr == -1 && dc == 0) || // UP
                (dr == 1 && dc == 0)  || // DOWN
                (dr == (thisIsEvenCol ? -1 : 0) && dc == -1) || // UPLEFT (เลขคู่ขยับขึ้น)
                (dr == (thisIsEvenCol ? -1 : 0) && dc == 1)  || // UPRIGHT (เลขคู่ขยับขึ้น)
                (dr == (thisIsEvenCol ? 0 : 1)  && dc == -1) || // DOWNLEFT (เลขคี่ขยับลง)
                (dr == (thisIsEvenCol ? 0 : 1)  && dc == 1);       // DOWNRIGHT (เฉพาะเลขคู่)
    }

    public static boolean isValidPosition(Position position) {
        return position.row() >= 0 && position.row() < size &&
                position.col() >= 0 && position.col() < size;
    }

    @Override
    public String toString() {
        int r = this.row+1, c = this.col+1;
        return "(" + r + ", " + c + ")";
    }
}
