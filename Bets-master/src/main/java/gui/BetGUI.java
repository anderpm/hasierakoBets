package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bet;
import domain.Bezeroa;
import domain.Copy;
import domain.MultipleBets;
import domain.Pronostic;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class BetGUI extends JFrame {
	private String language = MainGUI.getLanguage();
	private static final long serialVersionUID = 1L; 
	private MultipleBets mb;
	private int mbid;
	private int n = 1000;
	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePronostic = new JScrollPane();
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePronostics = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelPronostics;
	
	JButton btnAddBet;
	JButton btnCreateMB;
	
	boolean good;
	boolean create = false;
	Vector<Pronostic> pronostics = new Vector<Pronostic>();
//	private String[] columnNamesEvents = new String[] {
//			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
//			ResourceBundle.getBundle("Etiquetas").getString("Event"), 
//
//	};
//	
//	private String[] columnNamesQueries = new String[] {
//			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
//			ResourceBundle.getBundle("Etiquetas").getString("Query")
//
//	};
//	private String[] columnNamesPronostic = new String[] {
//			ResourceBundle.getBundle("Etiquetas").getString("Fee"), 
//			ResourceBundle.getBundle("Etiquetas").getString("Pronostic")
//
//	};
	 //$NON-NLS-1$ //$NON-NLS-2$
	 //$NON-NLS-1$ //$NON-NLS-2$

	private BetGUI nireFrame = this;
	private JTextField textField;
	//$NON-NLS-1$ //$NON-NLS-2$

	
	public BetGUI(BezeroGUI bezeroFrame, Bezeroa bezero)
	{
		language = MainGUI.getLanguage();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				bezeroFrame.setVisible(true);
			}
		});
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
		
		String[] columnNamesEvents = new String[] {
				ResourceBundle.getBundle(language).getString("EventN"), 
				ResourceBundle.getBundle(language).getString("Event"), 

		};
		
		String[] columnNamesQueries = new String[] {
				ResourceBundle.getBundle(language).getString("QueryN"), 
				ResourceBundle.getBundle(language).getString("Query"),
				ResourceBundle.getBundle(language).getString("minBet")

		};
		String[] columnNamesPronostic = new String[] {
				ResourceBundle.getBundle(language).getString("fee"), 
				ResourceBundle.getBundle(language).getString("pronostic")

		};
		
		
		
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(888, 501));
		this.setTitle(ResourceBundle.getBundle(language).getString("QueryQueries"));

		JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(language).getString("EventDate"));
		JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle(language).getString("Queries"));
		JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(language).getString("Events"));
		
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 254, 409, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{
				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle(language).getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle(language).getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}
				}
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		
		scrollPaneQueries.setBounds(new Rectangle(40, 278, 364, 116));
		
		scrollPanePronostic.setBounds(new Rectangle(456, 278, 326, 116));
		
		
		 // another column added to allocate ev objects

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelQueries.setDataVector(null, columnNamesQueries);
				tableModelQueries.setColumnCount(4);
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle(language).getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle(language).getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();
					
					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(q.getBetMinimum());
					row.add(q);
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(208);
				tableQueries.getColumnModel().getColumn(2).setPreferredWidth(60);
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(3)); // not shown in JTable
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelPronostics.setDataVector(null, columnNamesPronostic);
				tableModelPronostics.setColumnCount(3);
				int i = tableQueries.getSelectedRow();
				
				Question q = (Question) tableModelQueries.getValueAt(i,3);
				Vector<Pronostic> pronostics = q.getPronostics();


				if (pronostics.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle(language).getString("NoPronostics")+": "+q.getQuestion());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle(language).getString("SelectedEvent")+" "+q.getQuestion());

				for (Pronostic p:pronostics){
					Vector<Object> row = new Vector<Object>();

					row.add(p.getFee());
					row.add(p.getDescription());
					row.add(p);
					tableModelPronostics.addRow(row);	
				}
				tablePronostics.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronostics.getColumnModel().getColumn(1).setPreferredWidth(268);
				tablePronostics.getColumnModel().removeColumn(tablePronostics.getColumnModel().getColumn(2));
			}
				
		});
		
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);


		scrollPanePronostic.setViewportView(tablePronostics);
		tableModelPronostics = new DefaultTableModel(null, columnNamesPronostic);
		
		tablePronostics.setModel(tableModelPronostics);
		tablePronostics.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronostics.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePronostic, null);
		
		
		JLabel lblError = new JLabel(ResourceBundle.getBundle(language).getString("error"));
		lblError.setVisible(false);
		
		getContentPane().add(lblError);
		
		JButton btnBack = new JButton(ResourceBundle.getBundle(language).getString("back"));
		btnBack.setBounds(40, 431, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bezeroFrame.setVisible(true);
				nireFrame.dispose();
			}
		});
		getContentPane().add(btnBack);
		
		textField = new JTextField();
		textField.setBounds(593, 251, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle(language).getString("amount")); 
		lblNewLabel.setBounds(459, 254, 124, 14);
		getContentPane().add(lblNewLabel);
		
		
		JLabel lblEzDuzuHaibeste = new JLabel(ResourceBundle.getBundle(language).getString("enoughCash"));
		lblEzDuzuHaibeste.setBounds(666, 415, 140, 14);
		getContentPane().add(lblEzDuzuHaibeste);
		lblEzDuzuHaibeste.setVisible(false);
		
		JButton btnBet_1 = new JButton(ResourceBundle.getBundle(language).getString("bet"));
		btnBet_1.setBounds(526, 411, 130, 23);
		btnBet_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i = tablePronostics.getSelectedRow();
				int k = tableQueries.getSelectedRow();
				Question q = (Question) tableModelQueries.getValueAt(k,3);
				Pronostic p = (Pronostic) tableModelPronostics.getValueAt(i,2);
				int j = facade.addBet(p, Double.parseDouble(textField.getText()), bezero, q);
				if(j != -1) {
					JOptionPane.showMessageDialog(null, "Bet created correctly", "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		getContentPane().add(btnBet_1);
		
		JButton btnMB = new JButton(ResourceBundle.getBundle(language).getString("startMultipleBet"));
		btnMB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mb = new MultipleBets();
				mb.setUserName(bezero.getUserName());
				mb.setAmount(Double.parseDouble(textField.getText()));
				btnAddBet.setEnabled(true);
				btnCreateMB.setEnabled(true);
				textField.setEnabled(false);
				btnMB.setEnabled(false);
				btnBet_1.setEnabled(false);
			}
		});
		btnMB.setBounds(139, 412, 130, 21);
		getContentPane().add(btnMB);
		
		btnAddBet = new JButton(ResourceBundle.getBundle(language).getString("addBet"));
		btnAddBet.setEnabled(false);
		btnAddBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean contain = false;
				int i = tablePronostics.getSelectedRow();
				int j = tableQueries.getSelectedRow();
				Question q = (Question) tableModelQueries.getValueAt(j,3);
				Pronostic p = (Pronostic) tableModelPronostics.getValueAt(i,2);
				Bet b = new Bet(p, q);
				mb.addBet(b);
				for(Pronostic p1:pronostics) {
					if(p.getId() == p1.getId()) {
						contain = true;
					}
				}
				if(!contain) {
					pronostics.add(p);
					JOptionPane.showMessageDialog(null, ResourceBundle.getBundle(language).getString("addBetMsg"), "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAddBet.setBounds(295, 412, 85, 20);
		getContentPane().add(btnAddBet);
		
		btnCreateMB = new JButton(ResourceBundle.getBundle(language).getString("createMultipleBet"));
		btnCreateMB.setEnabled(false);
		btnCreateMB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double dirua = Double.parseDouble(textField.getText());	
				int j = tableQueries.getSelectedRow();
				Question q = (Question) tableModelQueries.getValueAt(j,3);
				double fee = 1.0;
				for(Bet b:mb.getBets()) {
					fee *= b.getPronostic().getFee();
				}
				mb.setTotalPronostic(fee);
				if(bezero.getCash() < dirua) {
					lblEzDuzuHaibeste.setVisible(true);
					btnAddBet.setEnabled(false);
					btnCreateMB.setEnabled(false);
					textField.setEnabled(true);
					btnMB.setEnabled(true);
					btnBet_1.setEnabled(true);
				}
				else if(q.getBetMinimum() > dirua) {
					lblEzDuzuHaibeste.setVisible(true);
					btnAddBet.setEnabled(false);
					btnCreateMB.setEnabled(false);
					textField.setEnabled(true);
					btnMB.setEnabled(true);
					btnBet_1.setEnabled(true);
				}
				else {
					int i = tablePronostics.getSelectedRow();
					Pronostic p = (Pronostic) tableModelPronostics.getValueAt(i,2);
					
					BLFacade facade = MainGUI.getBusinessLogic();

					int z = facade.addBetMB(mb, pronostics, q);
					if(z != -1) {
						JOptionPane.showMessageDialog(null, ResourceBundle.getBundle(language).getString("multBetMsg"), "", JOptionPane.INFORMATION_MESSAGE);
						btnAddBet.setEnabled(false);
						btnCreateMB.setEnabled(false);
						textField.setEnabled(true);
						btnMB.setEnabled(true);
						btnBet_1.setEnabled(true);
						
					}
					
				}
			}
		});
		btnCreateMB.setBounds(390, 412, 126, 21);
		getContentPane().add(btnCreateMB);
		
	}
}