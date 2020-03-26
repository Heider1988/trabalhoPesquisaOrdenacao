package hashing_encadeado;

import dados.Cliente;
import simplesmente.No;

public class EncadeamentoHash {

	private NoHash[] table;
	private int tam;

	public EncadeamentoHash(int tamVet) {
		table = new NoHash[nextPrimo(tamVet)];
		tam = 0;
	}

	// eVazia
	public boolean eVazia() {
		return tam == 0;
	}

	public int getTam() {
		return tam;
	}

	public NoHash[] getTable() {
		return table;
	}

	// Inserir
	public void inserir(Cliente elem) {
		int pos = (int) myhash(elem.chaveCpf());
		NoHash novoNo = new NoHash(elem);
		if (table[pos] == null)
			table[pos] = novoNo;
		else {
			novoNo.setProx(table[pos]);
			table[pos] = novoNo;
		}
		tam++;
	}

	// Função Hash
	private long myhash(long x) {
		long hashVal = x;
		hashVal = hashVal % table.length;
		if (hashVal < 0) {
			hashVal += table.length;
		}
		return hashVal;
	}

	// Se o número não for primo, então localiza o primo mais próximo incrementando
	// mais 1
	private static int nextPrimo(int n) {
		while (ehPrimo(n) == false) {
			n++;
		}

		return n;
	}

	// Localiza o número primo
	private static boolean ehPrimo(int numero) {
		boolean b = false;
		for (int j = 2; j < numero; j++) {
			if (numero % j == 0)
				return false;
		}
		return true;
	}

	// Pesquisa os elementos dentro do vetor, conforme a posição solicitada
	public NoHash[] pesquisarNo(int pos, long chave) {
		NoHash atual = table[pos];
		NoHash[] no = new NoHash[this.tam];

		int i = 0;
		while (atual != null) {
			if (atual.getInfo().chaveCpf() == chave) {
				no[i] = atual;
				i++;
			}

			atual = atual.getProx();
		}
		return no;
	}

}
