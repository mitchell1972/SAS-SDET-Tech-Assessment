package sas.sdet.techtest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_items")
public class Tournament {

	@Id
	@Column(name = "item_name")
	private String name;

	@Column(name = "item_prop")
	private int professionalism;

	@Column(name = "item_type")
	private String type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getProfessionalism() {
		return professionalism;
	}

	public void setProfessionalism(int professionalism) {
		this.professionalism = professionalism;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
