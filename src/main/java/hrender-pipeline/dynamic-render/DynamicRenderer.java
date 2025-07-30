package hrenderpipeline.dynamicrender;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

public class DynamicRenderer extends JComponent implements Runnable {
	private int FPS = 60; // 60 ticks per second (60 fps)
	private volatile boolean RenderRunning = true;
	private Thread RenderThread;

	// Shared state
	private BufferedImage Frame;
	private int width, height;
	//private final RenderTarget Target;

	//public interface RenderTarget {
	//	void draw(BufferedImage frame);
	//}
	
	public DynamicRenderer(int width, int height, int FPS) {
		Configure(width, height, FPS);
	}

	public void Configure(int width, int height, int FPS) {
		this.width = width;
		this.height = height;
		this.FPS = FPS;
		///this.Target = Target;

		this.Frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public void Ignite() {
		RenderThread = new Thread(this, "Dynamic-Render-Thread");
		RenderRunning = true;

		RenderThread.start();
	}

	@Override
	public void run() {
		long delay = 1000L / FPS;

		while (RenderRunning) {
			long startTime = System.currentTimeMillis();

			repaint();

			long elapsed = System.currentTimeMillis() - startTime;
			long sleepTime = delay - elapsed;

			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ignored) {}
			}
		}
	}

	public BufferedImage GetBuffer() {
		return Frame;
	}

	public void SetBuffer(BufferedImage Frame) {
		this.Frame = Frame;
	}

	public void Stop() {
		RenderRunning = false;
		try {
			RenderThread.join();
		} catch (InterruptedException ignored) {}
	}

	public void SetFPS(int NewFPS) {
		this.FPS = NewFPS;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (Frame == null) {
			return;
		}

		g.drawImage(Frame, 0, 0, this);
	}
}
