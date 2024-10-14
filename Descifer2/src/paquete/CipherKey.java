package paquete;

import java.util.Random;

public class CipherKey {
    private char[] key;

    public CipherKey(char[] initialKey) {
        this.key = initialKey.clone();
    }

    // Intercambiar aleatoriamente dos caracteres en la clave
    public void swapRandomCharacters() {
        Random random = new Random();
        int index1 = random.nextInt(26);
        int index2 = random.nextInt(26);
        char temp = key[index1];
        key[index1] = key[index2];
        key[index2] = temp;
    }

    // Descifrar un texto utilizando la clave
    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for (char c : ciphertext.toCharArray()) {
            int index = c - 'A';
            plaintext.append((char) (key[index] - 'A' + 'a'));  // Convertir a minúsculas
        }
        return plaintext.toString();
    }

    public char[] getKey() {
        return key;
    }

    public void setKey(char[] newKey) {
        this.key = newKey.clone();
    }

    @Override
    public String toString() {
        return new String(key);
    }
}
