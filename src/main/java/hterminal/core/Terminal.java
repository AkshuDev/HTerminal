package hterminal.core;

import hterminal.ui.*;
import hterminal.*;

import java.util.*;
import java.io.*;

import javax.swing.SwingUtilities;

public class Terminal {
	private ConfigParser config;

	public Terminal(ConfigParser config) {
		this.config = config;
	}

	public float getOpacity() {
		return config.getFloat("opacity", 1.0f);
	}

	public boolean blurEnabled() {
		return config.getBoolean("blur", false);
	}

	public String handleCommand(String cmd) {
		if (cmd.trim().equals("clear")) return "\033[H\033[2J";
		if (cmd.trim().equals("exit")) System.exit(0);
		return "Unknown Command";
	}

	public void OpenGUI(){
		SwingUtilities.invokeLater(() -> new TerminalWindow(config, 800, 500, 14, "Monospaced", 1.0f, false));
		try {
			new java.util.concurrent.CountDownLatch(1).await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
