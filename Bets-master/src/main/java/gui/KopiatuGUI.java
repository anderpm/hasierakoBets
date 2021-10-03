package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bezeroa;
import domain.Copy;
import domain.Pronostic;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class KopiatuGUI extends JFrame {
	
	private String language = MainGUI.getLanguage();
	private static final long serialVersionUID = 1L; 

	private static BLFacade facade;
	
	private JTable tablePronostics = new JTable();

	private DefaultTableModel tableModelPronostics;
	
	private JScrollPane scrollPanePronostic = new JScrollPane();

	private KopiatuGUI nireFrame = this;
	private JTextField textField;
	private DefaultTableModel tableModel;
	private JTextField textEhunekoa;
	
	public KopiatuGUI(BezeroGUI bezeroFrame, Bezeroa bezero)
	{
		try
		{
			jbInit(bezeroFrame, bezero);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void jbInit(BezeroGUI bezeroFrame, Bezeroa bezero) throws Exception
	{
		
		facade = MainGUI.getBusinessLogic();

	
		String[] columnNamesPronostic = new String[] {
				ResourceBundle.getBundle(language).getString("user"), 
		};
		
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(450, 350));
		this.getContentPane().add(scrollPanePronostic, null);

		scrollPanePronostic.setBounds(new Rectangle(52, 36, 326, 116));
		scrollPanePronostic.setViewportView(tablePronostics);

		tableModelPronostics = new DefaultTableModel(null, columnNamesPronostic);

		tableModelPronostics.setDataVector(null, columnNamesPronostic);
		tableModelPronostics.setColumnCount(1);
			
		Vector<Bezeroa> bezeroak = facade.getBezeroak();
				
				
		tablePronostics.setModel(tableModelPronostics);
		
		JLabel lblHautatuKopiatuNahi = new JLabel(ResourceBundle.getBundle(language).getString("copyText"));
		lblHautatuKopiatuNahi.setBounds(52, 11, 326, 14);
		getContentPane().add(lblHautatuKopiatuNahi);
		
		JButton btnKopiatu = new JButton(ResourceBundle.getBundle(language).getString("copy"));
		btnKopiatu.setBounds(52, 213, 89, 23);
		btnKopiatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tablePronostics.getSelectedRow();
				String usuario = (String)tableModelPronostics.getValueAt(i,0);
				System.out.println(usuario);
//				facade.addKopiatuNi(bezero, usuario);

				Bezeroa b = facade.getUserByUserName(usuario);
				String e = textEhunekoa.getText();
				Copy c = new Copy(Double.parseDouble(e), b);
				int id = c.getId();
				boolean res = facade.addKopiatuNi(usuario, Double.parseDouble(e), bezero);
				if(res) {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle(language).getString("copyMSG"), "", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle(language).getString("errorCopyMSG"), "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		getContentPane().add(btnKopiatu);
		
		textEhunekoa = new JTextField();
		textEhunekoa.setBounds(140, 172, 86, 20);
		getContentPane().add(textEhunekoa);
		textEhunekoa.setColumns(10);
		
		JLabel lblEhunekoa = new JLabel(ResourceBundle.getBundle(language).getString("percentaje"));
		lblEhunekoa.setBounds(52, 175, 78, 14);
		getContentPane().add(lblEhunekoa);
		
		JButton backButton = new JButton(ResourceBundle.getBundle(language).getString("Close"));
		backButton.setBounds(289, 254, 89, 23);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bezeroFrame.setVisible(true);
				nireFrame.dispose();
			}
		});
		getContentPane().add(backButton);
		
		
		for (Bezeroa b:bezeroak){
			Vector<Object> row = new Vector<Object>();
					
			row.add(b.getUserName());
			row.add(b);
			tableModelPronostics.addRow(row);	
		}
		
	}
	
}