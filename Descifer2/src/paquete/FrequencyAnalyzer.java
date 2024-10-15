package paquete;

import java.util.*;

public class FrequencyAnalyzer {
    private static final char[] ENGLISH_FREQUENCY = "ETAOINSHRDLCUMWFGYPBVKJXQZ".toCharArray();
    
    // Method to analyze the frequency of letters in the ciphered text
    public static Map<Character, Integer> analyzeFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        

        // Initialize all letters of the alphabet with frequency 0
        for (char c = 'A'; c <= 'Z'; c++) {
            frequencyMap.put(c, 0);
        }

        // Traverse the ciphered text to update the actual frequencies
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c);
                frequencyMap.put(c, frequencyMap.get(c) + 1);
            }
        }
        
        return frequencyMap;
    }

    // Method to sort the letters of the ciphered text by frequency
    public static List<Map.Entry<Character, Integer>> sortFrequency(Map<Character, Integer> frequencyMap) {
        List<Map.Entry<Character, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());
        frequencyList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return frequencyList;
    }

    // Method to create the decryption key in the form of a char array
    public static char[] createDecryptionKey(List<Map.Entry<Character, Integer>> sortedCipherFrequency) {
        char[] decryptionKey = new char[26]; // Key of 26 letters
        Arrays.fill(decryptionKey, ' ');     // Initialize the key with spaces

        int i = 0;
        for (Map.Entry<Character, Integer> entry : sortedCipherFrequency) {
            if (i < ENGLISH_FREQUENCY.length) {
                decryptionKey[entry.getKey() - 'A'] = ENGLISH_FREQUENCY[i]; // Assign the most frequent letter
                i++;
            }
        }

        return decryptionKey;
    }
}
