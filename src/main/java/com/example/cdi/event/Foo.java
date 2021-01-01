package com.example.cdi.event;

import java.util.Objects;

public class Foo {

	private final int value;

	public Foo(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
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
		return value == other.value;
	}

	@Override
	public String toString() {
		return "Foo(" + value + ")";
	}
}
