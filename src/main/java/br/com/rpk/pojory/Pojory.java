package br.com.rpk.pojory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pojory {

	static private Map<Class<?>, List<Trait>> definitions = new HashMap<Class<?>, List<Trait>>();
	
	static public void define(Class<?> clazz, Trait trait) {
		List<Trait> clazzTraits = definitions.get(clazz);
		if (clazzTraits == null) {
			clazzTraits = new ArrayList<Trait>();
		}
		clazzTraits.add(trait);
		definitions.put(clazz, clazzTraits);
	}
	
	static public void define(Class<?> clazz, Trait ...traits) {
		for (Trait t : traits)
			define(clazz, t);
	}
	
	static public PojoryFactory pojory(Class<?> clazz) {
		return new PojoryFactory(clazz, getClazzTraits(clazz));
	}

	private static List<Trait> getClazzTraits(Class<?> clazz) throws IllegalStateException {
		List<Trait> clazzTraits = definitions.get(clazz);
		if (clazzTraits == null) {
			throw new IllegalStateException("There isn't traits for given class");
		}
		return clazzTraits;
	}
}
