import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class EnvLoader {

    private static final String ENV_FILE_NAME = ".env";
    private static final Map<String, String> ENV_VALUES = loadEnvFile();

    private EnvLoader() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String get(String key, String defaultValue) {
        String fileValue = ENV_VALUES.get(key);

        if (fileValue != null && !fileValue.isBlank()) {
            return fileValue;
        }

        String systemValue = System.getenv(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }

        return defaultValue;
    }

    private static Map<String, String> loadEnvFile() {
        Path envPath = Path.of(ENV_FILE_NAME);

        if (!Files.exists(envPath)) {
            return Map.of();
        }

        Map<String, String> values = new HashMap<>();

        try {
            for (String line : Files.readAllLines(envPath)) {
                addEnvValue(values, line);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read .env file", e);
        }

        return values;
    }

    private static void addEnvValue(Map<String, String> values, String line) {
        String trimmedLine = line.trim();

        if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
            return;
        }

        int separatorIndex = trimmedLine.indexOf('=');

        if (separatorIndex <= 0) {
            return;
        }

        String key = trimmedLine.substring(0, separatorIndex).trim();
        String value = trimmedLine.substring(separatorIndex + 1).trim();

        values.put(key, removeQuotes(value));
    }

    private static String removeQuotes(String value) {
        if (value.length() < 2) {
            return value;
        }

        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }

        if (value.startsWith("'") && value.endsWith("'")) {
            return value.substring(1, value.length() - 1);
        }

        return value;
    }
}
