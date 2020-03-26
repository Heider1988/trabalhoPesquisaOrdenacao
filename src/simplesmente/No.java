package simplesmente;

import dados.Cliente;

public class No {

	private Cliente info;
	private No prox;

	public No(Cliente elem) {
		this.prox = null;
		this.info = elem;
	}

	public Cliente getInfo() {
		return info;
	}

	public void setInfo(Cliente elem) {
		this.info = elem;
	}

	public No getProx() {
		return prox;
	}

	public void setProx(No novoNo) {
		this.prox = novoNo;
	}

	

}
