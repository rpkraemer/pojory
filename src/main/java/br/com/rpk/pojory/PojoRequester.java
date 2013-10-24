package br.com.rpk.pojory;

import java.util.ArrayList;
import java.util.List;

public class PojoRequester<T> {

	private final Class<T> pojo;
	private final PojoDefinitions<T> pojoDefinitions;
	private List<String> chosenTraits;
	private String chosenFactory;

	public PojoRequester(Class<T> pojo, PojoDefinitions<T> pojoDefinitions) {
		this.pojo = pojo;
		this.pojoDefinitions = pojoDefinitions;
		this.chosenTraits = new ArrayList<String>();
	}

	public PojoRequester<T> trait(String trait) {
		chosenTraits.add(trait);
		return this;
	}

	public PojoRequester<T> traits(String... traits) {
		for (String trait : traits) {
			chosenTraits.add(trait);
		}
		return this;
	}

	public T getOne() {
		T instance;

		if (requestForFactory()) {
			Factory<T> factory = pojoDefinitions.getFactory(chosenFactory);
			instance = factory.createInstance();
		} else {
			instance = getPojoInstance();
			if (!mustCombineTraits()) {
				Trait<T> trait = pojoDefinitions.getTrait(chosenTraits.get(0));
				trait.definitions(instance);
			} else {
				for (String traitName : chosenTraits) {
					Trait<T> trait = pojoDefinitions.getTrait(traitName);
					trait.definitions(instance);
				}
			}
		}
		return instance;
	}

	private boolean requestForFactory() {
		return chosenFactory != null;
	}

	private boolean mustCombineTraits() {
		return chosenTraits.size() > 1;
	}

	private T getPojoInstance() {
		try {
			return pojo.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(String.format(
					"The pojo '%s' must have a default no-arg constructor: %s",
					pojo.getSimpleName(), e.getMessage()));
		}
	}

	public List<T> getList(int size) {
		if (size < 1) {
			throw new IllegalArgumentException(
					"The size of required list must be greater than 0");
		}
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			list.add(getOne());
		}
		return list;
	}

	public PojoRequester<T> factory(String factory) {
		chosenFactory = factory;
		return this;
	}
}
