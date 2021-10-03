package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bezeroa;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class BezeroGUI extends JFrame {

	private JPanel contentPane;
	private BezeroGUI nireFrame = this;
	private JLabel lblCash;
	private int kop = 0;
	private static BLFacade facade;
	private Bezeroa bezero;
	private static String language = MainGUI.getLanguage();

	
	/**
	 * Create the frame.
	 */
	public BezeroGUI(MainGUI mainFrame, Bezeroa bezeroa) {
		language = MainGUI.getLanguage();
		this.bezero = bezeroa;
		facade = MainGUI.getBusinessLogic();
		
		//Leihoa ixtean MainGui frame-a irekiko da.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.setVisible(true);
			}
		});
		
		addComponentListener(new ComponentAdapter() { 
			public void componentShown(ComponentEvent e) {
				bezero = facade.getUserByUserName(bezero.getUserName());
				double cash = bezero.getCash();
				lblCash.setText(ResourceBundle.getBundle(language).getString("cash")+ cash);
		   }
		});
		
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Bezeroak duen dirua erakusten du.
		lblCash = new JLabel("Cash: " + bezero.getCash());
		lblCash.setBounds(323, 11, 82, 21);
		contentPane.add(lblCash);
		
		//LogOut botoia. Hau sakatzean MainGui frame-a irekiko da.
		JButton btnLogout = new JButton(ResourceBundle.getBundle(language).getString("logOut"));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.setVisible(true);
				nireFrame.dispose();
			}
		});
		btnLogout.setBounds(205, 274, 200, 26);
		contentPane.add(btnLogout);
		
		JButton btnQueryquestion = new JButton(ResourceBundle.getBundle(language).getString("QueryQueries"));
		btnQueryquestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		btnQueryquestion.setBounds(20, 43, 200, 26);
		contentPane.add(btnQueryquestion);
		
		JButton btnCashInOut = new JButton(ResourceBundle.getBundle(language).getString("cashInOut"));
		btnCashInOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nireFrame.setVisible(false);
				JFrame a = new CashInOutGUI(nireFrame, bezero);
				a.setVisible(true);
			}
		});
		btnCashInOut.setBounds(20, 154, 200, 26);
		contentPane.add(btnCashInOut);
		
		JButton btnBet = new JButton(ResourceBundle.getBundle(language).getString("bet"));
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nireFrame.setVisible(false);
				JFrame a = new BetGUI(nireFrame, bezero);
				a.setVisible(true);
			}
		});
		btnBet.setBounds(20, 80, 200, 26);
		contentPane.add(btnBet);
		
		JButton btnTransaction = new JButton(ResourceBundle.getBundle(language).getString("transactions"));
		btnTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TransactionsGUI a = new TransactionsGUI(nireFrame, bezero);
				a.setVisible(true);
			}
		});
		btnTransaction.setBounds(20, 191, 200, 26);
		contentPane.add(btnTransaction);
		
		JButton btnKopituApustuak = new JButton(ResourceBundle.getBundle(language).getString("copyBets"));
		btnKopituApustuak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KopiatuGUI k = new KopiatuGUI(nireFrame, bezeroa);
				k.setVisible(true);
			}
		});
		btnKopituApustuak.setBounds(20, 117, 200, 26);
		contentPane.add(btnKopituApustuak);
		
	}
}