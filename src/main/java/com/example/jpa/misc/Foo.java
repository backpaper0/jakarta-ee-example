package com.example.jpa.misc;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Foo {

	@Id
	private Integer id;

	public Foo() {
	}

	public Foo(Integer id) {
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
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		Foo other = (Foo) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Foo [id=" + id + "]";
	}
}
