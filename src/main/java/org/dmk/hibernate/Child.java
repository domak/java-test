package org.dmk.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@TableGenerator(name = "ChildGen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "Child", allocationSize = 1)
@Entity
public class Child {

	private int id;
	private int internalCode;
	private String comment;

	public Child() {
		// void
	}

	public Child(String comment) {
		super();
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ChildGen")
	public int getId() {
		return id;
	}

	public int getInternalCode() {
		return internalCode;
	}

	public String getComment() {
		return comment;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInternalCode(int internalCode) {
		this.internalCode = internalCode;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return id + " - " + comment;
	}

}
