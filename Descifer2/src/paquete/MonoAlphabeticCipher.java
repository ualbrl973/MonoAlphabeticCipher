package paquete;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MonoAlphabeticCipher {

    public static void main(String[] args) throws IOException {
        
        // Read the ciphertext
        System.out.println("What is your ciphertext?");
        Scanner scanner = new Scanner(System.in);
        String initialCiphertext = scanner.nextLine();
        String ciphertext = initialCiphertext.toUpperCase().replaceAll("[^A-Z]", "");
        scanner.close();
        
        // test(ciphertext, initialCiphertext); used for the report explanations.
        
        // Set the number of executions 
        int numberOfTests = 25;
        CipherTester cipherTester = new CipherTester(ciphertext, numberOfTests); 
        CipherKey bestCipherKey = cipherTester.executeTests();
        
        String finalBestPlainText = bestCipherKey.decrypt(ciphertext);
        TextReconstructor reconstructor = new TextReconstructor(initialCiphertext, finalBestPlainText); 
        String reconstructedText = reconstructor.reconstruct();
        
        System.out.println(reconstructedText); // Should print the text with spaces    
    }
    
    public static void test(String ciphertext, String initialCiphertext) {
        
        FrequencyAnalyzer fqAnalyzer = new FrequencyAnalyzer();
        
        char[] cipherKey;
        Map<Character, Integer> cipherFrequency;

        // Frequency analysis on the ciphertext
        cipherFrequency = FrequencyAnalyzer.analyzeFrequency(ciphertext);
        System.out.println(cipherFrequency);

        // Sort the frequencies of the ciphertext
        List<Map.Entry<Character, Integer>> sortedCipherFrequency = FrequencyAnalyzer.sortFrequency(cipherFrequency);

        // Generate the decryption key based on the frequencies
        cipherKey = FrequencyAnalyzer.createDecryptionKey(sortedCipherFrequency);
        // Adjust the key to improve decryption
        // cipherKey = KeyAdjuster.adjustKey(ciphertext, cipherKey);
        
        CipherKey cK = new CipherKey(cipherKey);
        
        System.out.println("Key: " + cK);
        String finalBestPlainText = cK.decrypt(ciphertext);
        TextReconstructor reconstructor = new TextReconstructor(initialCiphertext, finalBestPlainText);
        String reconstructedText = reconstructor.reconstruct();
        
        System.out.println("Decrypted text: " + reconstructedText); // Should print the text with spaces
    }
}
