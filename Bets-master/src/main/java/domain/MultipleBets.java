package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MultipleBets {
	
	@Id @GeneratedValue
	private int id;
	private double amount;
	private String userName;
	private String copyID;
	private double totalPronostic;
	private boolean deleted = false;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Vector<Bet> bets = new Vector<Bet>();
	
	
	public MultipleBets() {
		
	}
	public MultipleBets(double a, String s, Bet b, double p) {
		this.amount = a;
		this.userName = s;
		this.totalPronostic = p;
		this.bets.add(b);
	}
	
	public void updateMB(double a, String s, Bet b, double p) {
		this.amount = a;
		this.userName = s;
		this.totalPronostic = p;
		this.bets.add(b);
	}
	
	public double getTotalPronostic() {
		return totalPronostic;
	}

	public void setTotalPronostic(double d) {
		this.totalPronostic = d;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCopyID() {
		return copyID;
	}

	public void setCopyID(String copyID) {
		this.copyID = copyID;
	}

	public int getId() {
		return id;
	}
	
	public void addBet(Bet e) {
		this.bets.add(e);
	}
	
	public Vector<Bet> getBets(){
		return this.bets;
	}
	
	public void setBets(Vector<Bet> b){
		this.bets = b;
	}
	
	
	public void updateTPronostic(float p) {
		this.totalPronostic = totalPronostic*p;
	}
	
	public int getBetLength() {
		return this.bets.size();
	}
	
	public void deleteBet(Pronostic p) {
		int pos = 0;
		for(Bet b:this.bets) {
			if(b.getPronostic().getId() == p.getId()) {
				break;
			}
			pos++;
		}
		this.bets.remove(pos);
	}
	
	public void setDeleted(boolean del) {
		this.deleted = del;
	}
	
	public boolean getDeleted() {
		return this.deleted;
	}
	
	public int getHowManyBets() {
		return this.bets.size();
	}
	
}
