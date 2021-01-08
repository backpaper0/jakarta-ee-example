package com.example.jpa.misc;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.hamcrest.Description;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.jpa.JpaProviders;

@RunWith(Arquillian.class)
public class JpaMiscTest {

	@Inject
	private EntityManager em;
	@Inject
	private UserTransaction utx;

	@Before
	public void init() throws Exception {
		utx.begin();

		em.merge(new Foo(1));

		em.merge(new Bar(1, "aaa"));
		em.merge(new Bar(2, "aaa"));
		em.merge(new Bar(3, "aaa"));
		em.merge(new Bar(4, "bbb"));
		em.merge(new Bar(5, "bbb"));
		em.merge(new Bar(6, "ccc"));

		utx.commit();
	}

	@Test
	public void misc() throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> q = cb.createTupleQuery();
		Root<Foo> foo = q.from(Foo.class);

		q.multiselect(
				// リテラル
				cb.literal("hello").alias("lt"),
				// 日時関数
				cb.currentDate().alias("dt"),
				cb.currentTime().alias("tm"),
				cb.currentTimestamp().alias("ts"),
				// 汎用的な関数呼び出し
				cb.function("ascii", int.class, cb.literal("A")).alias("fn"),
				// case when
				cb.selectCase(foo.get("id")).when(1, "one").otherwise("other").alias("case1"),
				cb.selectCase().when(cb.equal(foo.get("id"), 1), "one").otherwise("other")
						.alias("case2"));

		Tuple result = em.createQuery(q).getSingleResult();
		for (TupleElement<?> tupleElement : result.getElements()) {
			System.out.printf("%s = %s%n", tupleElement.getAlias(), result.get(tupleElement));
		}
	}

	@Test
	public void aggregate() throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> q = cb.createTupleQuery();

		Root<Bar> bar = q.from(Bar.class);
		Path<Integer> id = bar.get("id");
		Path<String> tag = bar.get("tag");

		q.multiselect(tag.alias("tag"),
				cb.sum(id).alias("sum"),
				cb.avg(id).alias("avg"),
				cb.count(id).alias("count"),
				cb.max(id).alias("max"),
				cb.min(id).alias("min"))
				.groupBy(bar.get("tag"))
				.orderBy(cb.asc(bar.get("tag")));

		List<Tuple> result = em.createQuery(q).getResultList();

		assertEquals(3, result.size());
		MatcherAssert.assertThat(result.get(0), new AggregateMatcher("aaa", 6, 2.0, 3, 3, 1));
		MatcherAssert.assertThat(result.get(1), new AggregateMatcher("bbb", 9, 4.5, 2, 5, 4));
		MatcherAssert.assertThat(result.get(2), new AggregateMatcher("ccc", 6, 6.0, 1, 6, 6));
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClasses(JpaProviders.class, Foo.class, Bar.class)
				.addAsResource("META-INF/persistence.xml");
	}

	private static class AggregateMatcher extends TypeSafeMatcher<Tuple> {

		private final String tag;
		private final int sum;
		private final double avg;
		private final int count;
		private final int max;
		private final int min;

		public AggregateMatcher(String tag, int sum, double avg, int count, int max, int min) {
			this.tag = tag;
			this.sum = sum;
			this.avg = avg;
			this.count = count;
			this.max = max;
			this.min = min;
		}

		@Override
		public void describeTo(Description description) {
			List<String> values = List.of(
					String.format("%s = %s", "tag", tag),
					String.format("%s = %s", "sum", sum),
					String.format("%s = %s", "avg", avg),
					String.format("%s = %s", "count", count),
					String.format("%s = %s", "max", max),
					String.format("%s = %s", "min", min));
			description.appendValueList("", ", ", "", values);
		}

		@Override
		protected void describeMismatchSafely(Tuple item, Description mismatchDescription) {
			String text = item.getElements().stream()
					.map(a -> String.format("%s = %s", a.getAlias(), item.get(a)))
					.collect(Collectors.joining(", "));
			mismatchDescription.appendText(text);
		}

		@Override
		protected boolean matchesSafely(Tuple item) {
			return tag.equals(item.get("tag"))
					&& sum == ((Number) item.get("sum")).intValue()
					&& Double.compare(avg, ((Number) item.get("avg")).doubleValue()) == 0
					&& count == ((Number) item.get("count")).intValue()
					&& max == ((Number) item.get("max")).intValue()
					&& min == ((Number) item.get("min")).intValue();
		}
	}
}
