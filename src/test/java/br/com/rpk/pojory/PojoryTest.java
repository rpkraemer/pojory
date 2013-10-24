package br.com.rpk.pojory;

import static br.com.rpk.pojory.TraitHelperMethods.random;
import static br.com.rpk.pojory.TraitHelperMethods.range;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

public class PojoryTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRequestInvalidTrait() {
		Pojory.pojo(Person.class).trait("default").getOne();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRequestInvalidTrait2() {
		Pojory.define(Person.class).trait("default", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setId(random(range(1, 1000)));
				obj.setName(random("Default 1", "Default 2"));
				obj.setAge(random(range(18, 100)).shortValue());
			}
		});
		Pojory.pojo(Person.class).trait("fooBar").getOne();
	}

	@Test
	public void shouldReturnOneObjectForAGivenValidTrait() {
		Pojory.define(Person.class).trait("default", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setId(random(range(1, 1000)));
				obj.setName(random("Default 1", "Default 2"));
				obj.setAge(random(range(18, 100)).shortValue());
			}
		});
		Person p = Pojory.pojo(Person.class).trait("default").getOne();

		Assert.assertNotNull(p);
		Assert.assertTrue(p.getId() >= 1 && p.getId() <= 1000);
		Assert.assertTrue(p.getName().startsWith("Default"));
		Assert.assertTrue(p.getAge() >= 18 && p.getAge() <= 100);
	}

	@Test
	public void shouldReturnAListOfObjectsForAGivenValidTrait() {
		Pojory.define(Person.class).trait("default", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setId(random(range(1, 1000)));
				obj.setName(random("Default 1", "Default 2"));
				obj.setAge(random(range(18, 100)).shortValue());
			}
		});
		List<Person> list = Pojory.pojo(Person.class).trait("default")
				.getList(5);

		Assert.assertNotNull(list);
		Assert.assertEquals(5, list.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRequestAListWithZeroSize() {
		Pojory.pojo(Person.class).trait("default").getList(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenRequestAListWithNegativeSize() {
		Pojory.pojo(Person.class).trait("default").getList(-1);
	}

	@Test
	public void shouldCombineValidTraitsOnObjectInstance() {
		Pojory.define(Person.class).trait("default", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setId(random(range(1, 1000)));
				obj.setName(random("Default 1", "Default 2"));
				obj.setAge((short) 15);
			}
		});

		Pojory.define(Person.class).trait("major", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setAge(random(range(18, 100)).shortValue());
			}
		});

		Person p = Pojory.pojo(Person.class).traits("default", "major")
				.getOne();

		Assert.assertNotNull(p);
		Assert.assertTrue(p.getId() >= 1 && p.getId() <= 1000);
		Assert.assertTrue(p.getName().startsWith("Default"));
		Assert.assertTrue(p.getAge() >= 18);
	}

	@Test
	public void shouldCombineValidTraitsOnListOfObjectInstances() {
		Pojory.define(Person.class).trait("default", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setId(random(range(1, 1000)));
				obj.setName(random("Default 1", "Default 2"));
				obj.setAge((short) 15);
			}
		});

		Pojory.define(Person.class).trait("major", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setAge(random(range(18, 100)).shortValue());
			}
		});

		List<Person> list = Pojory.pojo(Person.class)
				.traits("default", "major").getList(5);

		Assert.assertNotNull(list);
		Assert.assertEquals(5, list.size());
		for (Person p : list)
			Assert.assertTrue(p.getAge() >= 18);
	}

	@Test
	public void shouldReturnOneObjectComposedOfOtherClasses() {
		Pojory.define(Person.class).trait("default", new Trait<Person>() {
			public void definitions(Person obj) {
				obj.setId(random(range(1, 1000)));
				obj.setName(random("Default 1", "Default 2"));
				obj.setAge((short) 15);
				obj.setAddress(Pojory.pojo(Address.class).trait("default")
						.getOne());
			}
		});
		Pojory.define(Address.class).trait("default", new Trait<Address>() {
			public void definitions(Address obj) {
				obj.setId(random(range(1, 1000)));
				obj.setStreet(random("Rua Fagundes dos Reis", "Avenida Brasil"));
				obj.setNeighborhood("Centro");
				obj.setCountry("Brasil");
				obj.setNumber(random(range(100, 1400)));
			}
		});

		Person p = Pojory.pojo(Person.class).trait("default").getOne();
		Assert.assertNotNull(p.getAddress());
		Assert.assertEquals("Brasil", p.getAddress().getCountry());
		Assert.assertTrue(p.getAddress().getNumber() >= 100
				&& p.getAddress().getNumber() <= 1400);
	}

	@Test
	public void shouldReturnOneObjectForAGivenValidFactory() {
		Pojory.define(CPF.class).factory("valid", new Factory<CPF>() {
			public CPF createInstance() {
				CPF cpf = new CPF("39295870417");
				return cpf;
			}
		});
		CPF cpf = Pojory.pojo(CPF.class).factory("valid").getOne();
		Assert.assertEquals("39295870417", cpf.getCpf());
	}

}