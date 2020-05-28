import java.util.*;

public class Board {

    private Map<BoardPosition, Ship> occupiedPositions;
    private Set<Ship> unsunkShips;
    private Set<Ship> sunkShips;
    private int numShipsAdded;
    private static final int EXPECTED_TOTAL_NUMBER_OF_SHIPS = 5;

    public Board() {
        this.occupiedPositions = new HashMap<BoardPosition, Ship>();
        this.unsunkShips = new HashSet<Ship>();
        this.sunkShips = new HashSet<Ship>();
        this.numShipsAdded = 0;
    }

    public void addShip(Ship ship) {
        numShipsAdded++;
        unsunkShips.add(ship);
    }

    public void addOccupiedPosition(BoardPosition p, Ship ship) {
        occupiedPositions.put(p, ship);
    }

    public boolean isGameOver() {
        return unsunkShips.isEmpty() && !sunkShips.isEmpty();
    }

    public void sinkShip(Ship ship) {
        unsunkShips.remove(ship);
        sunkShips.add(ship);
    }

    public boolean isOccupied(BoardPosition position) {
        return occupiedPositions.containsKey(position);
    }

    public Ship getShipAtPosition(BoardPosition position) {
        return occupiedPositions.get(position);
    }

    public boolean containsShipOfType(Ship.ShipType type) {
        for (Ship ship : unsunkShips) {
            if (ship.getType().equals(type)) return true;
        }
        for (Ship ship : sunkShips) {
            if (ship.getType().equals(type)) return true;
        }
        return false;
    }

    public boolean areAllShipsAddedToBoard() {
        return numShipsAdded == EXPECTED_TOTAL_NUMBER_OF_SHIPS;
    }
}
