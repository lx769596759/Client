package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class InitDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public static JProgressBar progressBar;

	/**
	 * Create the dialog.
	 */
	public InitDialog(JFrame frame,String name,boolean modal) {
		super(frame,name,modal);
		setBounds(frame.getX()+231, frame.getY()+150, 437, 166);
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setBounds(20, 80, 376, 27);
			contentPanel.add(progressBar);
		}
		
		JLabel label = new JLabel("\u6B63\u5728\u521D\u59CB\u5316\uFF0C\u8BF7\u7A0D\u540E...");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
		label.setBounds(10, 29, 140, 27);
		contentPanel.add(label);
	}

}
