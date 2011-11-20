package org.domak.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@TableGenerator(name = "ParentGen", table = "ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "Parent", allocationSize = 1)
@Entity
public class Parent {

	private int id;
	private int internalCode;
	private String comment;

	public Parent() {
		// void
	}

	public Parent(String comment) {
		super();
		this.comment = comment;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ParentGen")
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
