package TabuSearch_TSP.objects;

import TabuSearch_TSP.Preset;
import TabuSearch_TSP.display.WindowResults;
import TabuSearch_TSP.display.WindowTabu;

import java.util.*;

/**
 * The Concentric TabuSearch system. This class brings together the entire process of the Concentric Tabu Search.
 */
public class ConcentricTabuSearch {
    private final List<City[]> tabuList = new ArrayList<>();
    private int maxIterations;
    private City[] cities;
    private City[] bestRoute;
    private long executeTime;
    private int initialTabuSize;
    private int increaseTabuSizeInterval;  // The interval for increasing the size of the Tabu list
    private int maxTabuTenure;
    private boolean finished;
    // Parameters specific to Concentric Tabu Search
    int concentricRadius = 10;       // Radius for the concentric search

    // Construct the Concentric TabuSearch object with default values.
    public ConcentricTabuSearch() {
        finished = false;
        executeTime = 0;
        initialTabuSize = 10;
        increaseTabuSizeInterval = 15;
        maxTabuTenure = 30;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }


    public void setCities(City[] cities) {
        if (cities == null) {
            throw new IllegalArgumentException("cities cannot be null.");
        }
        this.cities = cities;
    }

    public void setInitialTabuSize(int initialTabuSize) {
        this.initialTabuSize = initialTabuSize;
    }

    public void setIncreaseTabuSizeInterval(int interval) {
        this.increaseTabuSizeInterval = interval;
    }

    public void setMaxTabuTenure(int maxTabuTenure) {
        this.maxTabuTenure = maxTabuTenure;
    }

    /**
     * Run the Concentric Tabu Search algorithm.
     * Displays the fittest of each iteration to the screen.
     * and get execute time of algorithm
     */
    public void run_ConcentricTabuSearch() {
        long startTime = System.currentTimeMillis();
        City[] currentRoute = generateInitialSolution(cities);
        WindowTabu win = new WindowTabu(currentRoute, "Concentric Tabu Search");
        win.draw(currentRoute);

        int currentTabuTenure = initialTabuSize;
        bestRoute = currentRoute.clone();

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            List<City[]> neighbors = generateNeighbors(currentRoute);

            for (City[] neighbor : neighbors) {
                double neighborDistance = getDistanceRoute(neighbor);
                if (neighborDistance < getDistanceRoute(bestRoute) && isMoveAllowed(neighbor)) {
                    bestRoute = neighbor;
                    win.draw(bestRoute);
                }
            }

            if (getDistanceRoute(currentRoute) < getDistanceRoute(bestRoute)) {
                bestRoute = currentRoute.clone();
                win.draw(bestRoute);
            }

            updateTabuList(currentRoute, currentTabuTenure);
            currentRoute = bestRoute;

            if (iteration % increaseTabuSizeInterval == 0 && currentTabuTenure < maxTabuTenure) {
                currentTabuTenure++;
            }

//             Concentric Search (ring moves)
            currentRoute = bestRingMove(currentRoute, concentricRadius, win);
            if (getDistanceRoute(currentRoute) < getDistanceRoute(bestRoute)) {
                bestRoute = currentRoute.clone();
                win.draw(bestRoute);
            }
        }

        long endTime = System.currentTimeMillis();
        executeTime = endTime - startTime;
        finished = true;
    }

    private boolean isMoveAllowed(City[] route) {
        return !isTabu(route) || aspirationCriterion(route);
    }

    /**
     * Run the concentric search around the current route.
     *
     * @param route  The current route of TSP.
     * @param radius The radius of the concentric search.
     * @return The best route found in the concentric search.
     */
    private City[] bestRingMove(City[] route, int radius, WindowTabu win) {
        City[] centerSolution = route.clone();
        City[] bestSolutionInRing = route.clone();
        double minDistInRing = getDistanceRoute(bestSolutionInRing);

        for (int i = 0; i < route.length; i++) {
            for (int j = i + 1; j < route.length && j - i <= radius; j++) {
                City[] newSolution = ringMove(route, i, j);
                double newDist = getDistanceRoute(newSolution);

                if (newDist < minDistInRing && isMoveAllowed(newSolution)) {
                    minDistInRing = newDist;
                    bestSolutionInRing = newSolution;
                    win.draw(bestRoute);
                }
            }
        }

        return compareSolutions(centerSolution, bestSolutionInRing, radius);
    }

    private City[] compareSolutions(City[] center, City[] ring, int radius) {
        double centerDistance = getDistanceRoute(center);
        double ringDistance = getDistanceRoute(ring);

        // If the ring solution is better
        if (ringDistance < centerDistance) {
            return ring;
        } else {
            return center;
        }
    }

    private City[] ringMove(City[] route, int i, int j) {
        City[] newRoute = route.clone();
        while (i < j) {
            City temp = newRoute[i];
            newRoute[i] = newRoute[j];
            newRoute[j] = temp;
            i++;
            j--;
        }
        return newRoute;
    }


    private void reverseCitiesBetween(City[] route, int i, int j) {
        // Swap cities at positions i and j
        while (i < j) {
            City temp = route[i];
            route[i] = route[j];
            route[j] = temp;
            i++;
            j--;
        }
    }

    /**
     * Checks the aspiration criterion to determine if a solution should be accepted.
     *
     * @param route current route of TSP
     */
    private boolean aspirationCriterion(City[] route) {
        return getDistanceRoute(route) < getDistanceRoute(bestRoute);
    }

    private City[] generateInitialSolution(City[] cities) {
        City[] newCities = cities.clone();
        Random rand = new Random(Preset.seed);

        for (int i = 0; i < newCities.length; i++) {
            int randomIndexToSwap = rand.nextInt(newCities.length);
            City temp = newCities[randomIndexToSwap];
            newCities[randomIndexToSwap] = newCities[i];
            newCities[i] = temp;
        }

        return newCities;
    }


    /**
     * Using 2-opt generate neighbors of currentRoute
     *
     * @param currentRoute current route of TSP
     * @return neighbors of currentRoute
     */
    public List<City[]> generateNeighbors(City[] currentRoute) {
        List<City[]> neighbors = new ArrayList<>();

        for (int i = 0; i < currentRoute.length; i++) {
            for (int j = i + 1; j < currentRoute.length; j++) {
                City[] newRoute = currentRoute.clone();
                reverseCitiesBetween(newRoute, i, j);
                neighbors.add(newRoute);
            }
        }

        return neighbors;
    }

    private boolean isTabu(City[] route) {
        return tabuList.contains(route);
    }

    /**
     * Add new route to Tabu list
     * Remove the oldest solution in  the size of the tabu list exceeds the specified limit.
     *
     * @param tabuSize size of tabu list
     */
    private void updateTabuList(City[] route, int tabuSize) {
        tabuList.add(route.clone());
        if (tabuList.size() > tabuSize) {
            tabuList.remove(0);
        }
    }

    private int getDistanceRoute(City[] route) {
        double distanceTravelled = 0;

        for (int i = 1; i < route.length; i++) {
            distanceTravelled += City.distance(route[i - 1], route[i]);
        }

        distanceTravelled += City.distance(route[route.length - 1], route[0]);
        return (int) distanceTravelled;
    }

    public City[] getBestRoute() {
        if (!finished) {
            throw new IllegalStateException("Concentric Tabu search was not initiated.");
        }
        return bestRoute;
    }

    public long getExecuteTime() {
        if (!finished) {
            throw new IllegalStateException("Concentric Tabu search was not initiated.");
        }
        return executeTime;
    }

    public int getBestDistanceRoute() {
        if (!finished) {
            throw new IllegalStateException("Concentric Tabu search was not initiated.");
        }
        return getDistanceRoute(bestRoute);
    }

    public void printResults() {
        if (!finished) {
            throw new IllegalStateException("Concentric Tabu search is not initiated.");
        }
        System.out.println("Concentric Tabu search");
        System.out.println("Best route: " + Arrays.toString(getBestRoute()));
        System.out.println("Best distance route: " + getBestDistanceRoute());
        System.out.println("Execute time: " + getExecuteTime() + " ms");
    }

    /**
     * Display results of TSP
     */
    public void showVisualResults() {
        if (!finished) {
            throw new IllegalStateException("Concentric Tabu search is not initiated.");
        }
        StringBuilder results = new StringBuilder();
        results.append("Concentric Tabu search\n");
        results.append("Number of cities: ").append(getBestRoute().length).append("\n");
        results.append("\nBest distance route: ").append(getBestDistanceRoute()).append("\n");
        results.append("\nBest route:\n ");
        for (City city : getBestRoute()) {
            results.append(city.getName()).append("  ");
        }

        new WindowResults(results);
    }
}
