package br.com.rpk.pojory;


public abstract class Trait {
	
	private final String name;

	public Trait(String name) {
		this.name = name;
	}
	
	protected String getName() { 
		return this.name; 
	}
	
	public abstract void definitions(Object obj);
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Trait) {
			Trait that = (Trait) obj;
			return this.getName().equalsIgnoreCase(that.getName());
		}
		return false;
	}
}
