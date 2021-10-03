package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Admin;
import domain.Bezeroa;
import domain.User;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class MainGUI extends JFrame {

	private JPanel contentPane;
	private static BLFacade appFacadeInterface;
	private MainGUI nireFrame = this;
	
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblError;
	
	private JPasswordField passwordField;
	private JTextField userNameField;
	
	private JButton registerButton;
	private JButton loginButton;
	private JButton queryQuestionButton;
	
	private static String language = "Etiquetas_en";
	private JRadioButton rdbtnEuskera = new JRadioButton("euskera");
	
	private JRadioButton rdbtnEnglish = new JRadioButton("english");
	private JRadioButton rdbtnCastellano = new JRadioButton("castellano");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface = afi;
	}
	
	public MainGUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userNameField = new JTextField();
		userNameField.setBounds(147, 36, 243, 34);
		contentPane.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(147, 82, 243, 34);
		contentPane.add(passwordField);
		
		lblUsername = new JLabel(ResourceBundle.getBundle(language).getString("username"));
		lblUsername.setBounds(58, 45, 91, 15);
		contentPane.add(lblUsername);
		
		lblPassword = new JLabel(ResourceBundle.getBundle(language).getString("password"));
		lblPassword.setBounds(58, 91, 91, 15);
		contentPane.add(lblPassword);
		
		lblError = new JLabel(ResourceBundle.getBundle(language).getString("wronguserpass"));
		lblError.setForeground(Color.RED);
		lblError.setBounds(136, 185, 179, 22);
		contentPane.add(lblError);
		lblError.setVisible(false);
		
		loginButton = new JButton(ResourceBundle.getBundle(language).getString("login"));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				User user = appFacadeInterface.login(userNameField.getText(), new String(passwordField.getPassword()));
				if(user == null) {
					lblError.setVisible(true);
				}
				else if(user instanceof Admin) {
					AdminGUI langile = new AdminGUI(nireFrame);
					langile.setVisible(true);
					nireFrame.setVisible(false);
				}
				else if(user instanceof Bezeroa) {
					BezeroGUI bezero = new BezeroGUI(nireFrame, (Bezeroa)user);
					bezero.setVisible(true);
					nireFrame.setVisible(false);
				}
			}
		});
		loginButton.setBounds(58, 127, 332, 34);
		contentPane.add(loginButton);
		
		registerButton = new JButton(ResourceBundle.getBundle(language).getString("register"));
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nireFrame.setVisible(false);
				RegisterGUI register = new RegisterGUI(nireFrame);
				register.setVisible(true);
			}
		});
		registerButton.setBounds(58, 230, 332, 34);
		contentPane.add(registerButton);
		
		queryQuestionButton = new JButton(ResourceBundle.getBundle(language).getString("QueryQueries"));
		queryQuestionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		queryQuestionButton.setBounds(58, 276, 332, 34);
		contentPane.add(queryQuestionButton);
		buttonGroup.add(rdbtnEuskera);
		rdbtnEuskera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				language = "Etiquetas_eus";
				repaint();
			}
		});
		
		
		rdbtnEuskera.setBounds(43, 334, 106, 23);
		contentPane.add(rdbtnEuskera);
		buttonGroup.add(rdbtnEnglish);
		rdbtnEnglish.setSelected(true);
		rdbtnEnglish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				language = "Etiquetas_en";	
				repaint();
			}
		});
		
		
		rdbtnEnglish.setBounds(149, 334, 106, 23);
		contentPane.add(rdbtnEnglish);
		buttonGroup.add(rdbtnCastellano);
		rdbtnCastellano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				language = "Etiquetas_es";
				repaint();
			}
		});
		
		
		rdbtnCastellano.setBounds(259, 334, 122, 23);
		contentPane.add(rdbtnCastellano);
	}
	
	public void repaint() {
		lblUsername.setText(ResourceBundle.getBundle(language).getString("username"));
		lblPassword.setText(ResourceBundle.getBundle(language).getString("password"));
		loginButton.setText(ResourceBundle.getBundle(language).getString("login"));
		registerButton.setText(ResourceBundle.getBundle(language).getString("register"));
		lblError.setText(ResourceBundle.getBundle(language).getString("wronguserpass"));
		queryQuestionButton.setText(ResourceBundle.getBundle(language).getString("QueryQueries"));
	}
	
	public static String getLanguage() {
		return language;
	}
}
