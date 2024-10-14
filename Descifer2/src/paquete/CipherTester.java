package paquete;

import java.io.IOException;

public class CipherTester {

    private String ciphertext;
    private QuadgramAnalyzer quadgramAnalyzer;
    private int rounds;
    
    // Atributos para almacenar los mejores resultados
    private double bestOverallFitness;
    private CipherKey bestOverallCipherKey;
    private String bestOverallPlaintext;

    // Objetos creados una sola vez
    private FrequencyAnalysisKeyGenerator initialKeyGenerator;

    public CipherTester(String ciphertext, int rounds) {
        try {
            this.quadgramAnalyzer = new QuadgramAnalyzer("english_quadgrams.txt");
            this.initialKeyGenerator = new FrequencyAnalysisKeyGenerator(ciphertext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ciphertext = ciphertext;
        this.rounds = rounds;
        this.bestOverallFitness = Double.NEGATIVE_INFINITY; // Inicializa el mejor fitness
    }

    // Método principal que ejecuta todas las pruebas
    public CipherKey executeTests() {
        for (int test = 0; test < rounds; test++) {
            System.out.println("Executing round " + (test + 1) + "...");
            CipherKey bestCipherKey = findBestCipherKey();

            // Actualizar la mejor clave global si es necesario
            updateBestResults(bestCipherKey);
        }

        System.out.println("Best Overall Fitness: " + bestOverallFitness + " | Best Key: " + bestOverallCipherKey);
        return bestOverallCipherKey;
    }

    // Método para encontrar la mejor clave en una ronda
    private CipherKey findBestCipherKey() {
        char[] initialKey = initialKeyGenerator.getCypherKey();
        CipherKey cipherKey = new CipherKey(initialKey);

        double bestFitness = quadgramAnalyzer.calculateFitness(cipherKey.decrypt(ciphertext));
        CipherKey bestCipherKey = new CipherKey(cipherKey.getKey());

        // Proceso de búsqueda de mejor clave
        for (int iteration = 0; iteration < 10000; iteration++) {
            // Generar una nueva clave realizando un pequeño cambio
            cipherKey.swapRandomCharacters();

            // Calcular la aptitud de la nueva clave
            String newPlaintext = cipherKey.decrypt(ciphertext);
            double newFitness = quadgramAnalyzer.calculateFitness(newPlaintext);

            // Si la nueva clave es mejor, actualizar la clave y aptitud
            if (newFitness > bestFitness) {
                bestFitness = newFitness;
                bestCipherKey.setKey(cipherKey.getKey());
                //System.out.println("Iteration: " + iteration + " | Fitness: " + bestFitness +  " | Best plaintext: " + newPlaintext + " | Key: " + bestCipherKey);
            }

         
            // Restablecer la clave a la mejor encontada
            cipherKey.setKey(bestCipherKey.getKey());
        }
        System.out.println("Results of round:"  + " | Fitness: " + bestFitness + " | Key: " + bestCipherKey);

        return bestCipherKey;
    }

    // Método para actualizar los mejores resultados
    private void updateBestResults(CipherKey bestCipherKey) {
        double currentFitness = quadgramAnalyzer.calculateFitness(bestCipherKey.decrypt(ciphertext));
        if (currentFitness > bestOverallFitness) {
            bestOverallFitness = currentFitness;
            bestOverallCipherKey = new CipherKey(bestCipherKey.getKey());
            bestOverallPlaintext = bestCipherKey.decrypt(ciphertext);
        }
    }

    // Getters para acceder a los resultados
    public double getBestOverallFitness() {
        return bestOverallFitness;
    }

    public CipherKey getBestOverallCipherKey() {
        return bestOverallCipherKey;
    }

    public String getBestOverallPlaintext() {
        return bestOverallPlaintext;
    }
}
