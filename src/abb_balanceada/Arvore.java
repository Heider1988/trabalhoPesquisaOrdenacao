package abb_balanceada;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import arvore_avl.GravarArquivo;
import arvore_avl.NoArvAvl;
import dados.Cliente;
import simplesmente.ListaSimpes;
import simplesmente.No;

public class Arvore {

	private NoArv raiz;
	private int quantNos;

	public Arvore() {
		this.raiz = null;
		this.quantNos = 0;
	}

	public NoArv getRaiz() {
		return raiz;
	}

	public int getQuantNos() {
		return quantNos;
	}

	// eVaziaArv
	public boolean eVazia() {
		return (this.raiz == null);
	}

	// inserir um nó no final da lista
	public void inserirNoLista(NoArv no, Cliente novo) {
		if (no.getLista() == null) {
			ListaSimpes aux = new ListaSimpes();
			no.setLista(aux);
		}

		no.setLista(no.getLista().inserirNo(novo));
	}

	// Método inserir
	public void inserirNoArv(Cliente cadastro) {
		this.raiz = inserir(cadastro, this.raiz);

	}

	// Método inserir (lógico)
	private NoArv inserir(Cliente cadastro, NoArv no) {
		if (no == null) {
			NoArv novoNo = new NoArv(cadastro);
			this.quantNos++;
			return novoNo;
		} else {
			if (cadastro.chaveCpf() < no.getInfo().chaveCpf()) {
				no.setEsq(inserir(cadastro, no.getEsq()));
				return no;
			} else if (cadastro.chaveCpf() > no.getInfo().chaveCpf()) {
				no.setDir(inserir(cadastro, no.getDir()));
				return no;
			} else {
				inserirNoLista(no, cadastro);
				return no;

			}
		}

	}

	// Método pesquisar
	public NoArv pesquisar(long valor) {

		NoArv atual = pesquisar(valor, this.raiz);
		if (atual != null) {
			return atual;
		} else {
			return null;
		}
	}

	// Método pesquisar (lógico)
	public NoArv pesquisar(long valor, NoArv no) {
		if (no != null) {
			if (valor < no.getInfo().chaveCpf()) {
				no = pesquisar(valor, no.getEsq());

			} else {
				if (valor > no.getInfo().chaveCpf()) {
					no = pesquisar(valor, no.getDir());
				}
			}
		}
		return no;
	}

	// Método central NoArv
	public NoArv[] caminhoCentral() {
		int[] n = new int[1];
		n[0] = 0;
		NoArv[] vet = new NoArv[this.quantNos];
		return (caminhoCentral(this.raiz, vet, n));
	}

	// Método central NoArv (lógico)
	private NoArv[] caminhoCentral(NoArv arv, NoArv[] vet, int[] n) {
		if (arv != null) {
			vet = caminhoCentral(arv.getEsq(), vet, n);
			vet[n[0]] = arv;
			n[0]++;
			vet = caminhoCentral(arv.getDir(), vet, n);
		}
		return vet;
	}

	// BALANCEAR
	public void arvoreBalanceada() {
		Arvore temp = new Arvore();
		this.balancear(caminhoCentral(), temp, 0, caminhoCentral().length - 1);
		// return temp;
	}

	// BALANCEAR (LÓGICO)
	private void balancear(NoArv[] vet, Arvore temp, int inic, int fim) {
		int meio;
		if (fim >= inic) {
			meio = (inic + fim) / 2;
			temp.inserirNoArv(vet[meio].getInfo());
			this.balancear(vet, temp, inic, meio - 1);
			this.balancear(vet, temp, meio + 1, fim);
		}
	}

	// REMOVENDO CPFS DUPLICADOS DA LISTA QUE FOI FORNECIDA PARA EFETUAR A
	// COMPARAÇÃO
	public long[] removerRepetidos(long[] original) {
		// remover repetidos
		long[] unicos = new long[original.length];
		int qtd = 0;
		for (int i = 0; i < original.length; i++) {
			boolean existe = false;
			for (int j = 0; j < qtd; j++) {
				if (unicos[j] == original[i]) {
					existe = true;
					break;
				}
			}
			if (!existe) {
				unicos[qtd++] = original[i];
			}
		}

		return unicos = Arrays.copyOf(unicos, qtd);

	}

	// ESTE MÉTODO VERIFICA SE HÁ CPF NA ÁRVORE COMPARANDO COM O ÁRQUIVO DE CPFS
	public void compararCPFS(String tamanho, String tipo) throws IOException {

		Scanner scan = new Scanner(new File("./src/dados/Cliente.txt"));

		GravarArquivo gravarCpf = null;

		gravarCpf = new GravarArquivo(
				"./src/arquivos_comparados_cpf_abb/cpfs_comparados_ABB" + "+" + tipo + "+" + tamanho + ".txt");

		long[] vetorCpfs = new long[400];

		int aux = 0;
		while (scan.hasNext()) {
			vetorCpfs[aux] = Long.parseLong(scan.next().trim());
			aux++;

		}
		scan.close();

		long[] cpfOrganizado = removerRepetidos(vetorCpfs);
		Arrays.sort(cpfOrganizado);

		String line = "";

		int pos = 0;
		while (pos < cpfOrganizado.length) {
			NoArv atual = pesquisar(cpfOrganizado[pos]);
			double saldo = 0.0;
			if (atual != null) {

				int numeroConta = 0;
				String tipoConta = "";

				numeroConta = Integer.parseInt(atual.getInfo().getNumConta().substring(0, 3));

				line += "CPF " + atual.getInfo().getCpfTitular() + ": \n";

				if (numeroConta == 1) {
					tipoConta = ", Conta Corrente: ";
				} else {
					if (numeroConta == 2) {
						tipoConta = ", Conta Poupança: ";
					}
				}

				line += "Agencia: " + atual.getInfo().getAgencia() + tipoConta + atual.getInfo().getNumConta()
						+ ", Saldo: " + atual.getInfo().getSaldo() + "\n";

				saldo += atual.getInfo().getSaldo();

				if (atual.getLista() != null) {
					No pesq = atual.getLista().getPrim();
					while (pesq != null) {

						numeroConta = Integer.parseInt(pesq.getInfo().getNumConta().substring(0, 3));
						if (numeroConta == 1) {
							tipoConta = ", Conta Corrente: ";
						} else {
							if (numeroConta == 2) {
								tipoConta = ", Conta Poupança: ";
							}
						}

						line += "Agencia: " + pesq.getInfo().getAgencia() + tipoConta + pesq.getInfo().getNumConta()
								+ ", Saldo: " + pesq.getInfo().getSaldo() + "\n";

						saldo += pesq.getInfo().getSaldo();

						pesq = pesq.getProx();

					}
				}

				line += "Saldo Total: " + saldo + "\n\n";
			} else {
				line += "CPF: " + cpfOrganizado[pos] + ":\n";
				line += "NÃO HÁ CLIENTE COM O CPF " + cpfOrganizado[pos] + "\n\n";
			}

			pos++;
		}

		gravarCpf.gravarArquivo(line.trim());
		gravarCpf.fecharArquivo();

	}

}
