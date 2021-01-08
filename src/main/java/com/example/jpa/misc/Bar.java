package com.example.jpa.misc;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bar {

	@Id
	private Integer id;
	private String tag;

	public Bar() {
	}

	public Bar(Integer id, String tag) {
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
		return Objects.hash(id, tag);
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
		Bar other = (Bar) obj;
		return Objects.equals(id, other.id) && Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return "Bar [id=" + id + ", tag=" + tag + "]";
	}
}
