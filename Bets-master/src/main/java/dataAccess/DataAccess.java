package dataAccess;

//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Bet;
import domain.Bezeroa;
import domain.Copy;
import domain.Event;
import domain.MultipleBets;
import domain.Pronostic;
import domain.Question;
import domain.TMBet;
import domain.TCashInOut;
import domain.User;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess(boolean initializeMode)  {
		
		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);
		close();
		
	}

	public DataAccess()  {	
		 new DataAccess(false);
	}
	
	
	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();
		try {

			
		   Calendar today = Calendar.getInstance();
		   
		   int month=today.get(Calendar.MONTH);
		   month+=1;
		   int year=today.get(Calendar.YEAR);
		   if (month==12) { month=0; year+=1;}  
	    
			Event ev1=new Event(1, "Atlético-Athletic", UtilDate.newDate(year,month,17));
			Event ev2=new Event(2, "Eibar-Barcelona", UtilDate.newDate(year,month,17));
			Event ev3=new Event(3, "Getafe-Celta", UtilDate.newDate(year,month,17));
			Event ev4=new Event(4, "Alavés-Deportivo", UtilDate.newDate(year,month,17));
			Event ev5=new Event(5, "Español-Villareal", UtilDate.newDate(year,month,17));
			Event ev6=new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year,month,17));
			Event ev7=new Event(7, "Malaga-Valencia", UtilDate.newDate(year,month,17));
			Event ev8=new Event(8, "Girona-Leganés", UtilDate.newDate(year,month,17));
			Event ev9=new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year,month,17));
			Event ev10=new Event(10, "Betis-Real Madrid", UtilDate.newDate(year,month,17));

			Event ev11=new Event(11, "Atletico-Athletic", UtilDate.newDate(year,month,1));
			Event ev12=new Event(12, "Eibar-Barcelona", UtilDate.newDate(year,month,1));
			Event ev13=new Event(13, "Getafe-Celta", UtilDate.newDate(year,month,1));
			Event ev14=new Event(14, "Alavés-Deportivo", UtilDate.newDate(year,month,1));
			Event ev15=new Event(15, "Español-Villareal", UtilDate.newDate(year,month,1));
			Event ev16=new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year,month,1));
			

			Event ev17=new Event(17, "Málaga-Valencia", UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(18, "Girona-Leganés", UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(20, "Betis-Real Madrid", UtilDate.newDate(year,month+1,28));
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
					
			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);
				
			}
			
			
			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6); 
	
	        
			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);			
			
			Admin admin = new Admin("admin", "admin");
			db.persist(admin);
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		
			Event ev = db.find(Event.class, event.getEventNumber());
			
			if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
			
			db.getTransaction().begin();
			Question q = ev.addQuestion(question, betMinimum);
			//db.persist(q);
			db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
							// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
			db.getTransaction().commit();
			return q;
		
	}
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
	 	 for (Event ev:events){
	 	   System.out.println(ev.toString());		 
		   res.add(ev);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	
		
		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);
				
		
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
	 	 for (Date d:dates){
	 	   System.out.println(d.toString());		 
		   res.add(d);
		  }
	 	return res;
	}

	public void open(boolean initializeMode){
		
		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		
	}
	
	public boolean existQuestion(Event event, String question) {
	System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
	Event ev = db.find(Event.class, event.getEventNumber());
	return ev.DoesQuestionExists(question);
	
	}
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}
	
	public boolean register(String userName, String password) {
		if(db.find(User.class, userName) == null) {
			Bezeroa b = new Bezeroa(userName, password);
			db.getTransaction().begin();
			db.persist(b);
			db.getTransaction().commit();
			return true;
		}
		return false;
	}
	
	public User login(String userName) {
		return db.find(User.class, userName);
	}
	
	public boolean CreateEvent(String description, Date eventDate) {
		try {
			Event newEvent = new Event(description, eventDate);
			db.getTransaction().begin();
			db.persist(newEvent);
			db.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean diruaSartu(String userName, double zenbat) {
		try{
			if(zenbat <= 0) {
				return false;
	
			}
			Bezeroa bezero = db.find(Bezeroa.class, userName);
			db.getTransaction().begin();
			if(!bezero.isLimit()) {
				double actual = bezero.getCash();
				bezero.setCash(actual + zenbat);
				bezero.addTransactionInOut(0, zenbat);
				db.getTransaction().commit();
				System.out.println("amount:"+actual + zenbat);
				return true;
			}
			else if(!bezero.calculateDate() && bezero.isLimit()) {
				boolean res = true;
				double actual = bezero.getCash();
				if((bezero.getActualLimit() + zenbat) > bezero.getMaxLimit()) {
					res = false;
				}
				else {
					bezero.setCash(actual + zenbat);
					bezero.addTransactionInOut(0, zenbat);
					bezero.setActualLimit(bezero.getActualLimit() + actual +zenbat);
				}
				db.getTransaction().commit();
				System.out.println("amount:"+actual + zenbat);
				return res;
			}
			else if(bezero.isLimit()){
				bezero.setActualLimit(0);
				bezero.setLimitDate();
				boolean res = true;
				double actual = bezero.getCash();
				if((bezero.getActualLimit() + zenbat) > bezero.getMaxLimit()) {
					res = false;
				}
				else {
					bezero.setCash(actual + zenbat);
					bezero.addTransactionInOut(0, zenbat);
					bezero.setActualLimit(bezero.getActualLimit() + actual +zenbat);
				}
				db.getTransaction().commit();
				System.out.println("amount:"+actual + zenbat);
				return res;
			}
			else {
				return false;
			}
		}catch(Exception e){
			System.out.println("Error Amount");
			return false;
		}
	}
	
	public boolean diruaAtera(String userName, double zenbat) {
		try{
			Bezeroa bezero = db.find(Bezeroa.class, userName);
			if(bezero.getCash()<zenbat || zenbat <= 0) {
				return false;
			}
			db.getTransaction().begin();
			double actual = bezero.getCash();
			bezero.setCash(actual - zenbat);
			bezero.addTransactionInOut(1, zenbat);
			db.getTransaction().commit();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public double getCashByUserName(String userName) {
		User bezero = db.find(User.class, userName);
		Bezeroa b2 = (Bezeroa)bezero;
		return b2.getCash();
	}
	public Bezeroa getUserByUserName(String userName) {
		Bezeroa bezero = db.find(Bezeroa.class, userName);
		return bezero;
	}
	
	public boolean addPronostic(Question q, String desc, double fee) {
		try {
			
			Question question = db.find(Question.class, q.getQuestionNumber());
			db.getTransaction().begin();
			question.addPronostic(desc, fee);
			db.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean deleteEvent(Event evi) {
		Event ev = db.find(Event.class, evi.getEventNumber());
		Vector<Question> questions = ev.getQuestions();
		
		for (Question qi : questions) {
			Vector<Pronostic> pronostics = qi.getPronostics();
			for(Pronostic pi : pronostics) {
				pi.deleteNull();
				Vector<MultipleBets> bets = pi.getBets();
				for(MultipleBets bi: bets) {
					Bezeroa user = db.find(Bezeroa.class, bi.getUserName());
					if(!bi.getDeleted()) {	
						double amount = bi.getAmount();
						user.addCash(amount);
						user.addTransactionInOut(0, amount);
						bi.setDeleted(true);
					}
				}
				
			}
		}
		db.getTransaction().begin();
		db.remove(ev);
		db.getTransaction().commit();
		

		
		return true;
	}

	
public int addBet(Pronostic p, double zenbat, Bezeroa bezero, Question q) {
		try {
			db.getTransaction().begin();
			MultipleBets mb = new MultipleBets();
			mb.setAmount(zenbat);
			mb.setUserName(bezero.getUserName());
			Bet b = new Bet(p, q);
			
			mb.addBet(b);
			mb.setTotalPronostic(p.getFee());
			p = db.find(Pronostic.class, p.getId());
			p.addBet(mb);
			db.persist(p);
			Bezeroa b1 = db.find(Bezeroa.class, bezero.getUserName());
			b1.setCash(b1.getCash() - zenbat);
			TMBet tb = new TMBet(q, p, zenbat, mb.getBets());
			b1.addTransaction(tb);
			db.persist(b1);
			db.getTransaction().commit();
			
			db.getTransaction().begin();
			for(Copy c:b1.getKopiatuNi()) {
				Bezeroa bc = db.find(Bezeroa.class, c.getkopiatuDit().getUserName());
				if(bc.getCash() > zenbat*c.getPercentage()) {
					bc.setCash(bc.getCash() - zenbat*c.getPercentage());
					db.persist(bc);
					
					mb = new MultipleBets();
					mb.setAmount(zenbat*c.getPercentage());
					mb.setUserName(bc.getUserName());
					b = new Bet(p, q);
					
					mb.addBet(b);
					mb.setTotalPronostic(p.getFee());
					p = db.find(Pronostic.class, p.getId());
					p.addBet(mb);
					tb = new TMBet(q, p, zenbat*c.getPercentage(), mb.getBets());
					bc.addTransaction(tb);
					db.persist(p);
				}
				
				
			}
			db.getTransaction().commit();
			
			return mb.getId();
		}catch(Exception e) {
			System.out.println("error");
			return -1;
		}
	}
	
	public int addBetMB(MultipleBets mb, Vector<Pronostic> pronostics, Question q) {
		try {
			
			db.getTransaction().begin();
			db.persist(mb);
			db.getTransaction().commit();
			db.getTransaction().begin();
			for(Pronostic p:pronostics) {
				Pronostic p1 = db.find(Pronostic.class, p);
				MultipleBets mb1 = db.find(MultipleBets.class, mb.getId());
				p1.addBet(mb1);
				db.persist(p1);
			}
			Bezeroa b = db.find(Bezeroa.class, mb.getUserName());
			b.setCash(b.getCash()-mb.getAmount());
			TMBet tb = new TMBet(null, null, mb.getAmount(), mb.getBets());
			b.addTransaction(tb);
			db.persist(b);
			db.getTransaction().commit();
			for(Copy c:b.getKopiatuNi()) {
				db.getTransaction().begin();
				Bezeroa bc = c.getkopiatuDit();
				if(bc.getCash() > mb.getAmount()*c.getPercentage()) {
					MultipleBets mb2 = new MultipleBets();
					Vector<Bet> bets = new Vector<Bet>(mb.getBets());
					bc.setCash(bc.getCash()-mb.getAmount()*c.getPercentage());
					tb = new TMBet(null, null, mb.getAmount(), mb.getBets());
					bc.addTransaction(tb);
					db.persist(bc);
					mb2.setBets(bets);
					mb2.setAmount(mb.getAmount()*c.getPercentage());
					mb2.setUserName(bc.getUserName());
					for(Pronostic p:pronostics) {
						Pronostic p1 = db.find(Pronostic.class, p);
					
						p1.addBet(mb2);
						db.persist(p1);
					}
				}
				db.getTransaction().commit();
			}
			
			return mb.getId();
		}catch(Exception e) {
			System.out.println(e);
			return -1;
		}
	}
	
	public boolean setResult(Pronostic p) {
		try {
			db.getTransaction().begin();
			Pronostic pronostic = db.find(Pronostic.class, p.getId());
			pronostic.deleteNull();
			
			System.out.println(pronostic.getDescription());
			Vector<MultipleBets> bets = pronostic.getBets();
			for(MultipleBets b:bets) {
				System.out.println(b.getId());
				
				if(b.getBetLength() == 1) {
					b.deleteBet(pronostic);
					//this.diruaSartu(b.getUserName(), (b.getAmount()*b.getTotalPronostic()));
					Bezeroa bezero = db.find(Bezeroa.class, b.getUserName());
					
					double actual = bezero.getCash();
					bezero.setCash(actual + (b.getAmount()*b.getTotalPronostic()));
					System.out.println(bezero.getCash());
					bezero.addTransactionInOut(0, (b.getAmount()*b.getTotalPronostic()));
					
					db.persist(bezero);
					
				}
				else {
					b.deleteBet(pronostic);
				}
				db.persist(b);
				if(b.getBetLength()==0) {
					p.deleteMB(b);
					db.remove(b);
				}
			}
			db.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}

	
	public void addTBet(Bezeroa bezero, Event event, Question question, Pronostic pronostic, double betAmount, Vector<Bet> b) {
		TMBet tb = new TMBet(question, pronostic, betAmount, b);
		Bezeroa b2 = db.find(Bezeroa.class, bezero.getUserName());
		db.getTransaction().begin();
		b2.addTransaction(tb);
		db.getTransaction().commit();
	}

	
	public void addTCashInOut(Bezeroa bezero, int operation, double cash) {
		TCashInOut tbc = new TCashInOut(operation, cash);
		Bezeroa b2 = db.find(Bezeroa.class, bezero);
		db.getTransaction().begin();
		b2.addTransaction(tbc);
		db.getTransaction().commit();
	}
	
	
	public Vector<Bezeroa> getBezeroak() {
		Vector<Bezeroa> res = new Vector<Bezeroa>();	
		TypedQuery<Bezeroa> query = db.createQuery("SELECT b FROM Bezeroa b", Bezeroa.class);
		List<Bezeroa> b = query.getResultList();
	 	 for (Bezeroa i:b){
		   res.add(i);
		  }
	 	return res;
	}
	
	public boolean addKopiatuNi(String u, Double p, Bezeroa b) {
		try {
			System.out.println(u);
			System.out.println(b.getUserName());
			Bezeroa b1 = db.find(Bezeroa.class, u);
			Bezeroa b2 = db.find(Bezeroa.class, b.getUserName());
			Copy c = new Copy(p, b2);
			db.getTransaction().begin();
			b1.addKopiatuNi(c);
			db.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public boolean setLimit(double limit, Bezeroa b) {
		b = db.find(Bezeroa.class, b);
		boolean res;
		db.getTransaction().begin();
		if(b.getLimitDate() == null) {
			b.setLimitDate();
			b.setLimit(true);
			b.setMaxLimit(limit);
			res = true;
		}
		else if(b.calculateDate()) {
			b.setLimitDate();
			b.setLimit(true);
			b.setMaxLimit(limit);
			res = true;
		}
		else {
			res = false;
		}
		db.persist(b);
		db.getTransaction().commit();
		return res;
	}
	
	public TMBet getTBetByID(int id) {
		TMBet t = db.find(TMBet.class, id);
		return t;
	}
	
	public MultipleBets getMultipleBet(int id) {
		return db.find(MultipleBets.class, id);
	}
	
	public boolean cancelLimit(Bezeroa b) {
		try {
			db.getTransaction().begin();
			Bezeroa b1 = db.find(Bezeroa.class, b.getUserName());
			b1.setActualLimit(0);
			b1.setLimit(false);
			db.persist(b1);
			db.getTransaction().commit();
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
//	public void addKopiatzenDut(String bezeroa, Double d, Bezeroa bez) {
//		Bezeroa b = db.find(Bezeroa.class, bezeroa);
//		Bezeroa b2 = db.find(Bezeroa.class, bez.getUserName());
//		Copy c = new Copy(d, b2);
//		Copy c2 = db.find(Copy.class, c.getId());
//		db.getTransaction().begin();
//		b.addKopiatzenDut(c2);
//		db.getTransaction().commit();
//	}
	
}