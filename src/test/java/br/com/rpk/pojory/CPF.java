package br.com.rpk.pojory;

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