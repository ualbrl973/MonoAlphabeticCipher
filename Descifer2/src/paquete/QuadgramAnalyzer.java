package paquete;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuadgramAnalyzer {
    private final Map<String, Integer> quadgramFrequencies;
    private final long totalQuadgrams;

    public QuadgramAnalyzer(String filePath) throws IOException {
        this.quadgramFrequencies = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        // Leer el archivo de cuadrigramas
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            quadgramFrequencies.put(parts[0], Integer.parseInt(parts[1]));
        }
        reader.close();
        this.totalQuadgrams = quadgramFrequencies.values().stream().mapToLong(Integer::longValue).sum();
    }

    // Calcular la aptitud de un texto basado en cuadrigramas
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
