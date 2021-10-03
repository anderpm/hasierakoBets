package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Transaction {

	@Id @GeneratedValue
	private Integer transactionID;
	
	/**
	 * Transaction klasearen eraikitzailea.
	 * @param transactionID, Transaction-aren trasactionID bezala ezarriko da.
	 */
	public Transaction() {
	
	}

	/**
	 * Transaction baten transactionID atributuaren getter-a.
	 * @return Transaction-aren transactionID.
	 */
	public Integer getTransactionID() {
		return transactionID;
	}

	/**
	 * Transaction baten transactionID atributuaren setter-a.
	 * @param transactionID, Transaction-aren transactionID bezala ezarriko da.
	 */
	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}
	
}