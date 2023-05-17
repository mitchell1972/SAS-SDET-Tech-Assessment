package sas.sdet.techtest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_ordenes")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ord_id")
	private Long id;

	@OneToOne
	@JoinColumn(name = "ord_user")
	private User user;

	@OneToOne
	@JoinColumn(name = "ord_item")
	private Tournament item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public Tournament getItem() {
		return item;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setItem(Tournament item) {
		this.item = item;
	}
}