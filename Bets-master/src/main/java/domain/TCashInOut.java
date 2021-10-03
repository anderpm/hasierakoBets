package domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class TCashInOut extends Transaction {

	private int operation;
	private double cashAmount;
	
	
	/**
	 * TCashInOut klasearen eraikitzailea.
	 * @param transactionID, TCashInOut-ren transactionID bezala ezarriko da.
	 * @param operation, operation-ren transactionID bezala ezarriko da.
	 * @param cashAmount, cashAmount-ren transactionID bezala ezarriko da.
	 */
	public TCashInOut(int operation, double cashAmount) {
		super();
		this.operation = operation;
		this.cashAmount = cashAmount;
	}
	
	/**
	 * TCashInOut-ren operation atributuaren getter-a.
	 * @return TCashInOut-ren operation. True "Sartu" bada, false "Atera bada.
	 */
	public int isOperation() {
		return operation;
	}
	
	/**
	 * TCashInOut-ren operation atributuaren setter-a.
	 * @param operation, true "Sartu" bada, false "Atera bada.
	 */
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	/**
	 * TCashInOut-ren cashAmount atributuaren getter-a.
	 * @return TCashInOut-ren cashAmount.
	 */
	public double getCashAmount() {
		return cashAmount;
	}
	
	/**
	 * TCashInOut-ren cashAmount atributuaren setter-a.
	 * @param cashAmount, TcashInOut-ren cashAmount bezala ezarriko da.
	 */
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}
	
}