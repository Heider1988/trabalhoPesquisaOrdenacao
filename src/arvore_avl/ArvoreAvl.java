package arvore_avl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import dados.Cliente;
import simplesmente.ListaSimpes;
import simplesmente.No;

public class ArvoreAvl {

	private NoArvAvl raiz;
	private int quantNos;
	private boolean h;
	private static int quant = 0;

	public ArvoreAvl() {
		this.raiz = null;
		this.quantNos = 0;
		this.h = true;
	}

	public NoArvAvl getRaiz() {
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
	public void inserirNoLista(NoArvAvl no, Cliente novo) {
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
	private NoArvAvl inserir(Cliente cadastro, NoArvAvl no) {
		if (no == null) {
			NoArvAvl novoNo = new NoArvAvl(cadastro);
			this.h = true;
			this.quantNos++;
			return novoNo;
		} else {
			if (cadastro.chaveCpf() < no.getInfo().chaveCpf()) {
				no.setEsq(inserir(cadastro, no.getEsq()));
				no = this.balancearDir(no);
				return no;
			} else if (cadastro.chaveCpf() > no.getInfo().chaveCpf()) {
				no.setDir(inserir(cadastro, no.getDir()));
				no = this.balancearEsq(no);
				return no;
			} else {

				inserirNoLista(no, cadastro);
				this.h = false;
				return no;

			}
		}

	}

	// Método para verificar se é necessário o balanceamento para direita do nó
	private NoArvAvl balancearDir(NoArvAvl no) {
		if (this.h)
			switch (no.getFatorBalanceamento()) {
			case 1:
				no.setFatorBalanceamento((byte) 0);
				this.h = false;
				break;
			case 0:
				no.setFatorBalanceamento((byte) -1);
				break;
			case -1:
				no = this.rotaçãoDireita(no);
			}
		return no;
	}

	// Método para verificar se é necessário o balanceamento para esquerda do nó
	private NoArvAvl balancearEsq(NoArvAvl no) {
		if (this.h)
			switch (no.getFatorBalanceamento()) {
			case -1:
				no.setFatorBalanceamento((byte) 0);
				this.h = false;
				break;
			case 0:
				no.setFatorBalanceamento((byte) 1);
				break;
			case 1:
				no = this.rotaçãoEsquerda(no);
			}
		return no;
	}

	// Método para realizar ROTAÇÃO DIREITA (RD) ou ROTAÇÃO DUPLA DIREITA (RDD)
	private NoArvAvl rotaçãoDireita(NoArvAvl no) {
		NoArvAvl temp1, temp2;
		temp1 = no.getEsq();
		// ROTAÇÃO DIREITA (RD)
		if (temp1.getFatorBalanceamento() == -1) {
			no.setEsq(temp1.getDir());
			temp1.setDir(no);
			no.setFatorBalanceamento((byte) 0);
			no = temp1;
		} else {
			// ROTAÇÃO DUPLA DIREITA (RDD)
			temp2 = temp1.getDir();
			temp1.setDir(temp2.getEsq());
			temp2.setEsq(temp1);
			no.setEsq(temp2.getDir());
			temp2.setDir(no);
			// Recalcula o FB do nó à direita na nova árvore
			if (temp2.getFatorBalanceamento() == -1)
				no.setFatorBalanceamento((byte) 1);
			else
				no.setFatorBalanceamento((byte) 0);
			// Recalcula o FB do nó à esquerda na nova árvore
			if (temp2.getFatorBalanceamento() == 1)
				temp1.setFatorBalanceamento((byte) -1);
			else
				temp1.setFatorBalanceamento((byte) 0);
			no = temp2;
		}
		// A raiz terá FB = 0
		no.setFatorBalanceamento((byte) 0);
		this.h = false;
		return no;
	}

	// Método para realizar ROTAÇÃO ESQUERDA (RE) ou ROTAÇÃO DUPLA ESQUERDA (RDE)
	private NoArvAvl rotaçãoEsquerda(NoArvAvl no) {
		NoArvAvl temp1, temp2;
		temp1 = no.getDir();
		if (temp1.getFatorBalanceamento() == 1) {
			no.setDir(temp1.getEsq());
			temp1.setEsq(no);
			no.setFatorBalanceamento((byte) 0);
			no = temp1;
		} else {
			temp2 = temp1.getEsq();
			temp1.setEsq(temp2.getDir());
			temp2.setDir(temp1);
			no.setDir(temp2.getEsq());
			temp2.setEsq(no);
			if (temp2.getFatorBalanceamento() == 1)
				no.setFatorBalanceamento((byte) -1);
			else
				no.setFatorBalanceamento((byte) 0);
			if (temp2.getFatorBalanceamento() == -1)
				temp1.setFatorBalanceamento((byte) 1);
			else
				temp1.setFatorBalanceamento((byte) 0);
			no = temp2;
		}
		no.setFatorBalanceamento((byte) 0);
		this.h = false;
		return no;
	}

	// Método pesquisar
	public NoArvAvl pesquisar(long valor) {

		NoArvAvl atual = pesquisar(valor, this.raiz);
		if (atual != null) {
			return atual;
		} else {
			return null;
		}
	}

	// Método pesquisar (lógico)
	public NoArvAvl pesquisar(long valor, NoArvAvl no) {
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
				"./src/arquivos_comparados_cpf_avl/cpfs_comparados_AVL" + "+" + tipo + "+" + tamanho + ".txt");

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
			NoArvAvl atual = pesquisar(cpfOrganizado[pos]);
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
				// o erro esta acontecendo aqui
				// Cliente[] no = this.raiz.getLista().pesquisarNo(atual.getInfo().chaveCpf());

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
