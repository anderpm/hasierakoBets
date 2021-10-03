package gui;

import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.Bezeroa;
import domain.TMBet;
import domain.TCashInOut;
import domain.Transaction;


import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextPane;
import javax.swing.JPanel;

public class TransactionsGUI extends JFrame{

	private TransactionsGUI frame = this;
	
	private String language = MainGUI.getLanguage();
	private JScrollPane scrollPane = new JScrollPane();
	private JTable tableTransaction = new JTable();
	private JTable tableMultiple = new JTable();

	private JScrollPane scrollPane_1 = new JScrollPane();
	private DefaultTableModel tableModelMug;
	private DefaultTableModel tableModelMB;
	
	private String[] columnNamesMug = new String[] {
			ResourceBundle.getBundle(language).getString("transactionN"), 
			ResourceBundle.getBundle(language).getString("transactionType"),
			ResourceBundle.getBundle(language).getString("amount")
	};
	private String[] columnNamesMB = new String[] {
			ResourceBundle.getBundle(language).getString("transactionN"), 
			ResourceBundle.getBundle(language).getString("Queries"),
			ResourceBundle.getBundle(language).getString("pronostic"),
			ResourceBundle.getBundle(language).getString("amount")
	};
	
	

	/**
	 * Create the application.
	 */
	public TransactionsGUI(BezeroGUI g, Bezeroa b) {
		language = MainGUI.getLanguage();
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle(language).getString("Close"));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(202, 391, 89, 23);
		getContentPane().add(btnNewButton);
		
		
		scrollPane.setBounds(28, 25, 449, 171);
		getContentPane().add(scrollPane);
		
		
		scrollPane.setViewportView(tableTransaction);
		tableModelMug = new DefaultTableModel(null, columnNamesMug);
		
		getContentPane().add(scrollPane_1);
		
		scrollPane_1.setViewportView(tableMultiple);
		tableTransaction.setModel(tableModelMug);
		
		tableModelMB = new DefaultTableModel(null, columnNamesMB);
		tableMultiple.setModel(tableModelMB);
		scrollPane_1.setBounds(28, 225, 449, 137);
		
		
		scrollPane_1.setViewportView(tableMultiple);
		tableTransaction.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableTransaction.getColumnModel().getColumn(1).setPreferredWidth(238);
		tableTransaction.getColumnModel().getColumn(2).setPreferredWidth(30);
		
		tableTransaction.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelMB.setDataVector(null, columnNamesMB);
				tableModelMB.setColumnCount(4);
				int i = tableTransaction.getSelectedRow();
				String k = (String) tableTransaction.getValueAt(i,1);
				if (k.equals(ResourceBundle.getBundle(language).getString("MoreThan1Bet"))) {
					int id = (int) tableTransaction.getValueAt(i,0);
					BLFacade facade=MainGUI.getBusinessLogic();
					TMBet b = facade.getTBetByID(id);
					Vector<Object> row = new Vector<Object>();
					Vector<Bet> bs = b.getBets();
					for (Bet b1: bs) {
						row.add(b.getTransactionID());
						row.add(b1.getQuestion().getQuestion());
						row.add(b1.getPronostic().getDescription());
						row.add(b.getBetAmount());
						
						
						tableModelMB.addRow(row);
					}
					
				}
				tableMultiple.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableMultiple.getColumnModel().getColumn(1).setPreferredWidth(238);
				tableMultiple.getColumnModel().getColumn(2).setPreferredWidth(30);
			}
		});
		initialize(g, b);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(BezeroGUI g, Bezeroa b) {
		frame.setBounds(100, 100, 450, 300);
		
		Vector<Transaction> T = b.getMugimenduak();
			tableModelMug.setDataVector(null, columnNamesMug);
			tableModelMug.setColumnCount(3);
			for(Transaction tr: T) {
				Vector<Object> row = new Vector<Object>();
				row.add(tr.getTransactionID());
			
				if(tr instanceof TCashInOut) {
					switch(((TCashInOut) tr).isOperation()) {
						case 0:
							row.add(ResourceBundle.getBundle(language).getString("putMoney"));
							break;
						case 1:
							ResourceBundle.getBundle(language).getString("leaveMoney");
							break;
					}
					row.add(((TCashInOut) tr).getCashAmount());
				} else if(tr instanceof TMBet) {
					switch(((TMBet)tr).isSingleBet()) {
						case 1:
							row.add(((TMBet) tr).getQuestion());
							row.add(((TMBet) tr).getBetAmount());
							break;
						case 0:
							row.add(ResourceBundle.getBundle(language).getString("MoreThan1Bet"));
							row.add(((TMBet) tr).getBetAmount());
							
					}
				}
				tableModelMug.addRow(row);
			}
	
	}
}
