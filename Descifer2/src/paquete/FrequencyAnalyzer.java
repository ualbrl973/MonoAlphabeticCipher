package paquete;

import java.util.*;

public class FrequencyAnalyzer {
    private static final char[] ENGLISH_FREQUENCY = "ETAOINSHRDLCUMWFGYPBVKJXQZ".toCharArray();
    
    // Método para analizar la frecuencia de letras en el texto cifrado
    public static Map<Character, Integer> analyzeFrequency(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();

        // Inicializamos todas las letras del alfabeto con frecuencia 0
        for (char c = 'A'; c <= 'Z'; c++) {
            frequencyMap.put(c, 0);
        }

        // Recorremos el texto cifrado para actualizar las frecuencias reales
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c);
                frequencyMap.put(c, frequencyMap.get(c) + 1);
            }
        }

        return frequencyMap;
    }

    // Método para ordenar las letras del texto cifrado por frecuencia
    public static List<Map.Entry<Character, Integer>> sortFrequency(Map<Character, Integer> frequencyMap) {
        List<Map.Entry<Character, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());
        frequencyList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return frequencyList;
    }

    // Método para crear la clave de descifrado en forma de array de char
    public static char[] createDecryptionKey(List<Map.Entry<Character, Integer>> sortedCipherFrequency) {
        char[] decryptionKey = new char[26]; // Clave de 26 letras
        Arrays.fill(decryptionKey, ' ');     // Inicializamos la clave con espacios

        int i = 0;
        for (Map.Entry<Character, Integer> entry : sortedCipherFrequency) {
            if (i < ENGLISH_FREQUENCY.length) {
                decryptionKey[entry.getKey() - 'A'] = ENGLISH_FREQUENCY[i]; // Asignamos la letra más frecuente
                i++;
            }
        }

        return decryptionKey;
    }
}
