package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezeroa;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class CashInOutGUI extends JFrame{
	
	private JPanel contentPane;
	private static BLFacade facade;
	private CashInOutGUI nireFrame = this;
	
	private JButton backButton;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private String language = MainGUI.getLanguage();
	private JLabel lblCash;
	private double cash;
	private Bezeroa bez;
	
	JRadioButton rdbtnSetLimit;
	
	public CashInOutGUI(BezeroGUI BezeroFrame, Bezeroa bezero) {
		language = MainGUI.getLanguage();
		bez = bezero;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				BezeroFrame.setVisible(true);
				
			}
		});
		
		cash = bez.getCash();

		
		facade = MainGUI.getBusinessLogic();
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		backButton = new JButton(ResourceBundle.getBundle(language).getString("Close"));
		backButton.setBounds(301, 169, 125, 32);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BezeroFrame.setVisible(true);
				nireFrame.dispose();
			}
		});
		contentPane.add(backButton);	
		
		textField = new JTextField();
		textField.setBounds(53, 115, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnDiruaSartu = new JRadioButton(ResourceBundle.getBundle(language).getString("putMoney"));
		buttonGroup.add(rdbtnDiruaSartu);
		rdbtnDiruaSartu.setSelected(true);
		rdbtnDiruaSartu.setBounds(53, 60, 109, 23);
		contentPane.add(rdbtnDiruaSartu);
		
		JRadioButton rdbtnDiruaAtera = new JRadioButton(ResourceBundle.getBundle(language).getString("leaveMoney"));
		buttonGroup.add(rdbtnDiruaAtera);
		rdbtnDiruaAtera.setBounds(165, 60, 109, 23);
		contentPane.add(rdbtnDiruaAtera);
		
		JLabel jlabeltransakziomota = new JLabel(ResourceBundle.getBundle(language).getString("transactionType"));
		jlabeltransakziomota.setBounds(43, 39, 109, 14);
		contentPane.add(jlabeltransakziomota);
		
		JLabel jlabeldirukop = new JLabel(ResourceBundle.getBundle(language).getString("moneyAmount"));
		jlabeldirukop.setBounds(43, 90, 96, 14);
		contentPane.add(jlabeldirukop);
		
		JLabel lblError = new JLabel("");
		lblError.setForeground(new Color(0, 150, 0));
		lblError.setBounds(37, 221, 159, 14);
		contentPane.add(lblError);
		lblError.setVisible(false);
		
		JButton btnKonfirmatu = new JButton(ResourceBundle.getBundle(language).getString("confirm"));
		btnKonfirmatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double zenbat = Double.parseDouble(textField.getText());
				if(rdbtnDiruaSartu.isSelected()) {
					boolean ema = facade.diruaSartu(bez.getUserName(), zenbat);
					if(ema==true) {
						cash += zenbat;
						bez = facade.getUserByUserName(bez.getUserName());
						lblCash.setText(ResourceBundle.getBundle(language).getString("cash") + cash);
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionSuccess"));
						lblError.setForeground(new Color(0, 255, 0));
						lblError.setVisible(true);
					}else {
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionWrong"));
						lblError.setForeground(new Color(255, 0, 0));
						lblError.setVisible(true);
					}
				} 
				if(rdbtnDiruaAtera.isSelected()) {
					boolean ema = facade.diruaAtera(bez.getUserName(), zenbat);
					if(ema==true) {
						cash -= zenbat;
						bez = facade.getUserByUserName(bez.getUserName());
						lblCash.setText(ResourceBundle.getBundle(language).getString("cash") + cash);
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionSuccess"));
						lblError.setForeground(new Color(0, 255, 0));
						lblError.setVisible(true);
					}else {
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionWrong"));
						lblError.setForeground(new Color(255, 0, 0));
						lblError.setVisible(true);
					}
				}
				if(rdbtnSetLimit.isSelected()) {
					boolean ema = facade.setLimit(zenbat, bez);
					if(ema==true) {
						bez = facade.getUserByUserName(bez.getUserName());
						lblCash.setText(ResourceBundle.getBundle(language).getString("cash") + cash);
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionSuccess"));
						lblError.setForeground(new Color(0, 255, 0));
						lblError.setVisible(true);
					}else {
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionWrong"));
						lblError.setForeground(new Color(255, 0, 0));
						lblError.setVisible(true);
					}
				}
				
			}
		});
		btnKonfirmatu.setBounds(37, 169, 125, 32);
		contentPane.add(btnKonfirmatu);
		
		lblCash = new JLabel(ResourceBundle.getBundle(language).getString("cash") + cash);
		lblCash.setBounds(306, 10, 109, 20);
		contentPane.add(lblCash);
		
		rdbtnSetLimit = new JRadioButton(ResourceBundle.getBundle(language).getString("setLimit"));
		buttonGroup.add(rdbtnSetLimit);
		rdbtnSetLimit.setBounds(286, 61, 103, 21);
		contentPane.add(rdbtnSetLimit);
		
		JButton btnCancel = new JButton(ResourceBundle.getBundle(language).getString("cancel"));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!bez.calculateDate()) {
					bez = facade.getUserByUserName(bez.getUserName());
					lblError.setText(ResourceBundle.getBundle(language).getString("transactionWrong"));
					lblError.setForeground(new Color(255, 0, 0));
					lblError.setVisible(true);
				}
				else {
					
					boolean res = facade.cancelLimit(bezero);
					if(res) {
						bez = facade.getUserByUserName(bez.getUserName());
						lblError.setText(ResourceBundle.getBundle(language).getString("transactionSuccess"));
						lblError.setForeground(new Color(0, 255, 0));
						lblError.setVisible(true);
					}
					
				}
				
			}
		});
		btnCancel.setBounds(175, 169, 111, 32);
		contentPane.add(btnCancel);
		
	}
}