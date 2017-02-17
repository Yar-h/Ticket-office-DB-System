package classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "transport")
public class Transport {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column
	private Integer transID;
	@Column
	private String type;
	@Column
	private Long seat_cnt;
	
	public Integer getTransID() {
		return transID;
	}
	public void setTransID(Integer transID) {
		this.transID = transID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getSeat_cnt() {
		return seat_cnt;
	}
	public void setSeat_cnt(Long seat_cnt) {
		this.seat_cnt = seat_cnt;
	}
	
	@Override
	public String toString() {
		return this.type;
	}

	
}
