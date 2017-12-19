package util;

public abstract class StringChecker {

    public static boolean IsNullOrWhiteSpace(String value) {
        return value == null || isWhiteSpace(value);
    }

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
