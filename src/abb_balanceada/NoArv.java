package abb_balanceada;

import dados.Cliente;
import simplesmente.*;

public class NoArv {

	private Cliente info;
	private NoArv esq, dir;
	private ListaSimpes lista;

	public NoArv(Cliente elem) {
		this.info = elem;
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

	public NoArv getEsq() {
		return esq;
	}

	public void setEsq(NoArv novoNo) {
		this.esq = novoNo;
	}

	public NoArv getDir() {
		return dir;
	}

	public void setDir(NoArv novoNo) {
		this.dir = novoNo;
	}

	public ListaSimpes getLista() {
		return lista;
	}

	public void setLista(ListaSimpes novaLista) {
		this.lista = novaLista;
	}

}
