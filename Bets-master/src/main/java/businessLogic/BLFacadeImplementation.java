package businessLogic;
//hola
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.TMBet;
import domain.User;
import domain.Bet;
import domain.Bezeroa;
import domain.Copy;
import domain.Event;
import domain.MultipleBets;
import domain.Pronostic;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	
	DataAccess dbManager;

	
	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
		    dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(false);
			da.initializeDB();
			da.close();

		}
		dbManager=da;		
	}
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	    //The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry=null;
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));	
		
		qry=dbManager.createQuestion(event,question,betMinimum);		
		dbManager.close();
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}
    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	@WebMethod
	public void close() {
		DataAccess dB4oManager=new DataAccess(false);
		dB4oManager.close();
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}
	
	/**
	 * This method calls the data access to create a new User by the given name and password
	 * @param userName the name of the new user
	 * @param password the password of the new user
	 * @return true if it is correctly registered, false if it is already registered
	 */
	@WebMethod
	public boolean register(String userName, String password) {
		dbManager.open(false);
		boolean correct = dbManager.register(userName, password);
		dbManager.close();
		return correct;
	}
	
	/**
	 * This method calls the data access to return User of the given userName and password
	 * @param userName Users name
	 * @param password Users password
	 * @return User if the user exists or null if it doesn't
	 */
	@WebMethod
	public User login(String userName, String password) {
		dbManager.open(false);
		User u = dbManager.login(userName);
		dbManager.close();
		if(u != null && u.getPassword().equals(password)) {
			return u;
		}
		return null;
	}
    
    /**
	 * This method calls the data access to create a new event with the given description and date
	 * @param description the description of the new event
	 * @param eventDate the date of the new event
	 * @return true if the even is successfully created, false if not.
	 */
    @WebMethod
    public boolean createEvent(String description, Date eventDate) {
    	if(new Date().compareTo(eventDate)>0) {
    		return false;
    	}
    	dbManager.open(false);
    	boolean result = dbManager.CreateEvent(description, eventDate);
    	dbManager.close();
    	return result;
    }

	@WebMethod public boolean diruaSartu(String userName, double zenbat) {
		dbManager.open(false);
    	boolean result = dbManager.diruaSartu(userName, zenbat);
    	dbManager.close();
    	return result;
	}
	@WebMethod public boolean diruaAtera(String userName, double zenbat) {
		dbManager.open(false);
    	boolean result = dbManager.diruaAtera(userName, zenbat);
    	dbManager.close();
    	return result;
	}

	@WebMethod public double getCashByUserName(String userName) {
		dbManager.open(false);
		double ema = dbManager.getCashByUserName(userName);
		dbManager.close();
		return ema;
	}
	
	@WebMethod public Bezeroa getUserByUserName(String userName) {
		dbManager.open(false);
		Bezeroa b = dbManager.getUserByUserName(userName);
		dbManager.close();
		return b;
	}

	@WebMethod
	public boolean addPronostic(Question q, String desc, double fee) {
		dbManager.open(false);
		boolean good = dbManager.addPronostic(q, desc, fee);
		dbManager.close();
		return good;
	}
	
	@WebMethod
	public boolean deleteEvent(Event evi) {
		dbManager.open(false);
		boolean ez = dbManager.deleteEvent(evi);
		dbManager.close();
		return ez;
	}

	@WebMethod
	public int addBet(Pronostic p, double zenbat, Bezeroa bezero, Question q) {
		dbManager.open(false);
		int ema = dbManager.addBet(p, zenbat, bezero, q);
		dbManager.close();
		return ema;
	}
	
	@WebMethod 
	public int addBetMB(MultipleBets mb, Vector<Pronostic> p, Question q) {
		dbManager.open(false);
		int ema = dbManager.addBetMB(mb, p, q);
		dbManager.close();
		return ema;
	}
	
	@WebMethod public boolean setResult(Pronostic p) {
		dbManager.open(false);
		boolean good = dbManager.setResult(p);
		dbManager.close();
		return good;
	}

	@WebMethod public void addTBet(Bezeroa bezero, Event event, Question question, Pronostic pronostic, double betAmount, Vector<Bet> b) {
		dbManager.open(false);
		dbManager.addTBet(bezero, event, question, pronostic, betAmount, b);
		dbManager.close();
	}

	@WebMethod public void addTCashInOut(Bezeroa bezero, int operation, double cash) {
		dbManager.open(false);
		dbManager.addTCashInOut(bezero, operation, cash);
		dbManager.close();
	}

	@WebMethod public Vector<Bezeroa> getBezeroak() {
		dbManager.open(false);
		Vector<Bezeroa> res = dbManager.getBezeroak();
		dbManager.close();
		return res;
	}

	@WebMethod 
	public boolean addKopiatuNi(String bezeroa, Double c, Bezeroa b) {
		dbManager.open(false);
		boolean res = dbManager.addKopiatuNi(bezeroa, c, b);
		dbManager.close();
		return res;
	}
	
	@WebMethod
	public boolean setLimit(double limit, Bezeroa b) {
		dbManager.open(false);
		boolean res = dbManager.setLimit(limit, b);
		dbManager.close();
		return res;
	}
	
	public TMBet getTBetByID(int id) {
		dbManager.open(false);
		TMBet b = dbManager.getTBetByID(id);
		dbManager.close();
		return b;
	}
	
	public MultipleBets getMultipleBet(int id) {
		dbManager.open(false);
		MultipleBets b = dbManager.getMultipleBet(id);
		dbManager.close();
		return b;
	}
	
	public boolean cancelLimit(Bezeroa b) {
		dbManager.open(false);
		boolean res = dbManager.cancelLimit(b);
		dbManager.close();
		return res;
	}
	
}