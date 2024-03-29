package com.example.cdi.stereotype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Stereotype;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Stereotype
@Dependent
public @interface MyStereotype {
}
