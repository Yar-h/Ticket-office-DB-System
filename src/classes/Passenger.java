package classes;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "passenger")

public class Passenger {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column
	private Integer passengerID;
	@Column
	private String FIO;
	@Column
	private String address;
	@Column
	private Timestamp birth_date;
	
	public Integer getPassengerID() {
		return passengerID;
	}
	public void setPassengerID(Integer passengerID) {
		this.passengerID = passengerID;
	}
	public String getFIO() {
		return FIO;
	}
	public void setFIO(String fIO) {
		FIO = fIO;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Timestamp getBirth_date() {
		if (birth_date == null)
			birth_date = new Timestamp(new Date().getTime());
		return birth_date;
	}
	public void setBirth_date(Timestamp birth_date) {
		this.birth_date = birth_date;
	}
	
	@Override
	public String toString() {
		return this.FIO;
	}
	

}
