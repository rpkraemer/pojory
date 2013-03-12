package br.com.rpk.pojory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PojoryFactory {

	private final List<Trait> traits;
	private final Class<?> clazz;
	private List<String> traitNames;
	private String traitName = "default";

	PojoryFactory(Class<?> clazz, List<Trait> traits) {
		this.clazz = clazz;
		this.traits = traits;
	}

	public <T> T getOne() {
		if (mustCombineTraits()) {
			List<Trait> traits = getChoosedTraits();
			return buildObjectCombiningTraits(traits);
		} else {
			Trait trait = getTrait(traitName);
			return buildObject(trait);
		}
	}
	
	public <T> List<T> getList(int quantityOfObjects) {
		if (quantityOfObjects <= 0)
			throw new IllegalArgumentException("You must inform a list size greater than 0");

		if (mustCombineTraits()) {
			List<Trait> traits = getChoosedTraits();
			return buildListOfObjectsCombiningTraits(traits, quantityOfObjects);
		} else {
			Trait trait = getTrait(traitName);
			return buildListOfObjects(trait, quantityOfObjects);
		}
	}
	
	public PojoryFactory trait(String traitName) {
		this.traitName = traitName;
		return this;
	}

	public PojoryFactory traits(String... traitNames) {
		int i = 0;
		for (String traitName : traitNames) traitNames[i++] = traitName.toLowerCase(); 
		this.traitNames = Arrays.asList(traitNames);
		return this;
	}
	
	private Trait getTrait(String traitName) throws IllegalStateException {
		for (Trait t : traits)
			if (traitName.equalsIgnoreCase(t.getName()))
				return t;
		throw new IllegalStateException(
				String.format("There isn't a '%s' trait for given class. Please define it", traitName));
	}

	private <T> T buildObjectCombiningTraits(List<Trait> traits) {
		T instance = null;
		for (int i = 0; i < traits.size(); i++) {
			Trait trait = traits.get(i);
			if (i == 0) instance = buildObject(trait);
			else fulfillObjectWithAnotherTrait(instance, trait);
		}
		return instance;
	}
	
	private <T> void fulfillObjectWithAnotherTrait(T instance, Trait trait) {
		trait.definitions(instance);
	}

	private List<Trait> getChoosedTraits() {
		List<Trait> choosed = new ArrayList<Trait>();
		for (String traitName : traitNames) {
			Trait trait = getTrait(traitName);
			choosed.add(trait);
		}
		return choosed;
	}

	private boolean mustCombineTraits() {
		return traitNames != null;
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> buildListOfObjectsCombiningTraits(List<Trait> traits, int quantityOfObjects) {
		List<T> objects = new ArrayList<T>();
		for (int i = 0; i < quantityOfObjects; i++)
			objects.add((T) buildObjectCombiningTraits(traits));
		return objects;
	}

	@SuppressWarnings("unchecked")
	private <T> T buildObject(Trait trait) {
		T instance;
		try {
			instance = (T) clazz.newInstance();
			trait.definitions(instance);
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> buildListOfObjects(Trait trait, int quantityOfObjects) {
		List<T> objects = new ArrayList<T>();
		for (int i = 0; i < quantityOfObjects; i++)
			objects.add((T) buildObject(trait));
		return objects;
	}
}