package com.example.jpa.misc;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Foo {

	@Id
	private Integer id;
	private String tag;

	public Foo() {
	}

	public Foo(Integer id, String tag) {
		this.id = id;
		this.tag = tag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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
		return Objects.equals(id, other.id) && Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return "Foo [id=" + id + ", tag=" + tag + "]";
	}
}
