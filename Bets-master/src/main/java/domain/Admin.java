package domain;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Admin extends User{

	/**
	 * Admin klasearen eraikitzailea
	 * @param userName, Admin-aren userName bazala ezarriko da.
	 * @param password, Admin-aren password bezala ezarriko da.
	 */
	public Admin(String userName, String password) {
		super(userName, password);
	}

}
