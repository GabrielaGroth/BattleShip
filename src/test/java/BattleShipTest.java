import org.junit.Test;

import java.io.File;

public class BattleShipTest {

    // Test simple example
    @Test
    public void test1() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        File inputFile = new File(classLoader.getResource("test_1_input_data.txt").getFile());
        String[] args = {inputFile.getAbsolutePath(), "test_1_expected_result.txt"};
        String expectedOutputFile = "resources/test_1_expected_result.txt";

        //BattleShip.main(args);

    }

}
