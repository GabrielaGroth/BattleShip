import java.util.HashSet;
import java.util.Set;

public class Ship {

    private ShipType type;
    private ShipDirection direction;
    private BoardPosition shipStartPosition;
    private Set<BoardPosition> unhitPositions;
    private Set<BoardPosition> hitPositions;

    public Ship(ShipType type, ShipDirection direction, BoardPosition shipStartPosition) throws Exception {
        this.type = type;
        this.direction = direction;
        this.shipStartPosition = shipStartPosition;
        this.unhitPositions = getAllPositionsCoveredByBoat(type, direction, shipStartPosition);
        this.hitPositions = new HashSet<>();
    }

    public Set<BoardPosition> getUnhitPositions() {
        return unhitPositions;
    }

    private static Set<BoardPosition> getAllPositionsCoveredByBoat(ShipType type, ShipDirection direction,
                                                                   BoardPosition shipStartPosition) throws Exception {
        HashSet<BoardPosition> allShipPositions = new HashSet<BoardPosition>();
        Integer shipLength = getShipLength(type);
        switch (direction) {
            case DOWN:
                allShipPositions.add(shipStartPosition);
                for (int i=1; i < shipLength; i++) {
                    int newRow = shipStartPosition.getRow() + i;
                    allShipPositions.add(new BoardPosition(newRow, shipStartPosition.getColumn()));
                }
                break;
            case RIGHT:
                allShipPositions.add(shipStartPosition);
                for (int i=1; i < shipLength; i++) {
                    int newColumn = shipStartPosition.getColumn() + i;
                    try {
                        allShipPositions.add(new BoardPosition(shipStartPosition.getRow(), newColumn));
                    } catch (Exception e) {
                        throw new Exception("Error when adding ship type: " + type + ". The start position is" +
                                BoardPosition.getRowLetter(shipStartPosition.getRow()) + shipStartPosition.getColumn() +
                                ". The ship length is " + shipLength + ". The ship direction is " + direction + ".");
                    }
                }
                break;
        }
        return allShipPositions;

    }

    public void addHit(BoardPosition position) {
        hitPositions.add(position);
        unhitPositions.remove(position);
    }

    public ShipType getType() {
        return type;
    }

    public boolean isSunk() {
        return unhitPositions.isEmpty();
    }

    public enum ShipType {
        CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER
    }

    private static Integer getShipLength(ShipType type) {
        switch (type)
        {
            case CARRIER: return 5;
            case BATTLESHIP: return 4;
            case CRUISER: return 3;
            case SUBMARINE: return 3;
            case DESTROYER:  return 2;
            default: return null;
        }
    }

    public enum ShipDirection {
        DOWN, RIGHT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (type != ship.type) return false;
        if (direction != ship.direction) return false;
        return shipStartPosition.equals(ship.shipStartPosition);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + direction.hashCode();
        result = 31 * result + shipStartPosition.hashCode();
        return result;
    }
}
