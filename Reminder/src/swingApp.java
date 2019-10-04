import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class swingApp {

	JFrame frame;
	static String remindText;

	public static void main(String[] args) {
		new swingApp();
	}

	public swingApp() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				frame = new JFrame("Break Reminder");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new TestPane()).setBackground(Color.gray);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public class TestPane extends JPanel {
		private final JLabel jlab, jlab1, minLab,remindlab,jlab2;
		private int index = 0;
		int copyindex;
		int minute = 0, seconds = 0;
		JButton b1, b2, b3, b4, b5;
		JTextField tfield, mintfield;
		Timer timer;
		int flag = 0;

		Font font = new Font("Courier", Font.BOLD, 30);
		Font font1 = new Font("Courier", Font.BOLD, 17);
		Font font2 = new Font("Courier", Font.BOLD + Font.ITALIC, 17);
		Font font3 = new Font("Courier", Font.BOLD, 14);
		Font textfieldfont = new Font("Courier", Font.BOLD, 20);

		public TestPane() {
			setLayout(null);
			jlab = new JLabel("00:00");
			jlab1 = new JLabel("Reminder in seconds");
			jlab2 =new JLabel("Click here -->");
			minLab = new JLabel("Reminder in minutes");
			remindlab = new JLabel("Set Reminder...");
			Map<TextAttribute, Object> attributes = new HashMap<>(font2.getAttributes());
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			b1 = new JButton("START");
			b2 = new JButton("PAUSE");
			b3 = new JButton("EXIT");
			b4 = new JButton("RESET");

			tfield = new JTextField();
			mintfield = new JTextField();
			jlab1.setBounds(90, 60, 180, 60);
			minLab.setBounds(90, 100, 180, 60);
			remindlab.setBounds(180,7,180,60);
			jlab2.setBounds(50,7,180,60);
			tfield.setBounds(290, 80, 70, 25);
			mintfield.setBounds(290, 120, 70, 25);
			jlab.setBounds(215, 130, 200, 200);
			b1.setBounds(150, 330, 95, 30);
			b2.setBounds(260, 330, 95, 30);
			b4.setBounds(150, 410, 95, 30);
			b3.setBounds(260, 410, 95, 30);
			jlab.setFont(font);
			jlab1.setFont(font1);
			minLab.setFont(font1);
			remindlab.setFont(font2.deriveFont(attributes));
			remindlab.setToolTipText("Click here to set the Reminder message");
			jlab1.setForeground(Color.WHITE);
			minLab.setForeground(Color.WHITE);
			remindlab.setForeground(Color.YELLOW);
			tfield.setFont(textfieldfont);
			mintfield.setFont(textfieldfont);
			jlab2.setFont(font3);
			jlab2.setForeground(Color.yellow);
			tfield.setText("0");
			mintfield.setText("0");
			add(jlab1);
			add(tfield);
			add(mintfield);
			add(jlab);
			add(minLab);
			add(b1);
			add(b2);
			add(b3);
			add(b4);
			add(remindlab);
			add(jlab2);

			b1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int text1 = 0;
					int text2 = 0;
					final int totalsec;
					if (flag == 1) {
						index = 0;
						flag = 0;
					}
					try {
						text1 = Integer.parseInt(tfield.getText());
						text2 = Integer.parseInt(mintfield.getText());
						totalsec = text1 + (text2 * 60);
						if (totalsec == 0) {
							JOptionPane.showMessageDialog(null, "Please Enter Seconds/Minutes in textfield");
						} else {
							b1.setEnabled(false);
							timer = new Timer(1000, new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {

									index++;
									copyindex = index;

									/*
									 * copyindex = copyindex % (24 * 3600); hour
									 * = copyindex / 3600;
									 */

									copyindex %= 3600;
									minute = copyindex / 60;

									copyindex %= 60;
									seconds = copyindex;

									if (minute == 0)
										if(seconds<10)
											jlab.setText("00:0" + seconds);
										else
											jlab.setText("00:" + seconds);
									else
									{
										if(seconds<10 && minute<10)
											jlab.setText("0"+minute + ":0" + seconds);
										else if(seconds>10 && minute<10)
											jlab.setText("0"+minute + ":" + seconds);
										else if (minute>10 && seconds<10)
											jlab.setText(minute + ":0" + seconds);
										else
											jlab.setText(minute + ":" + seconds);
									}
									jlab.setFont(font);
									jlab.setForeground(Color.ORANGE);
									if (index == totalsec) {

										if (SystemTray.isSupported()) {
											// swingApp td1 = new swingApp();
											try {
												swingApp.displayTray();
												jlab.setText("00:00");
												tfield.setText(tfield.getText());
												mintfield.setText(mintfield.getText());
												index = 0;
												timer.stop();
												b1.setEnabled(true);
												
											} catch (MalformedURLException | AWTException e1) {
												// TODO Auto-generated catch
												// block
												e1.printStackTrace();
											}
										} else {
											System.err.println("System tray not supported!");
										}
									}

								}
							});
							timer.setInitialDelay(0);
							timer.start();
						}

					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "Please Enter details in textfields");
					}

				}
			});

			b2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						timer.stop();
						b1.setEnabled(true);
					} catch (NullPointerException npe) {
						JOptionPane.showMessageDialog(null, "Counter is not yet started");
					}

				}
			});

			b3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
			b4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						timer.stop();
						jlab.setText("00:00");
						flag = 1;
						b1.setEnabled(true);
					} catch (NullPointerException npe) {
						JOptionPane.showMessageDialog(null, "Counter is not yet started");
					}

				}
			});
			

			remindlab.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					remindlab.setForeground(Color.YELLOW);
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					remindlab.setForeground(Color.CYAN);
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					remindText = JOptionPane.showInputDialog(null, "Enter the Reminder message");
				}
			});
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(500, 500);
		}
	}

	public static void displayTray() throws AWTException, MalformedURLException {
		// Obtain only one instance of the SystemTray object
		SystemTray tray = SystemTray.getSystemTray();

		// If the icon is a file
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		// Alternative (if the icon is on the classpath):
		// Image image =
		// Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

		TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
		// Let the system resize the image if needed
		trayIcon.setImageAutoSize(true);
		// Set tooltip text for the tray icon
		trayIcon.setToolTip("System tray icon demo");
		tray.add(trayIcon);
		try
		{
			if(remindText.isEmpty()){}				
			else
				trayIcon.displayMessage("Reminder", remindText, MessageType.INFO);
		}
		catch(Exception e)
		{
			trayIcon.displayMessage("Reminder", "Take a Break!!", MessageType.INFO);
		}
		
	}
}
