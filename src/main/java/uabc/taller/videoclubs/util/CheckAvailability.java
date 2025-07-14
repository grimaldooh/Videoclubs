package uabc.taller.videoclubs.util;

public class CheckAvailability {

    private CheckAvailability() {
    }

    public static String check(String value) {
        if (value == null || value.isEmpty() || value.isBlank())
            return "N/D";
        return value;
    }

    public static String check(Integer value) {
        if (value == null)
            return "0";
        return String.valueOf(value);
    }
}
