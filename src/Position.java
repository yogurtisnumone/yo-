public record Position(int row, int col) {//เดินแบบตารางสี่เหลี่ยมปกติ ยังไม่ใช่หกเหลี่ยม

    public Position move(Direction direction) {
        int newRow = row, newCol = col;

        switch (direction) {
            case UP -> newRow--;
            case DOWN -> newRow++;
            case UPLEFT -> { newRow--; newCol--; }
            case UPRIGHT -> { newRow--; newCol++; }
            case DOWNLEFT -> { newRow++; newCol--; }
            case DOWNRIGHT -> { newRow++; newCol++; }
            default -> { return this; } // อยู่ที่เดิมถ้า direction ผิด
        }
        return new Position(newRow, newCol);
    }
}
