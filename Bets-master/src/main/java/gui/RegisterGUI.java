package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private static BLFacade facade;
	private RegisterGUI nireFrame = this;
	
	private JLabel lblError;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblRepeatPassowrd;
	
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	private JButton registerButton;
	private JButton backButton;
	
	private static String language;

	
	public RegisterGUI(MainGUI mainFrame) {
		
		String language = MainGUI.getLanguage();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
		
		facade = MainGUI.getBusinessLogic();
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userNameField = new JTextField();
		userNameField.setBounds(162, 29, 249, 32);
		contentPane.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(162, 72, 249, 32);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(162, 116, 249, 32);
		contentPane.add(passwordField_1);
		
		lblError = new JLabel(ResourceBundle.getBundle(language).getString("userexists"));
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(149, 231, 169, 32);
		contentPane.add(lblError);
		lblError.setVisible(false);
		
		lblUsername = new JLabel(ResourceBundle.getBundle(language).getString("username"));
		lblUsername.setBounds(22, 37, 141, 15);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel(ResourceBundle.getBundle(language).getString("password"));
		lblPassword.setBounds(22, 80, 141, 15);
		contentPane.add(lblPassword);
		
		lblRepeatPassowrd = new JLabel(ResourceBundle.getBundle(language).getString("rpassword"));
		lblRepeatPassowrd.setBounds(22, 124, 141, 15);
		contentPane.add(lblRepeatPassowrd);
		
		registerButton = new JButton(ResourceBundle.getBundle(language).getString("register"));
		registerButton.setBounds(22, 169, 141, 32);
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String password = new String(passwordField.getPassword());
				String password2 = new String(passwordField_1.getPassword());
				if(password.equals(password2)) {
					boolean correct = facade.register(userNameField.getText(), password);
					if(!correct) {
						lblError.setText(ResourceBundle.getBundle(language).getString("userexists"));
						lblError.setForeground(new Color(255, 0, 0));
						lblError.setVisible(true);
					}
					else {
						lblError.setText(ResourceBundle.getBundle(language).getString("registered"));
						lblError.setForeground(new Color(0, 150, 0));
						lblError.setVisible(true);
					}
				}
				else {
					lblError.setText(ResourceBundle.getBundle(language).getString("mpassword"));
					lblError.setForeground(new Color(255, 0, 0));
					lblError.setVisible(true);
				}	
			}
		});
		contentPane.add(registerButton);
		
		backButton = new JButton(ResourceBundle.getBundle(language).getString("back"));
		backButton.setBounds(286, 169, 125, 32);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setVisible(true);
				nireFrame.dispose();
			}
		});
		contentPane.add(backButton);	
	}
	
}