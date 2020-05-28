import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BattleShip {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            throw new Exception("Battleship requires two arguments. The first argument should be the path to a file " +
                    " containing a sequence of battleship commands. The second argument should a path to where the " +
                    "output should be written." );
        }
        String commandFile = args[0];
        String resultFile = args[1];

        // Read command file
        List<String> commands = readCommandFile(commandFile);
        // Play game and get result
        List<String> gameResults = Play.playBattleShip(commands);
        // Write result to file
        writeResults(gameResults, resultFile);
    }

    private static List<String> readCommandFile(String commandFile) throws IOException {
        return Files.readAllLines(Paths.get(commandFile));
    }

    private static void writeResults(List<String> gameResults, String resultFile) throws IOException {
        FileWriter fw = new FileWriter(resultFile);
        for (String result : gameResults) {
            fw.write(result);
        }
        fw.close();
    }

}
