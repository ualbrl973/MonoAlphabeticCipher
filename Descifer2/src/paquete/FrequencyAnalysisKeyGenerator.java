package paquete;

import java.util.List;
import java.util.Map;

public class FrequencyAnalysisKeyGenerator {

    char[] cipherKey;
    public Map<Character, Integer> cipherFrequency;

    public FrequencyAnalysisKeyGenerator(String ciphertext) {
        // Frequency analysis on the ciphertext
        cipherFrequency = FrequencyAnalyzer.analyzeFrequency(ciphertext);
        System.out.println(cipherFrequency);

        // Sort the frequencies of the ciphertext
        List<Map.Entry<Character, Integer>> sortedCipherFrequency = FrequencyAnalyzer.sortFrequency(cipherFrequency);

        // Generate the decryption key based on the frequencies
        this.cipherKey = FrequencyAnalyzer.createDecryptionKey(sortedCipherFrequency);
        
        // Adjust the key to improve decryption
        this.cipherKey = KeyAdjuster.adjustKey(ciphertext, cipherKey);
        
        System.out.println("Initial key generated based on frequency analysis.");
    }

    public char[] getCypherKey() {
        return this.cipherKey;
    }
}
