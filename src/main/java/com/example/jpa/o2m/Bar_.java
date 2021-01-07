package com.example.jpa.o2m;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Bar.class)
public class Bar_ {

	public static SingularAttribute<Bar, Integer> id;
}
