package com.example.jpa.o2m;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Foo.class)
public class Foo_ {

	public static SingularAttribute<Foo, Integer> id;
	public static ListAttribute<Foo, Bar> bars;
}
