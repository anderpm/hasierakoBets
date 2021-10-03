package domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Langilea extends User{

	/**
	 * Langilea klasearen eraikitzailea.
	 * @param userName, Langilearen userName bezala ezarriko da.
	 * @param password, Langilearen password bezala ezarriko da.
	 */
	public Langilea(String userName, String password) {
		super(userName, password);
	}

}