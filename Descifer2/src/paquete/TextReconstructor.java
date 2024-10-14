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
        StringBuilder reconstructedText = new StringBuilder();
        int decryptedIndex = 0;

        for (int i = 0; i < originalCiphertext.length(); i++) {
            char originalChar = originalCiphertext.charAt(i);
            // Si el carácter original es un espacio, lo agregamos al texto reconstruido
            if (originalChar == ' ') {
                reconstructedText.append(' ');
            } else if (punctuationPositions.contains(i) || originalChar == '\'' || originalChar == '\"') {
                // Si hay un signo de puntuación o comillas en la posición original, lo agregamos
                reconstructedText.append(originalChar);
            } else {
                // Agregamos el carácter desencriptado solo si hay caracteres restantes
                if (decryptedIndex < decryptedText.length()) {
                    char decryptedChar = decryptedText.charAt(decryptedIndex);
                    
                    // Mantener la mayúscula si el carácter original es mayúscula
                    if (Character.isUpperCase(originalChar)) {
                        reconstructedText.append(Character.toUpperCase(decryptedChar));
                    } else {
                        reconstructedText.append(Character.toLowerCase(decryptedChar));
                    }
                    decryptedIndex++;
                }
            }
        }

        return reconstructedText.toString();
    }

    private List<Integer> findPunctuationPositions() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < originalCiphertext.length(); i++) {
            char c = originalCiphertext.charAt(i);
            if (c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':' || c == '\'' || c == '\"') {
                positions.add(i);
            }
        }
        return positions;
    }
}
