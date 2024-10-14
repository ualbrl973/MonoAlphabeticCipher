package paquete;

import java.util.ArrayList;
import java.util.List;

public class TextReconstructor {
    private final String originalCiphertext;
    private final String decryptedText;

    public TextReconstructor(String originalCiphertext, String decryptedText) {
        this.originalCiphertext = originalCiphertext;
        this.decryptedText = decryptedText;
    }

    public String reconstruct() {
        List<Integer> punctuationPositions = findPunctuationPositions();
        List<Integer> numberPositions = findNumberPositions();
        StringBuilder reconstructedText = new StringBuilder();
        int decryptedIndex = 0;

        for (int i = 0; i < originalCiphertext.length(); i++) {
            char originalChar = originalCiphertext.charAt(i);
            
            // Add spaces directly
            if (originalChar == ' ') {
                reconstructedText.append(' ');
            } else if (punctuationPositions.contains(i)) {
                // Add punctuation marks
                reconstructedText.append(originalChar);
            } else if (numberPositions.contains(i)) {
                // Add numbers
                reconstructedText.append(originalChar);
            } else {
                // Only add a decrypted character if there are remaining characters
                if (decryptedIndex < decryptedText.length()) {
                    char decryptedChar = decryptedText.charAt(decryptedIndex);
                    
                    // Maintain uppercase if the original character is uppercase
                    if (Character.isUpperCase(originalChar)) {
                        reconstructedText.append(Character.toUpperCase(decryptedChar));
                    } else {
                        reconstructedText.append(Character.toLowerCase(decryptedChar));
                    }
                    decryptedIndex++;
                } else {
                    // If no more decrypted characters, add the original character
                    reconstructedText.append(originalChar);
                }
            }
        }

        return reconstructedText.toString();
    }

    private List<Integer> findPunctuationPositions() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < originalCiphertext.length(); i++) {
            char c = originalCiphertext.charAt(i);
            // Include common special characters
            if (c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':' ||
                c == '\'' || c == '\"' || c == '’' || c == '-' || c == '_' ||
                c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' ||
                c == '~' || c == '`' || c == '@' || c == '#' || c == '$' || c == '%' ||
                c == '^' || c == '&' || c == '*' || c == '+' || c == '=' || c == '|' ||
                c == '\\' || c == '/' || c == '<' || c == '>') {
                positions.add(i);
            }
        }
        return positions;
    }

    private List<Integer> findNumberPositions() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < originalCiphertext.length(); i++) {
            char c = originalCiphertext.charAt(i);
            if (Character.isDigit(c)) {
                positions.add(i);
            }
        }
        return positions;
    }
}
