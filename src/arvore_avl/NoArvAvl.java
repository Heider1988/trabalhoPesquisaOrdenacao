package arvore_avl;

import dados.Cliente;
import simplesmente.*;

public class NoArvAvl {

	private Cliente info;
	private NoArvAvl esq, dir;
	private ListaSimpes lista;
	private byte fatorBalanceamento;

	public NoArvAvl(Cliente elem) {
		this.info = elem;
		this.fatorBalanceamento = 0;
		this.lista = null;
		this.esq = null;
		this.dir = null;
		
	}

	public Cliente getInfo() {
		return info;
	}

	public void setInfo(Cliente elem) {
		this.info = elem;
	}

	public NoArvAvl getEsq() {
		return esq;
	}

	public void setEsq(NoArvAvl novoNo) {
		this.esq = novoNo;
	}

	public NoArvAvl getDir() {
		return dir;
	}

	public void setDir(NoArvAvl novoNo) {
		this.dir = novoNo;
	}

	public ListaSimpes getLista() {
		return lista;
	}

	public void setLista(ListaSimpes novaLista) {
		this.lista = novaLista;
	}

	public byte getFatorBalanceamento() {
		return this.fatorBalanceamento;
	}

	public void setFatorBalanceamento(byte fatorBalanceamento) {
		this.fatorBalanceamento = fatorBalanceamento;
	}

}
