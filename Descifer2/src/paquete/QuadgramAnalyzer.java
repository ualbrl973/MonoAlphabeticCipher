package paquete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class QuadgramAnalyzer {
    private final Map<String, Integer> quadgramFrequencies;
    private final long totalQuadgrams;

    public QuadgramAnalyzer(String filePath) throws IOException {
        this.quadgramFrequencies = new HashMap<>();
        
        
        InputStream is = getClass().getResourceAsStream("/english_quadgrams.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
     
        String line;

        // Read the quadgram file
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            quadgramFrequencies.put(parts[0], Integer.parseInt(parts[1]));
        }
        reader.close();
        this.totalQuadgrams = quadgramFrequencies.values().stream().mapToLong(Integer::longValue).sum();
    }

    // Calculate the fitness of a text based on quadgrams
    public double calculateFitness(String text) {
        double fitness = 0.0;
        for (int i = 0; i < text.length() - 3; i++) {
            String quadgram = text.substring(i, i + 4).toUpperCase();
            int frequency = quadgramFrequencies.getOrDefault(quadgram, 0);
            double probability = (frequency > 0) ? (double) frequency / totalQuadgrams : 0.1 / totalQuadgrams;
            fitness += Math.log(probability);
        }
        return fitness;
    }
}
