package hterminal.ui;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.*;

import hterminal.ConfigParser;

public class TerminalWindow extends JFrame {
	private JTextArea terminalArea;
	private ConfigParser config;
	private int width;
	private int height;
	private int font_size;
	private String font;
	private float opacity;
	private boolean blur;

	public TerminalWindow(ConfigParser config, int w, int h, int font_size, String font, float opacity, boolean blur) {
		this.config = config;

		this.width = config.getInt("width", w);
		this.height = config.getInt("height", h);
		this.font_size = config.getInt("font-size", font_size);
		this.font = config.getOrDefault("font", font);
		this.opacity = config.getFloat("opacity", opacity);
		this.blur = config.getBoolean("blur", blur);

		initGUI();
	}

	private void initGUI() {
		// Window Settings
		setTitle("HTerminal");
		setSize(width, height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(false);

		// Set Opacity if it is supported
		if (GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT)) {
			setOpacity(opacity);
		}

		// Main Terminal Area
		terminalArea = new JTextArea();
		terminalArea.setFont(new Font(font, Font.PLAIN, font_size));
		terminalArea.setLineWrap(true);
		terminalArea.setWrapStyleWord(true);
		terminalArea.setEditable(false);

		// Foreground & Background
		try {
			// Get fg and bg from config here
			String bgHex = config.getOrDefault("bg-color", "#000000").replace("#", "");
			String fgHex = config.getOrDefault("fg-color", "#00ff00").replace("#", "");
			Color bgColor = new Color(Integer.parseInt(bgHex, 16));
			Color fgColor = new Color(Integer.parseInt(fgHex, 16));

			terminalArea.setBackground(bgColor);
			terminalArea.setForeground(fgColor);
		} catch (NumberFormatException e) {
			System.err.println("Invalid Color in config, using defaults");
			terminalArea.setBackground(new Color(0, 0, 0));
			terminalArea.setForeground(new Color(0, 255, 0));
		}

		// Auto-scroll
		DefaultCaret caret = (DefaultCaret) terminalArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		boolean AddScrollPane = config.getBoolean("scroll-pane", true);
		
		if (AddScrollPane) {
			JScrollPane scrollPane = new JScrollPane(terminalArea);
			scrollPane.setBorder(BorderFactory.createEmptyBorder());

			add(scrollPane, BorderLayout.CENTER);
		}

		setVisible(true);

		// Apply blur
		if (blur) {
			applyBlurEffect();
		}
		
		if (config.getBoolean("print-welcome", true)) {
			printWelcome();
		}
	}

	private void printWelcome() {
		println("Welcome to HTerminal");
	}

	private void println(String message) {
		terminalArea.append(message + '\n');
	}

	private void print(String message) {
		terminalArea.append(message);
	}

	private void applyBlurEffect() {
		System.out.println("Sorry, blur is not added yet!");
	}
}
