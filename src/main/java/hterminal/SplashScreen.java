package hterminal;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
	public SplashScreen(int duration) {
		// Splash Screen size
		int width = 500;
		int height = 300;

		// Center the splash Screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		// Content Pane Setup
		JPanel content = (JPanel) getContentPane();
		content.setBackground(Color.black);
		content.setLayout(new BorderLayout());

		// Logo/Message
		JLabel label = new JLabel("HTerminal", JLabel.CENTER);
		label.setFont(new Font("Monospaced", Font.BOLD, 26));
		label.setForeground(Color.white);

		// Loading info/details
		JLabel info = new JLabel("By - Pheonix Studios / AkshuDev", JLabel.CENTER);
		info.setFont(new Font("Sans-Serif", Font.PLAIN, 14));

		content.add(label, BorderLayout.CENTER);
		content.add(info, BorderLayout.SOUTH);

		setVisible(true);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);
	}

	public static void showSplash() {
		SplashScreen splash = new SplashScreen(3000); // 3000 ms (3 seconds)
		System.out.println("Splash Screen Finished");
		splash.dispose();
	}
}
