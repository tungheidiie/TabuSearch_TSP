package TabuSearch_TSP;

import TabuSearch_TSP.data.ImportData;

import TabuSearch_TSP.objects.TabuSearch;

import java.util.Random;

public class Preset {
    static Random random = new Random();
    public static long seed = random.nextLong();

    public static void setSeed(long number) {
        seed = number;
    }
    public static void printSeed() {
        System.out.println("Seed: " + seed);
    }
    /**
     * Gets a default TabuSearch instance with preset configurations.
     *
     * @return A TabuSearch instance with default configurations.
     */
    public static TabuSearch getDefaultTabu() {

        TabuSearch tabuSearch = new TabuSearch();
        tabuSearch.setCities(ImportData.getCities());
        tabuSearch.setMaxTabuSize(ImportData.getCities().length);
        tabuSearch.setInitialTabuSize(ImportData.getCities().length / 5);
        tabuSearch.setIncreaseTabuSizeInterval(10);
        tabuSearch.setMaxIterations(800);
        return tabuSearch;
    }
}
