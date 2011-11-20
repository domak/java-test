package org.domak.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@javax.persistence.SequenceGenerator(name = "DETAIL_SEQ", sequenceName = "detail_sequence")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Detail {
	private int id;
	private String name;
	private Master master;

	public Detail() {
		super();
	}

	public Detail(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DETAIL_SEQ")
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

	@ManyToOne
	@JoinColumn(name = "master_fk")
	public Master getMaster() {
		return master;
	}

	public void setMaster(Master master) {
		this.master = master;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "#" + id + " - " + name;
	}

}
