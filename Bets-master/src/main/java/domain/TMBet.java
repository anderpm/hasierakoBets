package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class TMBet extends Transaction {

	private Question question;
	private Pronostic pronostic;
	private int howManyBets;
	private double betAmount;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Bet> bets = new Vector<Bet>();
	

	/**
	 * Tbet klasearen eraikitzailea.
	 * @param transactionID, TBet-ren transactionID bezala ezarriko da.
	 * @param questionm TBet-ren question bezala ezarriko da.
	 * @param betAmount TBet-ren betAmount bezala ezarriko da.
	 */
	public TMBet( Question question, Pronostic pronostic, double betAmount, Vector<Bet> b) {
		super();
		this.question = question;
		this.pronostic = pronostic;
		this.betAmount = betAmount;
		this.bets = b;
		this.howManyBets = b.size();
	}

	/**
	 * TBet-ren question atributuaren getter-a.
	 * @return Tbet-ren question.
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * TBet-ren question atributuaren setter-a.
	 * @param question, TBet-ren question bezala ezarriko da.
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * TBet-ren betAmount atributuaren getter-a.
	 * @return TBet-ren betAmount.
	 */
	public double getBetAmount() {
		return betAmount;
	}

	/**
	 * TBet-ren betAmount atributuaren setter-a.
	 * @param betAmount, TBet-ren betAmount bezala ezarriko da.
	 */
	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}


	public Pronostic getPronostic() {
		return pronostic;
	}

	public void setPronostic(Pronostic pronostic) {
		this.pronostic = pronostic;
	}
	
	public int isSingleBet() {
		if(this.howManyBets == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setHowManyBets(int howManyBets) {
		this.howManyBets = howManyBets;
	}
	
	public int getHowManyBets() {
		return this.howManyBets;
	}
	
	public Vector<Bet> getBets() {
		return bets;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}
	
}