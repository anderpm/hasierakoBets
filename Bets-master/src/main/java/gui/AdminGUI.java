package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AdminGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton btnCreatePronostic = null;

    private static BLFacade appFacadeInterface;
	private String language;

	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	protected JLabel jLabelSelectOption;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnCreateEvent = null;
	private JButton btnLogout;
	private AdminGUI nireFrame = this;
	private MainGUI mainFrame;
	
	
	/**
	 * This is the default constructor
	 */
	public AdminGUI(MainGUI mainFrame) {
		super();

		this.mainFrame = mainFrame;
		
		this.language = MainGUI.getLanguage();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				mainFrame.setVisible(true);
				//System.exit(1);
			}
		});
		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(525, 552);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle(language).getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getBtnCreateEvent());
			jContentPane.add(getBtnCreatePronostic());
			jContentPane.add(getBtnLogout());
			
			JButton btnSetResult = new JButton(ResourceBundle.getBundle(language).getString("setResult"));
			btnSetResult.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new SetResultGUI(nireFrame);
					a.setVisible(true);
				}
			});
			btnSetResult.setBounds(87, 311, 332, 54);
			jContentPane.add(btnSetResult);
			
			JButton btnDelEvent = new JButton(ResourceBundle.getBundle(language).getString("deleteEvent"));
			btnDelEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					DelEventsGUI a = new DelEventsGUI();
					a.setVisible(true);
				}
			});
			btnDelEvent.setBounds(87, 376, 332, 54);
			jContentPane.add(btnDelEvent);
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(87, 71, 332, 49);
			jButtonCreateQuery.setText(ResourceBundle.getBundle(language).getString("createQuery"));
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuery;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(87, 251, 332, 49);
			jButtonQueryQueries.setText(ResourceBundle.getBundle(language).getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();

					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}
	
	private JButton getBtnCreateEvent() {
		if (btnCreateEvent == null) {
			btnCreateEvent = new JButton();
			btnCreateEvent.setBounds(87, 131, 332, 49);
			btnCreateEvent.setText(ResourceBundle.getBundle(language).getString("createEvent"));
			btnCreateEvent.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateEventGUI();
					a.setVisible(true);
				}
			});
		}
		return btnCreateEvent;
	}
	private JButton getBtnCreatePronostic() {
		if (btnCreatePronostic == null) {
			btnCreatePronostic = new JButton();
			btnCreatePronostic.setBounds(87, 191, 332, 49);
			btnCreatePronostic.setText(ResourceBundle.getBundle(language).getString("createPronostic"));
			btnCreatePronostic.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreatePronosticGUI();
					a.setVisible(true);
				}
			});
		}
		return btnCreatePronostic;
	}
	
	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle(language).getString("SelectOption"));
			jLabelSelectOption.setBounds(87, 11, 332, 48);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle(language).getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle(language).getString("QueryQueries"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle(language).getString("CreateQuery"));
		btnCreateEvent.setText(ResourceBundle.getBundle(language).getString("createEvent"));
		btnCreatePronostic.setText(ResourceBundle.getBundle(language).getString("createPronostic"));
		this.setTitle(ResourceBundle.getBundle(language).getString("MainTitle"));
	}

	private JButton getBtnLogout() {
		if (btnLogout == null) {
			btnLogout = new JButton(ResourceBundle.getBundle(language).getString("logOut"));
			btnLogout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					mainFrame.setVisible(true);
					nireFrame.dispose();
				}
			});
			btnLogout.setBounds(183, 441, 138, 25);
		}
		return btnLogout;
	}
} // @jve:decl-index=0:visual-constraint="0,0"