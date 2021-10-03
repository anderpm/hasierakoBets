package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Pronostic {
	@Id @GeneratedValue
	private int id;
	private double fee;
	private String description;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<MultipleBets> bets = new Vector<MultipleBets>();
	
	public Pronostic(String description, double fee) {
		this.fee = fee;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Vector<MultipleBets> getBets() {
		return bets;
	}

	public void setBets(Vector<MultipleBets> bets) {
		this.bets = bets;
	}

	public void addBet(MultipleBets bet) {
		this.bets.add(bet);
	}
	
	public void deleteNull() {
		for(int i = 0; i <= this.bets.size(); i++) {
			for(MultipleBets bi:this.bets) {
				
				if(bi.getUserName()==null) {
					this.bets.remove(bi);
				}
				if(this.bets.size()==0) {
					break;
				}
			}
		}
	}
	public void deleteMB(MultipleBets MB) {
		this.bets.remove(MB);
	}
	
}
