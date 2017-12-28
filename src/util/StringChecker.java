package util;

/**
 * Permet de vérifier l'état d'un String
 */
public final class StringChecker {

    /**
     * Vérifie si un String est null ou est uniquement composé d'espaces
     * @param value le String a tester
     * @return true si value est null ou est uniquement composé d'espaces, sinon false
     */
    public static boolean IsNullOrWhiteSpace(String value) {
        return value == null || isWhiteSpace(value);
    }

    /**
     * Verifie si un String est uniquement composé d'espaces
     * @param value le String à tester
     * @return true si s est uniquement composé d'espaces, sinon false
     */
    private static boolean isWhiteSpace(String value) {
        int length = value.length();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                if (value.charAt(i) > ' ') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
