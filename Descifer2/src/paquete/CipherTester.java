package paquete;

import java.io.IOException;

public class CipherTester {

    private String ciphertext;
    private QuadgramAnalyzer quadgramAnalyzer;
    private int rounds;
    
    // Attributes to store the best results
    private double bestOverallFitness;
    private CipherKey bestOverallCipherKey;
    private String bestOverallPlaintext;

    // Objects created only once
    private FrequencyAnalysisKeyGenerator initialKeyGenerator;
    
    public FrequencyAnalysisKeyGenerator getInitialKeyGenerator() {
    	return initialKeyGenerator;
    }

    public CipherTester(String ciphertext, int rounds) {
        try {
            this.quadgramAnalyzer = new QuadgramAnalyzer("/english_quadgrams.txt");
            this.initialKeyGenerator = new FrequencyAnalysisKeyGenerator(ciphertext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ciphertext = ciphertext;
        this.rounds = rounds;
        this.bestOverallFitness = Double.NEGATIVE_INFINITY; // Initialize best fitness
    }

    // Main method that runs all tests
    public CipherKey executeTests() {
        for (int test = 0; test < rounds; test++) {
            System.out.println("Executing round " + (test + 1) + "...");
            CipherKey bestCipherKey = findBestCipherKey();

            // Update the global best key if necessary
            updateBestResults(bestCipherKey);
        }

        System.out.println("Best Overall Fitness: " + bestOverallFitness + " | Best Key: " + bestOverallCipherKey);
        return bestOverallCipherKey;
    }

    // For application progress bar purpose
    public CipherKey executeTests(java.util.function.Consumer<Integer> progressConsumer) {
        for (int test = 0; test < rounds; test++) {
            System.out.println("Executing round " + (test + 1) + "...");
            CipherKey bestCipherKey = findBestCipherKey();

            // Update the global best key if necessary
            updateBestResults(bestCipherKey);

            // Call the consumer to update progress
            progressConsumer.accept(test + 1); // +1 to show current round
        }

        System.out.println("Best Overall Fitness: " + bestOverallFitness + " | Best Key: " + bestOverallCipherKey);
        return bestOverallCipherKey;
    }

    
    // Method to find the best key in a round
    private CipherKey findBestCipherKey() {
        char[] initialKey = initialKeyGenerator.getCypherKey();
        CipherKey cipherKey = new CipherKey(initialKey);

        double bestFitness = quadgramAnalyzer.calculateFitness(cipherKey.decrypt(ciphertext));
        CipherKey bestCipherKey = new CipherKey(cipherKey.getKey());

        // Process of searching for the best key
        for (int iteration = 0; iteration < 10000; iteration++) {
            // Generate a new key by making a small change
            cipherKey.swapRandomCharacters();

            // Calculate the fitness of the new key
            String newPlaintext = cipherKey.decrypt(ciphertext);
            double newFitness = quadgramAnalyzer.calculateFitness(newPlaintext);

            // If the new key is better, update the key and fitness
            if (newFitness > bestFitness) {
                bestFitness = newFitness;
                bestCipherKey.setKey(cipherKey.getKey());
                //System.out.println("Iteration: " + iteration + " | Fitness: " + bestFitness 
                	//	+  " | Best plaintext: " + newPlaintext + " | Key: " + bestCipherKey);
            }

         
            // Reset the key to the best found
            cipherKey.setKey(bestCipherKey.getKey());
        }
        System.out.println("Results of round:"  + " | Fitness: " + bestFitness + " | Key: " + bestCipherKey);

        return bestCipherKey;
    }

    // Method to update the best results
    private void updateBestResults(CipherKey bestCipherKey) {
        double currentFitness = quadgramAnalyzer.calculateFitness(bestCipherKey.decrypt(ciphertext));
        if (currentFitness > bestOverallFitness) {
            bestOverallFitness = currentFitness;
            bestOverallCipherKey = new CipherKey(bestCipherKey.getKey());
            bestOverallPlaintext = bestCipherKey.decrypt(ciphertext);
        }
    }

    // Getters to access the results
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
