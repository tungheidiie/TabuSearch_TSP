package TabuSearch_TSP.data;

import TabuSearch_TSP.objects.City;

import java.io.InputStream;

/**
 * reading the provided data sets and converting it to an array of City objects.
 */
public class ImportData {

    public static City[] getCities() {

        String dataSetName = "a280.tsp";
        int startingLine = 6;

        String[] lines = read(dataSetName).split("\n");
        String[] words = lines[3].split(" ");
        int numOfCities = Integer.parseInt(words[words.length - 1]);
        City[] cities = new City[numOfCities];

        // Read each line and turn it into a City.
        for (int i = startingLine; i < startingLine + numOfCities; i++) {
            String[] line = removeWhiteSpace(lines[i]).trim().split(" ");
            int x = (int) Double.parseDouble(line[1].trim());
            int y = (int) Double.parseDouble(line[2].trim());
            City city = new City(line[0], x, y);
            cities[i - startingLine] = city;
        }
        return cities;
    }


//    Removes duplicate what spaces in a String.
    private static String removeWhiteSpace(String s) {
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ' ' && s.charAt(i - 1) == ' ') {
                s = s.substring(0, i) + s.substring(i + 1);
                i--;
            }
        }
        return s;
    }


//     Read from a file and load it to a String.
    private static String read(String fileName) {
        InputStream stream = ImportData.class.getResourceAsStream(fileName);
        if (stream == null) {
            throw new IllegalArgumentException("Stream is null");
        }
        java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
