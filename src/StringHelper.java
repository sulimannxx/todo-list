public final class StringHelper {

    private StringHelper(){
        throw new UnsupportedOperationException("Utility class");
    }

    public static int tryParseString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
