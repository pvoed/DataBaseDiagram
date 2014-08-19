
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The DisksApp class is a "frame", which is a top-level window.
 * Its only purpose in this program is as a container for the
 * DisksPanel, which is where all of the gameplay and
 * GUI operations occur.
 * 
 * You won't need to change this class.
 */
public class DiagramApp extends JFrame {
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		DiagramApp app = new DiagramApp();
		DiagramPanel panel = new DiagramPanel();
		app.getContentPane().add(panel, BorderLayout.CENTER);
		app.pack();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	
	}
}
