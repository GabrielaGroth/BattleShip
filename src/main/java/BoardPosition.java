import java.util.Map;

public class BoardPosition {
    private static final int ROW_MIN = 1;
    private static final int ROW_MAX = 10;
    private static final int COLUMN_MIN = 1;
    private static final int COLUMN_MAX = 10;
    private int row;
    private int column;

    public BoardPosition(int row, int column) throws Exception {
        if (isValidColumn(column) && isValidRow(row)) {
            this.row = row;
            this.column = column;
        } else {
            throw new Exception();
        }
    }

    public BoardPosition(String s) throws Exception {
        char[] rawBoardPositionArray = s.toCharArray();

        if (rawBoardPositionArray.length != 2) {
            throw new Exception("Invalid board position " + s);
        }

        int rowNum = getRowNumber(rawBoardPositionArray[0]);
        int colNum =  Character.getNumericValue(rawBoardPositionArray[1]);

        if (isValidColumn(colNum) && isValidRow(rowNum)) {
            this.row = rowNum;
            this.column = colNum;
        } else {
            throw new Exception();
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    private static boolean isValidColumn(int column) {
        if (column >= COLUMN_MIN && column <= COLUMN_MAX) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidRow(int row) {
        if (row >= ROW_MIN && row <= ROW_MAX) {
            return true;
        } else {
            return false;
        }
    }

    public static int getRowNumber(char c) {
        return c - 64;
    }

    public static char getRowLetter(int i) {
        return (char) ((char) i + 64);
    }

    @Override
    public String toString() {
        return "BoardPosition{" +
                "row=" + getRowLetter(row) +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardPosition position = (BoardPosition) o;

        if (row != position.row) return false;
        return column == position.column;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }
}
