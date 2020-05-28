import java.util.LinkedList;
import java.util.List;

public class BattleShip {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            // To Do add error message;
        }
        String commandFile = args[0];
        String directoryForResultFile = args[1];

        // Read command file
        List<String> commands = readCommandFile(commandFile);
        // Play game and get result
        List<String> gameResults = Play.playBattleShip(commands);
        // Write result to file
        writeResults(gameResults, directoryForResultFile);
    }

    private static List<String> readCommandFile(String commandFile) {
        return new LinkedList<String>();
    }

    private static void writeResults(List<String> gameResults, String directoryForResultFile) {
    }

}
