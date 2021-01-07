package com.example.jpa.o2m;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.HashEqualsToString;

@Entity
public class Foo implements Serializable {

	@Id
	private Integer id;
	@OneToMany
	private List<Bar> bars;

	public Foo() {
	}

	public Foo(Integer id, List<Bar> bars) {
		this.id = id;
		this.bars = bars;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Bar> getBars() {
		return bars;
	}

	public void setBars(List<Bar> bars) {
		this.bars = bars;
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
