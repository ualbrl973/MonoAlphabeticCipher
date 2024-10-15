package paquete;

import java.util.HashSet;
import java.util.Set;

public class KeyAdjuster {
    private static final Set<String> COMMON_BIGRAMS = new HashSet<>(Set.of("TH", "HE", "IN", "ER", "AN", "RE", "ON", "AT", "EN", "ND"));
    private static final Set<String> COMMON_TRIGRAMS = new HashSet<>(Set.of("THE", "AND", "ING", "ENT", "ION", "HER", "FOR", "THA"));
    private static final Set<String> COMMON_WORDS = new HashSet<>(Set.of("THE", "BE", "TO", "OF", "AND", "A", "IN", "THAT", "IT", "IS"));

    // Method to calculate the match score based on bigrams, trigrams, and words
    public static double calculatePatternMatchScore(String plaintext) {
        int bigramMatches = countMatches(plaintext, 2, COMMON_BIGRAMS);
        int trigramMatches = countMatches(plaintext, 3, COMMON_TRIGRAMS);
        int wordMatches = countWordMatches(plaintext, COMMON_WORDS);

        // Adjust the score
        return bigramMatches * 1.0 + trigramMatches * 1.5 + wordMatches * 2.0;
    }

    private static int countMatches(String text, int length, Set<String> commonPatterns) {
        int matches = 0;
        String normalizedText = text.replaceAll("\\s+", "").toUpperCase();

        for (int i = 0; i <= normalizedText.length() - length; i++) {
            String pattern = normalizedText.substring(i, i + length);
            if (commonPatterns.contains(pattern)) {
                matches++;
            }
        }
        return matches;
    }

    private static int countWordMatches(String text, Set<String> commonWords) {
        String[] words = text.split("\\s+");
        int matches = 0;
        for (String word : words) {
            if (commonWords.contains(word.toUpperCase())) {
                matches++;
            }
        }
        return matches;
    }

    // Method to adjust the decryption key
    public static char[] adjustKey(String ciphertext, char[] decryptionKey) {
        char[] currentKey = decryptionKey.clone();
        double currentScore = calculatePatternMatchScore(decrypt(ciphertext, currentKey));

        boolean improved;
        do {
            improved = false;

            // Try swapping each pair of letters
            for (int i = 0; i < currentKey.length; i++) {
                for (int j = i + 1; j < currentKey.length; j++) {

                    // Create a new key by swapping two letters
                    char[] newKey = currentKey.clone();
                    char temp = newKey[i];
                    newKey[i] = newKey[j];
                    newKey[j] = temp;

                    // Calculate the new score for the modified key
                    String newPlaintext = decrypt(ciphertext, newKey);
                    double newScore = calculatePatternMatchScore(newPlaintext);

                    // If the new score is better, update the key and continue
                    if (newScore > currentScore) {
                        currentScore = newScore;
                        currentKey = newKey.clone();
                        improved = true;
                    }
                }
            }
        } while (improved);

        return currentKey;
    }

    // Function to decrypt a text using a given key
    private static String decrypt(String ciphertext, char[] cipherKey) {
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            int index = c - 'A';
            plaintext.append((char) (cipherKey[index] - 'A' + 'a'));  // Convert to lowercase
        }
        return plaintext.toString();
    }
}
