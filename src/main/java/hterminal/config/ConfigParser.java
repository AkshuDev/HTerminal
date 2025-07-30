package hterminal;

import java.io.*;
import java.util.*;

public class ConfigParser {
	private final Map<String, String> config = new HashMap<>();

	public ConfigParser(String path) {
		parseConfig(path);
	}

	private void parseConfig(String path) {
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				// Skip empty or coments
				if (line.isEmpty() || line.startsWith("#")) continue;

				int equals = line.indexOf('=');
				if (equals > 0) {
					String key = line.substring(0, equals).trim();
					String value = line.substring(equals + 1).trim();
					config.put(key, value);
				}
			}
		} catch (IOException e) {
			System.err.println("ConfigPaser: Failed to parser config: " + e.getMessage());
		}
	}

	public String get(String key) {
		return config.get(key);
	}

	public String getOrDefault(String key, String def) {
		return config.getOrDefault(key, def);
	}

	public boolean getBoolean(String key, boolean def) {
		String value = config.get(key);
		if (value == null) return def;
		return value.equalsIgnoreCase("true");
	}

	public int getInt(String key, int def) {
		try {
			return Integer.parseInt(config.getOrDefault(key, String.valueOf(def)));
		} catch (NumberFormatException e) {
			return def;
		}
	}

	public float getFloat(String key, float def) {
		try {
			return Float.parseFloat(config.getOrDefault(key, String.valueOf(def)));
		} catch (NumberFormatException e) {
			return def;
		}
	}
}
