package util;

/**
 * Permet de verifier de l'etat d'un String
 */
public final class StringChecker {

    /**
     * Verifie si un String est null ou est uniquement compose d'espaces
     * @param value le String a tester
     * @return true si value est null ou est uniquement compose d'espaces, sinon false
     */
    public static boolean IsNullOrWhiteSpace(String value) {
        return value == null || isWhiteSpace(value);
    }

    /**
     * Verifie si un String est uniquement compose d'espaces
     * @param s le String a tester
     * @return true si s est uniquement compose d'espaces, sinon false
     */
    private static boolean isWhiteSpace(String s) {
        int length = s.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (s.charAt(i) > ' ') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
