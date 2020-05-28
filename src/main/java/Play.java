import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Play {

    public static List<String> playBattleShip(List<String> commands) throws Exception {
        Board board = new Board();
        List<String> commandResults = new LinkedList<String>();
        for (String rawCommand : commands) {
            Command command = Command.buildCommand(rawCommand);
            switch (command.getCommandType()) {
                case PLACE_SHIP:
                    String result = placeShip(board, command.getShipType(), command.getShipDirection(), command.getPosition());
                    commandResults.add(result);
                    break;
                case FIRE:
                    List<String> results = fire(board, command.getPosition());
                    commandResults.addAll(results);
                    if (board.isGameOver()) return commandResults;
                    break;
            }
        }
        return commandResults;
    }

    public static String placeShip(Board board, Ship.ShipType type, Ship.ShipDirection direction, BoardPosition startPosition)
            throws Exception {

        // Try to create a ship at the specified location and direction
        Ship ship = null;
        try {
            ship = new Ship(type, direction, startPosition);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Could not place a " + type + " ship at position " +
                    BoardPosition.getRowLetter(startPosition.getRow()) + startPosition.getColumn() + " in direction " +
                    direction;
            throw new Exception(errorMessage);
        }

        // Make sure a ship of this type has not been created added
        if (board.containsShipOfType(ship.getType())) {
            throw new Exception("Error, attempting to add a second ship of type " + ship.getType() +
                    ". Only one ship of each type can be added to the board");
        }

        board.addShip(ship);
        Set<BoardPosition> cellsOccupiedByShip = ship.getUnhitPositions();
        for (BoardPosition p : cellsOccupiedByShip) {
            board.addOccupiedPosition(p, ship);
        }
        return "Placed " + StringUtils.capitalize(type.toString().toLowerCase());
    }

    public static List<String> fire(Board board, BoardPosition position) throws Exception {
        // Store message results in a list
        List<String> results = new LinkedList<String>();

        // If not all ships have been placed on the board, throw an exception
        if (!board.areAllShipsAddedToBoard()) {
            throw new Exception("Error when trying to fire at " + position +
                    ". Attempting to fire at a position without placing all ships first. All ships must be placed before firing begins.");
        }

        // If player fires on an occupied cell
        if (board.isOccupied(position)) {
            // Add hit to the ship, and add a "Hit" output to result
            Ship ship = board.getShipAtPosition(position);
            ship.addHit(position);
            results.add("Hit");

            // If the hit ALSO sinks the ship, update sunk ships list and add a "You sunk my ship" result
            if (ship.isSunk()) {
                board.sinkShip(ship);

                results.add("You sunk my " + StringUtils.capitalize(ship.getType().toString().toLowerCase()) + "!");

                // If there are no remaining unsunk ships, then the game is over. Add "Game Over" to result;
                if (board.isGameOver()) {
                    results.add("Game Over");
                }
            }

            // If player fires on an unoccupied cell, return Miss
        } else {
            results.add("Miss");
        }
        return results;
    }

}
