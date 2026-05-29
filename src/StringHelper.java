public class StringHelper {
    public int tryParseString(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }
}
