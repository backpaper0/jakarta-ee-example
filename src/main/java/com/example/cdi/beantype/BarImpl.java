package com.example.cdi.beantype;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Typed;

@Dependent
@Typed(Bar.class)
public class BarImpl implements Bar {
}
