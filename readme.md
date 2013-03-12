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
    
    Pojory.define(User.class, new Trait("Default") {
		public void definitions(Object obj) {
			User user = (User) obj;
			user.setName("DefaultUser");
			user.setAdmin(false);
			user.setAddress("Rua abc, 123, Dreamland");
		}
	});
	
Once we define it, we can ask Pojory to build objects based at this trait:

	User defaultUser = Pojory.pojory(User.class).trait("Default").getOne();
	
At this moment, defaultUser has the "definitions" of "Default" trait.

	System.out.print(defaultUser.getName()); // prints DefaultUser 

**Note**: *trait names are case-insensitive. In this example, Default trait can be referred as "default", "DEFAULT" and so on*.

**Note 2**: *If you define a "Default" trait, Pojory permits you call getOne() and getList() methods without specifying the trait name. For instance*:

	User defaultUser = Pojory.pojory(User.class).getOne();
	
*has the same effect*.

<hr/>
	
If you need to build a list of "Default" Users in some unit test, Pojory can help you:

	List<User> defaultUsers = Pojory.pojory(User.class).getList(10);
	
#### Defining more Traits

Let's expand our example and create more profiles for users in our system.

	Pojory.define(User.class, 
		new Trait("Default") {
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
		}
	});
	
Now, we can ask Pojory to build objects for each of them:

	User defaultUser = Pojory.pojory(User.class).getOne(); // Default trait
	User adminUser = Pojory.pojory(User.class).trait("admin").getOne(); // Admin trait
	User activeUser = Pojory.pojory(User.class).trait("active").getOne(); // Active trait
	
OK, we have our 3 objects, but there's a problem. The "adminUser" has just the boolean admin attribute configured at trait, but how about the name, email?

We can solve this, by replicating "Default" trait code to the "Admin" trait, but it's not much elegant.

So, what can we do? =/

Pojory help us providing a mechanism to "merge"(or combine) different defined traits. So, we can obtain a default user "enhanced" with admin stuff:

	 User adminUser = Pojory.pojory(User.class).traits("default", "active").getOne();
	 
or, if we want a list:

	List<User> adminUsers = Pojory.pojory(User.class).traits("default", "active").getList(5);
	
Hope you enjoy it! =D

## license
Pojory is licensed under the terms of the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)