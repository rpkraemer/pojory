package br.com.rpk.pojory;

import static br.com.rpk.pojory.Pojory.pojory;
import static br.com.rpk.pojory.TraitHelperMethods.random;
import static br.com.rpk.pojory.TraitHelperMethods.range;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

public class PojoryTest {

	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenNoTraitsAreDefined() {
		Pojory.pojory(User.class).getOne();
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenNoTraitsAreDefined2() {
		pojory(User.class).getList(3);
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenDefaultTraitIsNotDefined() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		@SuppressWarnings("unused")
		User defaultUser = pojory(User.class).getOne();
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenDefaultTraitIsNotDefined2() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		@SuppressWarnings("unused")
		List<User> defaultUsers = pojory(User.class).getList(5);
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenDefaultTraitIsNotDefined3() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		@SuppressWarnings("unused")
		User defaultUser = pojory(User.class).trait("default").getOne();
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenDefaultTraitIsNotDefined4() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		@SuppressWarnings("unused")
		List<User> defaultUsers = pojory(User.class).trait("default").getList(5);
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenGivenTraitIsNotDefined() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		@SuppressWarnings("unused")
		User defaultUser = pojory(User.class).trait("inexistentTrait").getOne();
	}
	
	@Test (expected = IllegalStateException.class)
	public void shouldRaiseErrorWhenGivenTraitIsNotDefined2() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		@SuppressWarnings("unused")
		List<User> defaultUsers = pojory(User.class).trait("inexistentTrait").getList(3);
	}
	
	/*
	 * Using PojoryFactory.getOne()
	 */
	@Test
	public void shouldBuildDefaultTraitForUser() {
		Pojory.define(User.class, new Trait("Default") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("DefaultUser");
				user.setAdmin(false);
				user.setAddress("Rua abc, 123, Dreamland");
			}
		});
		User defaultUser = pojory(User.class).getOne();
		assertEquals("DefaultUser", defaultUser.getName());
		assertEquals(false, defaultUser.isAdmin());
		assertEquals("Rua abc, 123, Dreamland", defaultUser.getAddress());
	}
	
	/*
	 * Using PojoryFactory.trait(String).getOne()
	 */
	@Test
	public void shouldBuildDefaultTraitForUser2() {
		Pojory.define(User.class, new Trait("default") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("DefaultUser");
				user.setAdmin(false);
				user.setAddress("Rua abc, 123, Dreamland");
			}
		});
		User defaultUser = pojory(User.class).trait("Default").getOne();
		assertEquals("DefaultUser", defaultUser.getName());
		assertEquals(false, defaultUser.isAdmin());
		assertEquals("Rua abc, 123, Dreamland", defaultUser.getAddress());
	}
	
	@Test
	public void shouldBuildDefaultTraitForUser3() {
		Pojory.define(User.class, new Trait("Default") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("DefaultUser");
				user.setAdmin(false);
				user.setAddress("Rua abc, 123, Dreamland");
			}
		});
		int size = 5;
		List<User> defaultUsers = pojory(User.class).getList(size);
		assertEquals("List must have expected size", size, defaultUsers.size());
	}
	
	@Test
	public void shouldBuildDefaultTraitForUser4() {
		Pojory.define(User.class, new Trait("Default") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("DefaultUser");
				user.setAdmin(false);
				user.setAddress("Rua abc, 123, Dreamland");
			}
		});
		int size = 5;
		List<User> defaultUsers = pojory(User.class).trait("default").getList(size);
		assertEquals("List must have expected size", size, defaultUsers.size());
	}
	
	@Test
	public void shouldReturnCustomTraitForUser() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		User adminUser = pojory(User.class).trait("AdminUser").getOne();
		assertEquals("AdminUser", adminUser.getName());
		assertEquals(true, adminUser.isAdmin());
	}
	
	@Test
	public void shouldReturnListOfCustomTraitForUser() {
		Pojory.define(User.class, new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
		});
		int size = 5;
		List<User> adminUsers = pojory(User.class).trait("adminUser").getList(size);
		assertEquals("List must have expected size", size, adminUsers.size());
	}
	
	@Test
	public void shouldMaintainVariousTraitsForClasses() {
		Pojory.define (User.class, 
			new Trait("AdminUser") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("AdminUser");
				user.setAdmin(true);
			}
			},
			new Trait("default") {
			public void definitions(Object obj) {
				User user = (User) obj;
				user.setName("DefaultUser");
				user.setAdmin(false);
				user.setAddress("Rua abc, 123, Dreamland");
			}
		});
		Pojory.define (Product.class, 
			new Trait("default") {
			public void definitions(Object obj) {
				Product p = (Product) obj;
				p.setId(1);
				p.setName("Monitor Samsung 21'");
				p.setPrice(300.52);
			}
		});
		assertNotNull("Must return the user trait", pojory(User.class).trait("default").getOne());
		assertNotNull("Must return the user trait", pojory(User.class).trait("AdminUser").getOne());
		assertNotNull("Must return the product default trait", pojory(Product.class).getOne());
	}
	
	@Test
	public void shouldGenerateCorrectSequenceValues() {
		Pojory.define (Product.class, new Trait("ProdWithSeq") {

			// Define "sequence" stuff
			double seq_val = 0.99, seq_inc = 1;
			
			public void definitions(Object obj) {
				Product p = (Product) obj;
				p.setId(1);
				p.setName("Monitor Samsung 21'");
				
				seq_val += seq_inc;
				p.setPrice(seq_val);
			}
		});
		
		List<Product> products = pojory(Product.class).trait("ProdWithSeq").getList(25);
		assertEquals("Last element must have correct price", 25.99, products.get(products.size()-1).getPrice(), 2);
	}
	
	/*
	 * Using Factory.traits(String...).getOne()
	 */
	@Test
	public void shouldCombineTraitsOfAClass() {
		Pojory.define(User.class, 
				new Trait("User") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setName("Common User");
				}},
				new Trait("Admin") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setAdmin(true);
				}},
				new Trait("Active") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setActive(true);
				}});
	
		User inactiveAdmin = pojory(User.class).traits("user", "admin").getOne();
		assertEquals("Common User", inactiveAdmin.getName());
		assertTrue(inactiveAdmin.isAdmin());
		assertFalse(inactiveAdmin.isActive());
	}
	
	/*
	 * Using Factory.traits(String...).getList(int)
	 */
	@Test
	public void shouldCombineTraitsOfAClass2() {
		Pojory.define(User.class, 
				new Trait("User") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setName("Common User");
				}},
				new Trait("Admin") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setAdmin(true);
				}},
				new Trait("Active") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setActive(true);
				}});

		List<User> activeUsers = pojory(User.class).traits("user", "active").getList(3);
		assertEquals("Must contain 3 objects", 3, activeUsers.size());
		assertTrue("Must be active user", activeUsers.get(0).isActive());
		assertTrue("Must be active user", activeUsers.get(1).isActive());
		assertTrue("Must be active user", activeUsers.get(2).isActive());
	}
	
	@Test
	public void shouldConstructAPojoComposedOfOtherPojo() {
		Pojory.define(User.class,
			new Trait("default") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setActive(true);
					user.setAdmin(false);
					user.setName("Default User" + random(range(1, 1000)));
					user.setAddress("Rua cba, 715, Passo Fundo - RS");
				}
			},
			new Trait("valid") {
				public void definitions(Object obj) {
					User user = (User) obj;
					user.setActive(true);
					user.setAdmin(random(true, false));
					user.setName("Valid User" + random(range(1, 1000)));
					user.setAddress("Rua abc, 75, Passo Fundo - RS");
					
					List<User> users = pojory(User.class).trait("default").getList(3);
					user.setUsers(users);
				}
			}
		);
		
		User user = pojory(User.class).trait("valid").getOne();
		assertTrue(user.getName().startsWith("Valid User"));
		assertEquals(3, user.getUsers().size());
	}
}

	
/*
 * Test Helper Class
 */
class User {
	private String name;
	private boolean admin;
	private String address;
	private boolean active;
	private List<User> users;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public String toString() {
		return getName() + " - " + getAddress() + " - " + 
			   isActive() + " - " + isAdmin() + " - " + getUsers();
	}
}

/*
 * Test Helper Class
 */
class Product {
	private int id;
	private String name;
	private String description;
	private double price;
	
	public Product(int id, String name, String description, double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public Product() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}