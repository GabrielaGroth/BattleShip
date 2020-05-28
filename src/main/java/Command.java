public class Command {

    public enum CommandType {
        PLACE_SHIP, FIRE
    }

    private CommandType commandType;
    private BoardPosition position;
    private Ship.ShipType shipType;
    private Ship.ShipDirection shipDirection;
    private static final String PLACE_SHIP_COMMAND = "PLACE_SHIP";
    private static final String FIRE_COMMAND = "FIRE";

    private Command() {}

    public static Command buildCommand(String rawCommand) throws Exception {

        String[] commandTokens = rawCommand.split(" ");
        if (commandTokens.length == 0) {
            throw new Exception(getGenericInvalidCommandError(rawCommand));
        } else if (commandTokens[0].equals(PLACE_SHIP_COMMAND)) {
           return buildPlaceShipCommand(rawCommand, commandTokens);
        } else if (commandTokens[0].equals(FIRE_COMMAND)) {
            return buildFireCommand(rawCommand, commandTokens);
        } else {
            throw new Exception(getGenericInvalidCommandError(rawCommand));
        }

    }

    private static String getGenericInvalidCommandError(String rawCommand) {
        return "Error, this is not a invalid command: " + rawCommand;
    }

    private static Command buildPlaceShipCommand(String rawCommand, String[] commandTokens) throws Exception {
        if (commandTokens.length != 4) {
            throw new Exception(getGenericInvalidCommandError(rawCommand));
        }
        Command c = new Command();
        c.commandType = CommandType.PLACE_SHIP;
        try {
            c.shipType = Ship.ShipType.valueOf(commandTokens[1].toUpperCase());
        } catch (Exception e) {
            throw new Exception("Error in command " + rawCommand + ". " + commandTokens[1] +
                    " is not a valid ship type.");
        }
        try {
            c.shipDirection = Ship.ShipDirection.valueOf(commandTokens[2].toUpperCase());
        } catch (Exception e) {
            throw new Exception("Error in command " + rawCommand + ". " + commandTokens[2] +
                    " is not a valid direction type.");
        }
        try {
            c.position = new BoardPosition(commandTokens[3]);
        } catch (Exception e) {
            throw new Exception("Error in command " + rawCommand + ". Cannot place ship at position " + commandTokens[3]
                    + " because it is not a valid position.");
        }
        return c;
    }

    private static Command buildFireCommand(String rawCommand, String[] commandTokens) throws Exception {
        if (commandTokens.length != 2) {
            throw new Exception(getGenericInvalidCommandError(rawCommand));
        }
        Command c = new Command();
        c.commandType = CommandType.FIRE;
        try {
            c.position = new BoardPosition(commandTokens[1]);
        } catch (Exception e) {
            throw new Exception("Error in command " + rawCommand + ". Cannot fire at position " + commandTokens[1]
                    + " because it is not a valid position");
        }
        return c;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public BoardPosition getPosition() {
        return position;
    }

    public Ship.ShipType getShipType() {
        return shipType;
    }

    public Ship.ShipDirection getShipDirection() {
        return shipDirection;
    }
}
