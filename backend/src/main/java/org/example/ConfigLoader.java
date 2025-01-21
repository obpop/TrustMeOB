

public class ConfigLoader {

    public String getApiKey(String key) {
        String value = System.getenv(key);
        if (value == null) {
            throw new RuntimeException("Environment variable " + key + " not set");
        }
        return value;
    }
}
