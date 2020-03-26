package hashing_encadeado;

import dados.Cliente;

public class NoHash {

	private NoHash prox;
	private Cliente info;

	public NoHash(Cliente elem) {
		info = elem;
		prox = null;
	}

	public NoHash getProx() {
		return prox;
	}

	public void setProx(NoHash next) {
		this.prox = next;
	}

	public Cliente getInfo() {
		return info;
	}

	public void setInfo(Cliente elem) {
		this.info = elem;
	}
	
	
	
	

}
