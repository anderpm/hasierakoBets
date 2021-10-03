package domain;

import java.util.Date;
import java.util.Vector;
import java.text.SimpleDateFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Bezeroa extends User{

	private double cash;
	private String copyID;
	private float copyPercentage;
	private Date limitDate;
	private double maxLimit = 0.0;
	private double actualLimit = 0.0;
	private boolean limit = false;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Transaction> mugimenduak = new Vector<Transaction>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Copy> kopiatuNi = new Vector<Copy>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Copy> kopiatzenDut = new Vector<Copy>();

	
	/**
	 * Bezeroaren cash atributuaren getter-a.
	 * @return bezeroak duen dirua.
	 */
	public double getCash() {
		return cash;
	}

	/**
	 * Bezeroaren cash atributuaren setter-a.
	 * @param cash, Bezeroaren cash bezala ezarriko da.
	 */
	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getCopyID() {
		return copyID;
	}

	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	public float getCopyPercentage() {
		return copyPercentage;
	}

	public void setCopyPercentage(float copyPercentage) {
		this.copyPercentage = copyPercentage;
	}

	/**
	 * Bezeroa klasearen eraikitzailea.
	 * Hasierako dirua 0.0 izangoa da.
	 * @param userName, Bezeroaren userName bezala ezarriko da.
	 * @param password, Bezeroaren password bezala ezarriko da.
	 */
	public Bezeroa(String userName, String password) {
		super(userName, password);
		this.cash = 0.0;
	}
	
	public void addCash(double amount) {
		this.cash = this.cash + amount;
	}
	
	public void addTransactionInOut(int operation, double amount) {
		TCashInOut t = new TCashInOut(operation, amount);
		mugimenduak.add(t);
	}
	
	public void addTransaction(Transaction t) {
		this.mugimenduak.add(t);
	}
	
	public Vector<Transaction> getMugimenduak(){
		return this.mugimenduak;
	}
	
	public void addKopiatuNi(Copy c) {
		this.kopiatuNi.add(c);
	}

	public Vector<Copy> getKopiatuNi() {
		return kopiatuNi;
	}

	public void setKopiatuNi(Vector<Copy> kopiatuNi) {
		this.kopiatuNi = kopiatuNi;
	}
	
	public void addKopiatzenDut(Copy c) {
		this.kopiatzenDut.add(c);
	}

	public Vector<Copy> getKopiatzenDut() {
		return kopiatzenDut;
	}

	public void setKopiatzenDut(Vector<Copy> kopiatzenDut) {
		this.kopiatzenDut = kopiatzenDut;
	}

	public double getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(double maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Date getLimitDate() {
		return this.limitDate;
	}
	
	public boolean calculateDate() {
		Date date = new Date();
		long difference_In_Time = date.getTime() - this.limitDate.getTime();
		long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		System.out.println(difference_In_Days);
		if(difference_In_Days >= 30) {
			this.limitDate = new Date();
			return true;
		}
		else {
			return false;
		}
		
	}

	public void setLimitDate() {
		this.limitDate = new Date();
	}

	public double getActualLimit() {
		
		return actualLimit;
	}

	public void setActualLimit(double actualLimit) {
		this.actualLimit = actualLimit;
	}

	public boolean isLimit() {
		return limit;
	}

	public void setLimit(boolean limit) {
		this.limit = limit;
	}
	
}