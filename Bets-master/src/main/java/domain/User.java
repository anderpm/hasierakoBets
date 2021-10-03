package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class User {
	
	@Id
	private String userName;
	private String password;
	
	/**
	 * User klasearen eraikitzailea.
	 * @param userName, User-aren userName bezala ezarriko da.
	 * @param password, User-aren password bezala ezarriko da.
	 */
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * User-aren userName atributuaren getter-a.
	 * @return User-aren userName.
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * User-aren userName atributuaren setter-a.
	 * @param userName, User-aren userName bezala ezarriko da.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * User-aren password atrbutuaren getter-a.
	 * @return User-aren password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * User-aren password atributuaren setter-a.
	 * @param password, User-aren password bezala ezarriko da.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}