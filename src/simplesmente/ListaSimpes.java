package simplesmente;

import dados.Cliente;

public class ListaSimpes {

	private No prim;
	private No ult;
	private int quantNos;

	public ListaSimpes() {
		this.prim = null;
		this.ult = null;
		this.quantNos = 0;
	}

	public No getPrim() {
		return prim;
	}

	public void setPrim(No novoNo) {
		this.prim = novoNo;
	}

	public No getUlt() {
		return ult;
	}

	public void setUlt(No novoNo) {
		this.ult = novoNo;
	}

	public int getQuantNos() {
		return quantNos;
	}

	public void setQuantNos(int novoValor) {
		this.quantNos = novoValor;
	}

	public boolean eVazia() {
		return (this.quantNos == 0);
	}

	// inserir um nó no final da lista
	public ListaSimpes inserirNo(Cliente novo) {
		No novoNo = new No(novo);
		if (this.eVazia()) {
			this.prim = novoNo;
		} else {
			this.ult.setProx(novoNo);
		}
		this.ult = novoNo;
		this.quantNos++;
		return this;
	}

	public Cliente[] pesquisarNo(long chave) {
		No atual = this.prim;
		Cliente[] no = new Cliente[this.quantNos];

		int pos = 0;
		while (atual != null) {
			if (atual.getInfo().chaveCpf() == chave) {
				no[pos] = atual.getInfo();
				pos++;
			}
			atual = atual.getProx();
		}
		return no;
	}

	// mostrar conteúdo da lista
	public String toString() {
		String line = "";
		No atual = this.prim;

		while (atual != null) {
			line += "CPF:" + atual.getInfo().getCpfTitular() + ", Agência: " + atual.getInfo().getAgencia() + " Conta:"
					+ atual.getInfo().getNumConta() + ", Saldo: " + atual.getInfo().getSaldo() + "\n";
			atual = atual.getProx();

		}
		return line;

	}

}
