package br.com.rpk.pojory;

import java.util.HashMap;
import java.util.Map;

public class PojoDefinitions<T> {

	private final Class<?> pojo;
	private Map<String, Trait<T>> traits;
	private Map<String, Factory<T>> factories;

	public PojoDefinitions(Class<?> pojo) {
		this.pojo = pojo;
		this.traits = new HashMap<String, Trait<T>>();
		this.factories = new HashMap<String, Factory<T>>();
	}

	public void addTrait(String name, Trait<T> trait) {
		this.traits.put(name.toLowerCase(), trait);
	}
	
	public void addFactory(String name, Factory<T> factory) {
		this.factories.put(name.toLowerCase(), factory);
	}

	public Class<?> getPojo() {
		return pojo;
	}

	public Trait<T> getTrait(String traitName) {
		Trait<T> trait = traits.get(traitName.toLowerCase());
		if (trait == null) {
			throw new IllegalArgumentException(
					String.format(
							"The pojo '%s' does not have a trait named '%s' configured",
							pojo.getSimpleName(), traitName));
		}
		return trait;
	}
	
	public Factory<T> getFactory(String factoryName) {
		Factory<T> factory = factories.get(factoryName.toLowerCase());
		if (factory == null) {
			throw new IllegalArgumentException(
					String.format(
							"The pojo '%s' does not have a factory named '%s' configured",
							pojo.getSimpleName(), factoryName));
		}
		return factory;
	}
}