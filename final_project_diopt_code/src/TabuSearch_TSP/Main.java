package TabuSearch_TSP;

import TabuSearch_TSP.objects.TabuSearch;

public class Main {
    public static void main (String[] args) {
        Preset.printSeed();
        TabuSearch tabuSearch = Preset.getDefaultTabu();
        tabuSearch.run_TabuSearch();
        tabuSearch.printResults();
        tabuSearch.showVisualResults();
    }
}