package com.example.jpa.o2m;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.example.HashEqualsToString;

@Entity
public class Bar implements Serializable {

	@Id
	private Integer id;

	public Bar() {
	}

	public Bar(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return HashEqualsToString.hashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return HashEqualsToString.equals(this, obj);
	}

	@Override
	public String toString() {
		return HashEqualsToString.toString(this);
	}
}
