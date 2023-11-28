package TabuSearch_GA;

import TabuSearch_GA.objects.ConcentricTabuSearch;
import TabuSearch_GA.objects.TabuSearch;

import java.util.Random;

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