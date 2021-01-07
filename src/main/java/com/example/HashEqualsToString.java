package com.example;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class HashEqualsToString {

	private HashEqualsToString() {
	}

	public static int hashCode(Object self) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(self.getClass(), Object.class);

			PropertyDescriptor[] pds = Arrays
					.stream(beanInfo.getPropertyDescriptors())
					.sorted(Comparator.comparing(PropertyDescriptor::getName))
					.toArray(PropertyDescriptor[]::new);

			List<Object> values = new ArrayList<>(pds.length);
			for (PropertyDescriptor pd : pds) {
				Method getter = pd.getReadMethod();
				Object value = getter.invoke(self);
				values.add(value);
			}

			return Objects.hash(values.toArray());
		} catch (IntrospectionException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean equals(Object self, Object other) {
		Objects.requireNonNull(self);
		if (self == other) {
			return true;
		} else if (other == null) {
			return false;
		} else if (self.getClass() != other.getClass()) {
			return false;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(self.getClass(), Object.class);

			for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				Method getter = pd.getReadMethod();
				Object selfValue = getter.invoke(self);
				Object otherValue = getter.invoke(other);
				if (Objects.equals(selfValue, otherValue) == false) {
					return false;
				}
			}

			return true;
		} catch (IntrospectionException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toString(Object self) {
		StringBuilder buf = new StringBuilder();
		buf.append(self.getClass().getSimpleName());
		buf.append("[");
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(self.getClass(), Object.class);

			Iterator<PropertyDescriptor> it = Arrays.stream(beanInfo.getPropertyDescriptors())
					.iterator();
			while (it.hasNext()) {
				PropertyDescriptor pd = it.next();
				Method getter = pd.getReadMethod();
				Object value = getter.invoke(self);
				buf.append(pd.getName()).append("=").append(value);
				if (it.hasNext()) {
					buf.append(", ");
				}
			}

			buf.append("]");
			return buf.toString();
		} catch (IntrospectionException | ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
}
