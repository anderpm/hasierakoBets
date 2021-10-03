package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Pronostic;
import domain.Question;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;


public class CreatePronosticGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private String language = MainGUI.getLanguage();
	
	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle(language).getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle(language).getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle(language).getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle(language).getString("Close"));

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
	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle(language).getString("EventN"), 
			ResourceBundle.getBundle(language).getString("Event"), 

	};
	
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle(language).getString("QueryN"), 
			ResourceBundle.getBundle(language).getString("Query"),
			ResourceBundle.getBundle(language).getString("minBet")

	};
	private String[] columnNamesPronostic = new String[] {
			ResourceBundle.getBundle(language).getString("fee"), 
			ResourceBundle.getBundle(language).getString("pronostic")

	};
	private JTextField descriptionField;
	private JTextField feeField;
	private final JLabel lblError = new JLabel(ResourceBundle.getBundle(language).getString("error"));

	
	public CreatePronosticGUI()
	{
		language = MainGUI.getLanguage();
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void jbInit() throws Exception
	{
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(888, 501));
		this.setTitle(ResourceBundle.getBundle(language).getString("QueryQueries"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 254, 409, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(274, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

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
				tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(3));
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
				int i = tableQueries.getSelectedRow();
				
				Question q = (Question) tableModelQueries.getValueAt(i,3);
				Vector<Pronostic> pronostics = q.getPronostics();
				tableModelPronostics.setDataVector(null, columnNamesPronostic);

				if (pronostics.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle(language).getString("NoPronostics")+": "+q.getQuestion());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle(language).getString("SelectedEvent")+" "+q.getQuestion());

				for (Pronostic p:pronostics){
					Vector<Object> row = new Vector<Object>();

					row.add(p.getFee());
					row.add(p.getDescription());
					tableModelPronostics.addRow(row);	
				}
				tablePronostics.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronostics.getColumnModel().getColumn(1).setPreferredWidth(268);
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
		
		descriptionField = new JTextField();
		descriptionField.setBounds(456, 213, 208, 25);
		getContentPane().add(descriptionField);
		descriptionField.setColumns(10);
		
		feeField = new JTextField();
		feeField.setBounds(459, 248, 205, 25);
		getContentPane().add(feeField);
		feeField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle(language).getString("description"));
		lblNewLabel.setBounds(673, 219, 92, 19);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle(language).getString("fee"));
		lblNewLabel_1.setBounds(674, 254, 83, 14);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnCreatePronostic = new JButton(ResourceBundle.getBundle(language).getString("createPronostic")); //$NON-NLS-1$ //$NON-NLS-2$
		btnCreatePronostic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = tableQueries.getSelectedRow();
				try {
					Question q = (Question) tableModelQueries.getValueAt(i,3);
					BLFacade facade=MainGUI.getBusinessLogic();
					boolean good = facade.addPronostic(q, descriptionField.getText(), Double.parseDouble(feeField.getText()));
					Vector<Object> row = new Vector<Object>();
					
					if(!good) {
						lblError.setVisible(true);
					}
					else {
						row.add(Double.parseDouble(feeField.getText()));
						row.add(descriptionField.getText());
						tableModelPronostics.addRow(row);
						lblError.setVisible(false);
					}
				}
				catch(Exception e) {
					lblError.setVisible(true);
				}
				
			}
		});
		
		lblError.setVisible(false);
		
		btnCreatePronostic.setBounds(636, 404, 140, 30);
		getContentPane().add(btnCreatePronostic);
		lblError.setBounds(456, 404, 117, 25);
		
		getContentPane().add(lblError);
		
		
		
		
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}