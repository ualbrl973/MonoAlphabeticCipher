package paquete;

import java.io.IOException;
import java.util.Scanner;

public class MonoAlphabeticCipher {

    public static void main(String[] args) throws IOException {
        

        // Leer el texto cifrado
        System.out.println("What is your ciphertext?");
        Scanner scanner = new Scanner(System.in);
        String initialCiphertext = scanner.nextLine();
        String ciphertext = initialCiphertext.toUpperCase().replaceAll("[^A-Z]", "");
        scanner.close();
        
        // Configurar el número de ejecuciones
        int numberOfTests = 25;
        CipherTester cipherTester = new CipherTester(ciphertext, numberOfTests);
        CipherKey bestCipherKey = cipherTester.executeTests();
        
        String finalBestPlainText = bestCipherKey.decrypt(ciphertext);
        TextReconstructor reconstructor = new TextReconstructor(initialCiphertext, finalBestPlainText);
        String reconstructedText = reconstructor.reconstruct();

        System.out.println(reconstructedText); // Debería imprimir el texto con los espacios
    }
}
