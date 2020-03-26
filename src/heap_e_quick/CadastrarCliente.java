package heap_e_quick;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import dados.Cliente;

public class CadastrarCliente {

	private Cliente[] info;
	private int fim;

	public CadastrarCliente(int tamanho) {
		this.fim = 0;
		this.info = new Cliente[tamanho];
	}

	public Cliente getInfo(int i) {
		return info[i];
	}

	public void setInfo(Cliente elem, int i) {
		this.info[i] = elem;
	}

	public int getFim() {
		return fim;
	}

	// eVazia
	public boolean eVazia() {
		return (this.fim == 0);
	}

	// eCheia
	public boolean eCheia() {
		return (this.fim == this.info.length);
	}

	// retorna verdadeiro se conseguiu inserir o novo nó no final na lista.
	public boolean inserir(Cliente elem) {
		if (this.eCheia()) {
			return false;
		} else {
			this.info[this.fim] = elem;
			this.fim++;
			return true;
		}
	}

	// QUICKSORT
	public void quicksort() {
		ordena(0, this.fim - 1);
	}

	private void ordena(int esq, int dir) {

		int i = esq;
		int j = dir;
		Cliente temp;

		Cliente pivo = this.info[(i + j) / 2];
		do {

			while (this.info[i].compareTo(pivo) < 0)
				i++;
			while (this.info[j].compareTo(pivo) > 0)
				j--;

			if (i <= j) {

				temp = this.info[i];
				this.info[i] = this.info[j];
				this.info[j] = temp;
				i++;
				j--;
			}
		} while (i < j);
		if (esq < j)
			ordena(esq, j);
		if (dir > i)
			ordena(i, dir);
	}

	// HeapSort O índice inicial do vetor é 0 (zero), o sucessor à esquerda do
	// elemento de índice i será
	// 2i+1 e o da direita, 2i+2.
	public void heapSort() {
		int dir = this.fim - 1;
		int esq = (dir - 1) / 2;
		Cliente temp;

		while (esq >= 0) {
			refazHeap(esq, this.fim - 1);
			esq--;
		}

		while (dir > 0) {
			temp = this.info[0];
			this.info[0] = this.info[dir];
			this.info[dir] = temp;
			dir--;

			refazHeap(0, dir);
		}
	}

	// HeaSort (Lógico)
	private void refazHeap(int esq, int dir) {
		int i = esq;
		int mF = 2 * i + 1; // Maior Filho
		Cliente raiz = this.info[i];
		boolean heap = false;

		while ((mF <= dir) && (!heap)) {
			if (mF < dir) {
				if (this.info[mF].compareTo(this.info[mF + 1]) < 0) {
					mF++;
				}
			}
			// Se a raiz for menor que o maior filho, ocorre a troca. Volta e verifica se
			// sub-árvore continuará heap
			if (raiz.compareTo(this.info[mF]) < 0) {
				this.info[i] = this.info[mF];
				i = mF;
				mF = 2 * i + 1;
			}

			else {
				heap = true;
			}

			this.info[i] = raiz;
		}
	}

	// pesquisa binária
	public int pesqBinaria(long chave) {
		int meio, esq, dir;
		esq = 0;
		dir = this.fim - 1;
		while (esq <= dir) {
			meio = (esq + dir) / 2;
			if (chave == this.info[meio].chaveCpf())
				return meio;
			else {
				if (chave < this.info[meio].chaveCpf())
					dir = meio - 1;
				else
					esq = meio + 1;
			}
		}
		return -1;
	}

	// ESTE MÉTODO VERIFICA SE HÁ CPF NA LISTA CONFORME OS CPFS PASSADOS EM OUTRO
	// ARQUIVO
	public void compararCPFS(int tipoMetodo, String tamanho, String tipo) throws IOException {
		Scanner scan = new Scanner(new File("./src/dados/Cliente.txt"));

		GravarArquivo gravarCpf = null;

		if (tipoMetodo == 1) {
			gravarCpf = new GravarArquivo(
					"./src/arquivos_comparados_cpf_heap/cpfs_comparados_HEAP" + "+" + tipo + "+" + tamanho + ".txt");
		} else {
			if (tipoMetodo == 2) {
				gravarCpf = new GravarArquivo(
						"./src/arquivos_comparados_cpf_quick/cpfs_comparados_QUICK" + "+" + tipo + "+" + tamanho + ".txt");
			}
		}

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

		for (int i = 0; i < cpfOrganizado.length; i++) {
			int pos = 0;
			pos = pesqBinaria(cpfOrganizado[i]);

			if (pos >= 0) {

				int temp = pos;
				int numeroConta = 0;
				String tipoConta = "";
				double saldoTotal = 0.0;

				// COM VALOR IGUAL À ESQUERDA E À DIREITA
				if (this.info[pos].chaveCpf() == this.info[temp - 1].chaveCpf()
						&& this.info[pos].chaveCpf() == this.info[temp + 1].chaveCpf()) {
					line += "CPF " + this.info[pos].getCpfTitular() + ":\n";
					while (pos >= 0 && this.info[pos].chaveCpf() == this.info[temp - 1].chaveCpf()) {
						pos--;
					}
					pos++;
					while (pos < this.fim && this.info[pos].chaveCpf() == this.info[temp + 1].chaveCpf()) {
						numeroConta = Integer.parseInt(this.info[pos].getNumConta().substring(0, 3));
						if (numeroConta == 1) {
							tipoConta = " Conta corrente";
						} else {
							if (numeroConta == 2) {
								tipoConta = " Conta poupança";
							}
						}
						saldoTotal += this.info[pos].getSaldo();
						line += "Agencia: " + this.info[pos].getAgencia() + tipoConta + ": "
								+ this.info[pos].getNumConta() + " Saldo: " + this.info[pos].getSaldo() + "\n";
						pos++;
					}

					line += "SALDO TOTAL: " + saldoTotal + "\n\n";

				} else {
					// COM VALOR IGUAL À ESQUERDA
					if (this.info[pos].chaveCpf() == this.info[temp - 1].chaveCpf()) {
						line += "CPF " + this.info[pos].getCpfTitular() + ":\n";
						while (pos >= 0 && this.info[pos].chaveCpf() == this.info[temp - 1].chaveCpf()) {

							numeroConta = Integer.parseInt(this.info[pos].getNumConta().substring(0, 3));
							if (numeroConta == 1) {
								tipoConta = " Conta corrente";
							} else {
								if (numeroConta == 2) {
									tipoConta = " Conta poupança";
								}
							}
							saldoTotal += this.info[pos].getSaldo();
							line += "Agencia: " + this.info[pos].getAgencia() + tipoConta + ": "
									+ this.info[pos].getNumConta() + " Saldo: " + this.info[pos].getSaldo() + "\n";

							pos--;
						}

						line += "SALDO TOTAL: " + saldoTotal + "\n\n";

					} else {
						// COM VALOR IGUAL À DIREITA
						if (this.info[pos].chaveCpf() == this.info[temp + 1].chaveCpf()) {
							line += "CPF " + this.info[pos].getCpfTitular() + ":\n";
							while (pos < this.fim && this.info[pos].chaveCpf() == this.info[temp + 1].chaveCpf()) {

								numeroConta = Integer.parseInt(this.info[pos].getNumConta().substring(0, 3));
								if (numeroConta == 1) {
									tipoConta = " Conta corrente";
								} else {
									if (numeroConta == 2) {
										tipoConta = " Conta poupança";
									}
								}
								saldoTotal += this.info[pos].getSaldo();
								line += "Agencia: " + this.info[pos].getAgencia() + tipoConta + ": "
										+ this.info[pos].getNumConta() + " Saldo: " + this.info[pos].getSaldo() + "\n";

								pos++;
							}

							line += "SALDO TOTAL: " + saldoTotal + "\n\n";

						} else {

							if (this.info[pos].chaveCpf() != this.info[temp - 1].chaveCpf()
									&& this.info[pos].chaveCpf() != this.info[temp + 1].chaveCpf()) {

								line += "CPF " + cpfOrganizado[i] + ":\n";

								numeroConta = Integer.parseInt(this.info[pos].getNumConta().substring(0, 3));
								if (numeroConta == 1) {
									tipoConta = " Conta corrente";
								} else {
									if (numeroConta == 2) {
										tipoConta = " Conta poupança";
									}
								}
								saldoTotal += this.info[pos].getSaldo();
								line += "Agencia: " + this.info[pos].getAgencia() + tipoConta + ": "
										+ this.info[pos].getNumConta() + " Saldo: " + this.info[pos].getSaldo() + "\n";

								line += "SALDO TOTAL: " + saldoTotal + "\n\n";
							}
						}
					}
				}

			} else {
				// NÃO HÁ CPF
				if (pos == -1) {
					line += "CPF " + cpfOrganizado[i] + "\n" + "NÃO HÁ CLIENTE COM O CPF " + cpfOrganizado[i] + "\n\n";
				}
			}
		}

		gravarCpf.gravarArquivo(line.trim());
		gravarCpf.fecharArquivo();

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

	// retorna uma String com todo o conteúdo da lista, já ordenados.
	public String toString() {
		String msg = "";
		for (int i = 0; i < this.fim; i++) {
			msg += "CPF:" + this.info[i].getCpfTitular() + ", Agência: " + this.info[i].getAgencia() + " Conta:"
					+ this.info[i].getNumConta() + ", Saldo: " + this.info[i].getSaldo() + "\n";
		}

		return msg.trim();

	}

}
