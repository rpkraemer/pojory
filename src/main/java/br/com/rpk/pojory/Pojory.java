package br.com.rpk.pojory;

import java.util.HashMap;
import java.util.Map;

public class Pojory {

	static private Map<Class<?>, PojoDefinitions<?>> definitions = new HashMap<Class<?>, PojoDefinitions<?>>();
	
	static public <T> PojoDefiner<T> define(Class<T> pojo) {
		PojoDefinitions<T> pojoDefinitions = getDefinitions(pojo);
		return new PojoDefiner<T>(pojoDefinitions);
	}
	
	static public <T> PojoRequester<T> pojo(Class<T> pojo) {
		PojoDefinitions<T> pojoDefinitions = getDefinitions(pojo);
		return new PojoRequester<T>(pojo, pojoDefinitions);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> PojoDefinitions<T> getDefinitions(Class<T> pojo) {
		PojoDefinitions<T> pojoDefinitions = (PojoDefinitions<T>) definitions.get(pojo);
		if (pojoDefinitions == null) {
			pojoDefinitions = new PojoDefinitions<T>(pojo);
			definitions.put(pojo, pojoDefinitions);
		}
		return pojoDefinitions;
	}
}
