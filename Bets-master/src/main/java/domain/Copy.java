package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Copy {

	@Id @GeneratedValue
	private int id;
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Bezeroa kopiatuDit;
	private double percentage;
	
	
	public Copy(double percentage, Bezeroa noriKopiatu) {
		this.kopiatuDit = noriKopiatu;
		this.percentage = percentage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Bezeroa getkopiatuDit() {
		return kopiatuDit;
	}
	
	public void setkopiatuDit(Bezeroa noriKopiatu) {
		this.kopiatuDit = noriKopiatu;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
}