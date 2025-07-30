package hrenderpipeline.staticrender;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class StaticRenderer {
	private final Map<String, Object> config = new HashMap<>();
	private BufferedImage buffer;
	private boolean initialized = false;

	public void configure(Map<String, Object> configuration) {
		config.clear();
		config.putAll(configuration);
	}

	public void initialize(int width, int height) {
		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		initialized = true;
		render();
	}

	private void render() {
		if (!initialized) return;
		Graphics2D g = buffer.createGraphics();

		// Apply anti-aliasing if set in config
		if (Boolean.TRUE.equals(config.get("antialiasing"))) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		// Fill background
		Color bgColor = (Color) config.getOrDefault("background", Color.BLACK);
		g.setColor(bgColor);
		g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());


		// TODO: Add more rendering if needed
		

		g.dispose();
	}

	public void redraw() {
		render();
	}

	public BufferedImage getBuffer() {
		return buffer;
	}
}
