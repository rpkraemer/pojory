package br.com.rpk.pojory;

public class PojoDefiner<T> {

	private final PojoDefinitions<T> definitions;

	public PojoDefiner(PojoDefinitions<T> pojoDefinitions) {
		this.definitions = pojoDefinitions;
	}
	
	public PojoDefiner<T> trait(String name, Trait<T> trait) {
		checkValidName(name);
		if (trait == null) {
			throw new IllegalArgumentException("The trait must be configured. Cannot be null.");
		}
		definitions.addTrait(name, trait);
		return this;
	}

	public PojoDefiner<T> factory(String name, Factory<T> factory) {
		checkValidName(name);
		if (factory == null) {
			throw new IllegalArgumentException("The factory must be configured. Cannot be null.");
		}
		definitions.addFactory(name, factory);
		return this;
	}
	
	private void checkValidName(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("The trait/factory name must be informed.");
		}
	}

	
}
