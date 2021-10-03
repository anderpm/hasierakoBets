package businessLogic;

import java.util.Vector;
import java.util.Date;





//import domain.Booking;
import domain.Question;
import domain.TMBet;
import domain.Transaction;
import domain.User;
import domain.Bet;
import domain.Bezeroa;
import domain.Event;
import domain.MultipleBets;
import domain.Pronostic;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	/**
	 * This method calls the data access to return User of the given userName and password
	 * @param userName Users name
	 * @param password Users password
	 * @return User if the user exists or null if it doesn't
	 */
	@WebMethod public User login(String userName, String password);
	
	/**
	 * This method calls the data access to create a new User by the given name and password
	 * @param userName the name of the new user
	 * @param password the password of the new user
	 * @return true if it is correctly registered, false if it is already registered
	 */
	@WebMethod public boolean register(String userName, String password);
	
	/**
	 * This method calls the data access to create a new event with the given description and date
	 * @param description the description of the new event
	 * @param eventDate the date of the new event
	 * @return true if the even is successfully created, false if not.
	 */
	@WebMethod public boolean createEvent(String description, Date eventDate);
	
	@WebMethod public boolean diruaSartu(String userName, double zenbat);

	@WebMethod public double getCashByUserName(String userName);
	
	@WebMethod public boolean addPronostic(Question q, String desc, double fee);
	
	
	@WebMethod public int addBet(Pronostic p, double zenbat, Bezeroa bezero, Question q);
	
	@WebMethod public int addBetMB(MultipleBets mb, Vector<Pronostic> p, Question q);
	
	@WebMethod public Bezeroa getUserByUserName(String userName);

	@WebMethod public boolean deleteEvent(Event evi);
	
	@WebMethod public boolean setResult(Pronostic p);

	@WebMethod public void addTBet(Bezeroa bezero, Event event, Question question, Pronostic pronostic, double betAmount, Vector<Bet>b);
	
	@WebMethod public void addTCashInOut(Bezeroa bezero, int operation, double cash);
	
	@WebMethod public boolean diruaAtera(String userName, double zenbat);

	@WebMethod public Vector getBezeroak();
	
	@WebMethod public TMBet getTBetByID(int id);
	
	@WebMethod public boolean addKopiatuNi(String bezeroa, Double c, Bezeroa b);
	
	@WebMethod public boolean setLimit(double limit, Bezeroa b);
	
	@WebMethod public boolean cancelLimit(Bezeroa b);



}