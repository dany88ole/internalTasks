package com.internal.tasks.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "OFFICE")
@XmlRootElement
public class Office {

	@Id
	@GeneratedValue
	private Long name_id;

	private String name;
	private String time;
	private String operation;

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "name_id")
	public Long getName_id() {
		return name_id;
	}

	public void setName_id(Long name_id) {
		this.name_id = name_id;
	}

	@Column(name = "time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Column(name = "operation")
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "name_Id: "+name_id+",name: " + this.name + ",time: " + this.time +",operation: " + this.operation;

	}
}
