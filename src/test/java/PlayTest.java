import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayTest {

    // Test simple example given in project prompt
    @Test
    public void test1() throws Exception {

        List<String> commands = new LinkedList<String>();
        commands.add("PLACE_SHIP Destroyer right A1");
        commands.add("PLACE_SHIP Carrier down B2");
        commands.add("PLACE_SHIP Battleship right J4");
        commands.add("PLACE_SHIP Submarine right E6");
        commands.add("PLACE_SHIP Cruiser right H1");
        commands.add("FIRE A4");
        commands.add("FIRE E6");
        commands.add("FIRE E7");
        commands.add("FIRE E8");
        commands.add("FIRE B1");

        List<String> expectedResults = new LinkedList<String>();
        expectedResults.add("Placed Destroyer");
        expectedResults.add("Placed Carrier");
        expectedResults.add("Placed Battleship");
        expectedResults.add("Placed Submarine");
        expectedResults.add("Placed Cruiser");
        expectedResults.add("Miss");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("You sunk my Submarine!");
        expectedResults.add("Miss");

        List<String> actualResults = Play.playBattleShip(commands);
        assertEquals(expectedResults, actualResults);
    }

    // Test situation where the same ship is placed multiple times. Should get an exception.
    @Test
    public void test2() {

        List<String> commands = new LinkedList<String>();
        commands.add("PLACE_SHIP Destroyer right A1");
        commands.add("PLACE_SHIP Destroyer right A1");
        String expectedMessage = "Error, attempting to add a second ship of type DESTROYER. Only one ship of each type"
            + " can be added to the board";

        try {
            Play.playBattleShip(commands);
            fail("expected exception not thrown.");
        } catch(Exception e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    // Test situation where not all ships have been placed before firing begins.
    @Test
    public void test3() {

        List<String> commands = new LinkedList<String>();
        commands.add("PLACE_SHIP Destroyer right A1");
        commands.add("FIRE A1");
        String expectedMessage = "Error when trying to fire at BoardPosition{row=A, column=1}. Attempting to fire at a "
                + "position without placing all ships first. All ships must be placed before firing begins.";

        try {
            Play.playBattleShip(commands);
            fail("expected exception not thrown.");
        } catch(Exception e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    // Test scenario where game terminates with game over
    @Test
    public void test4() throws Exception {

        List<String> commands = new LinkedList<String>();
        commands.add("PLACE_SHIP Destroyer right A1");
        commands.add("PLACE_SHIP Carrier down B2");
        commands.add("PLACE_SHIP Battleship right J4");
        commands.add("PLACE_SHIP Submarine right E6");
        commands.add("PLACE_SHIP Cruiser right H1");
        // Miss
        commands.add("FIRE A4");
        // Sink submarine
        commands.add("FIRE E6");
        commands.add("FIRE E7");
        commands.add("FIRE E8");
        // Miss
        commands.add("FIRE B1");
        // Sink Carrier
        commands.add("FIRE B2");
        commands.add("FIRE C2");
        commands.add("FIRE D2");
        commands.add("FIRE E2");
        commands.add("FIRE F2");
        // Sink Battleship
        commands.add("FIRE J4");
        commands.add("FIRE J5");
        commands.add("FIRE J6");
        commands.add("FIRE J7");
        // Sink Destroyer
        commands.add("FIRE A1");
        commands.add("FIRE A2");
        // Sink Cruiser
        commands.add("FIRE H1");
        commands.add("FIRE H2");
        commands.add("FIRE H3");


        List<String> expectedResults = new LinkedList<String>();
        expectedResults.add("Placed Destroyer");
        expectedResults.add("Placed Carrier");
        expectedResults.add("Placed Battleship");
        expectedResults.add("Placed Submarine");
        expectedResults.add("Placed Cruiser");
        // Miss
        expectedResults.add("Miss");
        // Sub
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("You sunk my Submarine!");
        // Miss
        expectedResults.add("Miss");

        // Sink Carrier
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("You sunk my Carrier!");

        // Sink Battleship
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("You sunk my Battleship!");

        // Sink Destroyer
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("You sunk my Destroyer!");

        // Sink Cruiser
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("Hit");
        expectedResults.add("You sunk my Cruiser!");
        expectedResults.add("Game Over");

        List<String> actualResults = Play.playBattleShip(commands);
        assertEquals(expectedResults, actualResults);
    }
}
