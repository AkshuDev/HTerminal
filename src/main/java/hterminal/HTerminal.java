package hterminal;

import java.io.*;
import java.util.*;

import hterminal.core.*;
import hterminal.ui.*;

public class HTerminal {
	private static final String DEFAULT_CONFIG_DIR = System.getProperty("user.home") + File.separator + ".HTerminal";
	private static final String DEFAULT_CONFIG_FILE = DEFAULT_CONFIG_DIR + File.separator + "default.config";

	public static void main(String[] args) {
		HashMap<String, String> parsedArgs = parseArguments(args);

		boolean loadConfig = !parsedArgs.containsKey("no-config");
		String configPath = null;

		// Check if config-default is passed
		if (parsedArgs.containsKey("config-default")) {
			createDefaultConfigFile();
			configPath = DEFAULT_CONFIG_FILE;
		} else if (parsedArgs.containsKey("config-path")) {
			configPath = parsedArgs.get("config-path");
		} else if (loadConfig) {
			File defaultConfig = new File(DEFAULT_CONFIG_FILE);
			if (defaultConfig.exists()) {
				configPath = DEFAULT_CONFIG_FILE;
			} else {
				createDefaultConfigFile();
				configPath = DEFAULT_CONFIG_FILE;
			}
		} else {
			File defaultConfig = new File(DEFAULT_CONFIG_FILE);

			if (defaultConfig.exists()) {
				configPath = DEFAULT_CONFIG_FILE;
			} else {
				createDefaultConfigFile();
				configPath = DEFAULT_CONFIG_FILE;
			}
		}

		// System Debug
		if (parsedArgs.containsKey("debug")) {
			System.out.println("Config Enabled: " + loadConfig);
			System.out.println("Config Path: " + configPath);
			System.out.println("Starting HTerminal...");
		}

		File configFile = new File(configPath);
		if (!configFile.exists()) {
			createDefaultConfigFile();
			configPath = DEFAULT_CONFIG_FILE;
		}

		// Load Config at startup
		ConfigParser config = new ConfigParser(configPath);

		// Show Splash screen
		if (config.getBoolean("show-splash", true)){
			SplashScreen.showSplash();
		}

		// Launch the app
		if (parsedArgs.containsKey("debug")) {
			System.out.println("Starting Terminal...");
		}

		Terminal terminal = new Terminal(config);
		terminal.OpenGUI();

		if (parsedArgs.containsKey("debug")) {
			System.out.println("Terminal Exited");
		}	

		// Exit
		System.exit(0); // No Error
	}

	private static HashMap<String, String> parseArguments(String[] args) {
		HashMap<String, String> map = new HashMap<>();
		for (String arg : args) {
			if (arg.contains(":")) {
				String[] parts = arg.split(":", 2);
				map.put(parts[0], parts[1]);
			} else if (arg.contains("=")) {
				String[] parts = arg.split("=", 2);
				map.put(parts[0], parts[1]);
			} else {
				map.put(arg, "true");
			}
		}

		return map;
	}

	private static void createDefaultConfigFile() {
		System.out.println("Ensuring Default Config File is present");

		File dir = new File(DEFAULT_CONFIG_DIR);
		if (!dir.exists()) {
			boolean created = dir.mkdirs();
			if (!created) {
				System.err.println("Failed to create config directory at " + DEFAULT_CONFIG_DIR);
				System.exit(1); // fail gracefully
			}

			System.out.println("Created Default Directory");
		}

		File configFile = new File(DEFAULT_CONFIG_FILE);
		if (!configFile.exists()) {
			System.out.println("Creating default config file...");

			try(FileWriter writer = new FileWriter(configFile)) {
				String defConfigContents = "# Default Configuration for HTerminal\ntheme=dark\nfontSize=14\nshell=/bin/bash\nblur=false\nshow-splash=true\nopacity=1.0\nwidth=800\nheight=500\nfullscreen=false\nfont=Monospaced\n";
				writer.write(defConfigContents);
				writer.close();
				System.out.println("Created default Config File at: " + DEFAULT_CONFIG_FILE);
			} catch (IOException e) {
				System.err.println("Failed to create default config file at: " + DEFAULT_CONFIG_FILE + "\n\tError: " + e.getMessage());
				System.exit(1); // fail gracefully
			}
		}
	}
}
