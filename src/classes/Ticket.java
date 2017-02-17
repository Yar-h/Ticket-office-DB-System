package classes;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column
	private Integer ticketId;
	@Column
	private String category;
	@Column
	private Long price;
	
	@Column
	private Timestamp sell_date;
	@Column
	private Timestamp reservation_date;
	
	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "tripID")
	private Trip trip;
	
	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "passengerID")
	private Passenger passenger;
	
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Timestamp getSell_date() {
		return sell_date;
	}
	public void setSell_date(Timestamp sell_date) {
		this.sell_date = sell_date;
	}
	public Timestamp getReservation_date() {
		return reservation_date;
	}
	public void setReservation_date(Timestamp reservation_date) {
		this.reservation_date = reservation_date;
	}
	public Trip getTrip_num() {
		return trip;
	}
	public void setTrip_num(Trip trip_n) {
		this.trip = trip_n;
	}
	public Passenger getPass_id() {
		return passenger;
	}
	public void setPass_id(Passenger passenger) {
		this.passenger = passenger;
	}
	
	@Override
	public String toString() {
		return ""+this.ticketId;
	}
	
	

}
