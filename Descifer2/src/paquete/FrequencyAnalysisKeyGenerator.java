package paquete;

import java.util.List;
import java.util.Map;

public class FrequencyAnalysisKeyGenerator {

    char[] cipherKey;
    public Map<Character, Integer> cipherFrequency;

    public FrequencyAnalysisKeyGenerator(String ciphertext) {
        // Análisis de frecuencias en el texto cifrado
        cipherFrequency = FrequencyAnalyzer.analyzeFrequency(ciphertext);
        System.out.println(cipherFrequency);

        // Ordenamos las frecuencias del texto cifrado
        List<Map.Entry<Character, Integer>> sortedCipherFrequency = FrequencyAnalyzer.sortFrequency(cipherFrequency);

        // Generamos la clave de descifrado basada en las frecuencias
        this.cipherKey = FrequencyAnalyzer.createDecryptionKey(sortedCipherFrequency);
        
        // Ajustar la clave para mejorar el descifrado
        this.cipherKey = KeyAdjuster.adjustKey(ciphertext, cipherKey);
        
        System.out.println("Clave inicial generada en base al análisis de frecuencias.");
    }

    public char[] getCypherKey() {
        return this.cipherKey;
    }
}
