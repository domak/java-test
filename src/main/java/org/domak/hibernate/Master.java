package org.domak.hibernate;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
@javax.persistence.SequenceGenerator(name = "MASTER_SEQ", sequenceName = "master_sequence")
public class Master {
	private int id;
	private String name;
	private List<Detail> details;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MASTER_SEQ")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "master", cascade = {CascadeType.ALL})
	@JoinColumn(name = "master_fk")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE)
	public List<Detail> getDetails() {
		return details;
	}

	public void setDetails(List<Detail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "#" + id + " - " + name;
	}

}
