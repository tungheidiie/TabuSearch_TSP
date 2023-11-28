package TabuSearch_TSP;

import TabuSearch_TSP.objects.ConcentricTabuSearch;
import TabuSearch_TSP.objects.TabuSearch;

public class Main {
    public static void main (String[] args) {
//        for (int i = 0; i < 20; i++) {
//            Preset.setSeed(new Random().nextLong());
//            TabuSearch tabuSearch = Preset.getDefaultTabu();
//            tabuSearch.run_TabuSearch();
//            tabuSearch.printResults();
//
//            ConcentricTabuSearch concentricTS = Preset.getDefault_ConcentricTS();
//            concentricTS.run_ConcentricTabuSearch();
//            concentricTS.printResults();
//            System.out.println("------------------------------------------------");
//        }
        Preset.printSeed();
        TabuSearch tabuSearch = Preset.getDefaultTabu();
        tabuSearch.run_TabuSearch();
        tabuSearch.printResults();
        tabuSearch.showVisualResults();

        ConcentricTabuSearch concentricTS = Preset.getDefault_ConcentricTS();
        concentricTS.run_ConcentricTabuSearch();
        concentricTS.printResults();
        concentricTS.showVisualResults();
    }
}