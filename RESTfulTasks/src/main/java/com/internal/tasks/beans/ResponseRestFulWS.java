package com.internal.tasks.beans;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "NAMES")
@XmlRootElement
public class ResponseRestFulWS {

	@Id
	@GeneratedValue
	private Long name_id;
	
	private String name;
	private String time;
	
	@Column(name = "name",unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="name_id")
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
	
	@Override
	public String toString() {
		return "name: " + this.name + ",time: " + this.time;
	}
}
