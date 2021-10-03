package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bet {

	@Id @GeneratedValue
	private int id;
	private Question q;
	private Pronostic pronostic;
	
	public Bet(Pronostic p, Question q) {
		this.pronostic = p;
		this.q = q;
	}
	
	public Pronostic getPronostic() {
		return this.pronostic;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Question getQuestion() {
		return this.q;
	}
	

}