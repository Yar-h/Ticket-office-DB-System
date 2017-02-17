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
@Table(name="trip")

public class Trip {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column
	private Integer tripID;

	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "station_startID", referencedColumnName="stationID")
	private Station station_start;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "station_endID", referencedColumnName="stationID")
	private Station station_end;
	
	@Column
	private Timestamp start_date;
	@Column
	private Timestamp end_date;
	
	@OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "transID")
	private Transport transport;
	
	public Integer getTripID() {
		return tripID;
	}
	public void setTripID(Integer tripID) {
		this.tripID = tripID;
	}
	public Station getStation_start() {
		return station_start;
	}
	public void setStation_start(Station station_start) {
		this.station_start = station_start;
	}
	public Station getStation_end() {
		return station_end;
	}
	public void setStation_end(Station station_end) {
		this.station_end = station_end;
	}
	public Timestamp getStart_date() {
		return start_date;
	}
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}
	public Timestamp getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Timestamp end_date) {
		this.end_date = end_date;
	}
	public Transport getTransport() {
		return transport;
	}
	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	@Override
	public String toString() {
		return ""+this.tripID;
	}
	
	
}
