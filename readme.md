# Pojory

Simple tool to easily set up Java POJOs for unit tests

## Why?

Setting up objects during unit tests is a tedious task. As the project grows, it's common that "set up code" become 
messy, and several helper classes arise to try control the process.

Intention of Pojory is centralize code that sets up your POJOs, providing a uniform way to build them, 
beyond encourage eliminate code duplicates too. 
Pojory supports combine different "traits" of POJOs, making the build process more powerful for your tests.


## Getting Started

In order to permit Pojory build our POJOS, we need to define some "traits" for them. 
For example, suppose that we have a User class in our system, and we need to use them in our unit tests.
So, let's define some profiles(traits) that will be useful for our tests. We will start by the "Default" trait, that will be the "base" User:
    
    Pojory.define(User.class)
		.trait("default", new Trait<User>() {
			public void definitions(User user) {
				user.setName("DefaultUser");
				user.setAdmin(false);
				user.setAddress("Rua abc, 123, Dreamland");
			}
		});
	
Once we define it, we can ask Pojory to build objects based at this trait:

	User defaultUser = Pojory.pojo(User.class).trait("default").getOne();
	
At this moment, defaultUser has the "definitions" of "default" trait.

	System.out.print(defaultUser.getName()); // prints DefaultUser 

**Note**: *trait names are case-insensitive. In this example, Default trait can be referred as "default", "DEFAULT" and so on*.

<hr/>
	
If you need to build a list of "Default" Users in some unit test, Pojory can help you:

	List<User> defaultUsers = Pojory.pojo(User.class).trait("default").getList(10);
	
#### Defining more Traits

Let's expand our example and create more profiles for users in our system.

	Pojory.define(User.class)
		.trait("default", new Trait<User>() {
			public void definitions(User user) {
				user.setName("DefaultUser");
				user.setAddress("Rua abc, 123, Dreamland");
			}
		})
		.trait("admin", new Trait<User>() {
			public void definitions(User user) {
				user.setAdmin(true);
			}
		})
		.trait("active", new Trait<User>() {
			public void definitions(User user) {
				user.setActive(true);
			}
		});
	
Now, we can ask Pojory to build objects for each of them:

	User defaultUser = Pojory.pojo(User.class).trait("default").getOne(); // Default trait
	User adminUser = Pojory.pojo(User.class).trait("admin").getOne(); // Admin trait
	User activeUser = Pojory.pojo(User.class).trait("active").getOne(); // Active trait
	
OK, we have our 3 objects, but there's a problem. The "adminUser" has just the boolean admin attribute configured at trait, but how about the name, address?

We can solve this, by replicating "Default" trait code to the "Admin" trait, but it's not much elegant.

So, what can we do? =/

Pojory help us providing a mechanism to "merge"(or combine) different defined traits. So, we can obtain a default user "enhanced" with admin stuff:

	 User adminUser = Pojory.pojo(User.class).traits("default", "admin").getOne();
	 
or, if we want a list:

	List<User> adminUsers = Pojory.pojo(User.class).traits("default", "admin").getList(5);
	
Hope you enjoy it! =D

## Limitations

Pojory relies on Java Reflection API to build object instances, that means you must have a "no-arg" constructor on your class. 
By the way, when talking about pojos we infer that "no-arg" constructor are a requisite.

If you have a class that are setted up via constructor (not setters), you can use Pojory feature of factories. 
It permits you to create a object for your own and you can still request for traits (but not combine them) or other factories during the process.

For example:

	public class CPF {

		private final String cpf;
		
		public CPF(String cpf) {
			this.cpf = cpf;
			// call some logic to validate CPF for example
		}
	
		public String getCpf() {
			return cpf;
		}
	}
	
	Pojory.define(CPF.class).factory("valid", new Factory<CPF>() {
		public CPF createInstance() {
			/*
				Here you construct your object for your own
				You can call Pojory.pojo(SomeClass.class).trait("someTrait").getOne() to fulfill the object if necessary
				By example:
				   cpf.setSomeField(Pojory.pojo(SomeClass.class).trait("someTrait").getOne());
			*/
			CPF cpf = new CPF("39295870417"); 
			return cpf;
		}
	});
	
	CPF cpf = Pojory.pojo(CPF.class).factory("valid").getOne();

## license
Pojory is licensed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)