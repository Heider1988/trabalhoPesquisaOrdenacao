package hashing_encadeado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;

import arvore_avl.GravarArquivo;
import dados.Cliente;

public class Organizador {

	public void ordernaArquivos() throws IOException {

		LerArquivoHashingCliente ler = null;
		EncadeamentoHash cliente = null;
		GravarArquivo gravar = null;

		File arquivos = new File("./src/arquivos/");

		File[] listFile = arquivos.listFiles();

		String gerarRelatorio = "";

		for (File file : listFile) {

			// Para passar como parâmetro para o método tamanho(),para retornar o tamanho do
			// arquivo
			String nomeArquivo = "./src/arquivos/";
			nomeArquivo = nomeArquivo.concat(file.getName().trim());

			double tempoMedio = 0.0;
			Duration d = null;

			String line = "";

			try {

				String tamanho = nomeArquivo.replaceAll("[^0-9]", "");
				// 22 porque é o caminho totoal até chegar a uma determinada imagem
				// Exemplo: ./src/arquivos/cliente10000alea.txt
				String tipoArquivo = nomeArquivo.substring(22, nomeArquivo.length() - 4).replaceAll("[^a-z]", "")
						.toUpperCase();

				for (int i = 0; i < 5; i++) {

					Instant begin = Instant.now();

					ler = new LerArquivoHashingCliente(nomeArquivo);

					cliente = ler.lerArquivo(tamanho(nomeArquivo));

					compararCPFS(cliente, tamanho, tipoArquivo);

					// cliente.compararCPFS(tamanho, tipoArquivo);

					ler.fecharArquivo();

					Instant end = Instant.now();
					d = Duration.between(begin, end);

					long segundos = 0;
					String s = "";
					Double valorSegundos = null;

					if (d.getSeconds() > 0.0) {
						segundos = d.getSeconds();
						s = segundos + "";
						valorSegundos = Double.parseDouble(s);

						tempoMedio += valorSegundos;

						line = "Tempo do " + (i + 1) + "º loop" + " do arquivo " + file.getName().trim() + ": "
								+ valorSegundos + " segundos.";
						gerarRelatorio += line + "\n";
						System.out.println(line);

					} else {
						segundos = d.getNano();
						s = segundos + "";
						valorSegundos = Double.parseDouble(s);

						tempoMedio += valorSegundos;

						line = "Tempo do " + (i + 1) + "º loop" + " do arquivo " + file.getName().trim() + ": "
								+ valorSegundos + " Nanosegundos.";
						gerarRelatorio += line + "\n";
						System.out.println(line);
					}

				}

			} finally {

				if (d.getSeconds() > 0.0) {
					System.out.println();
					line = "Tempo médio para o arquivo " + file.getName().trim() + " rodando 5 vezes, é: "
							+ tempoMedio / 5 + " segundos.";
					System.out.println(line);
					System.out.println("-----------------------------------------------");

				} else {
					System.out.println();
					line = "Tempo médio para o arquivo " + file.getName().trim() + " rodando 5 vezes, é: "
							+ tempoMedio / 5 + " Nanosegundos.";
					System.out.println(line);
					System.out.println("-----------------------------------------------");
				}

				gerarRelatorio += "\n" + line + "\n" + "--------------------------------------------------------------"
						+ "\n";

			}

		}

		gravar = new GravarArquivo("./src/relatorios/relatorio_HASHING.txt");
		gravar.gravarArquivo(gerarRelatorio);
		gravar.fecharArquivo();

	}

	private static int tamanho(String str) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(str));
		int cont = 0;
		while (scan.hasNext()) {
			scan.nextLine();
			cont++;
		}
		return cont;
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
	public void compararCPFS(EncadeamentoHash no, String tamanho, String tipo) throws IOException {

		Scanner scan = new Scanner(new File("./src/dados/Cliente.txt"));

		GravarArquivo gravarCpf = null;

		gravarCpf = new GravarArquivo(
				"./src/arquivos_comparados_cpf_hashing/cpfs_comparados_HASHING" + "+" + tipo + "+" + tamanho + ".txt");

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
		NoHash[] vetHash = no.getTable();

		int cont = 0;
		while (cont < cpfOrganizado.length) {

			double saldo = 0.0;
			int numeroConta = 0;
			String tipoConta = "";

			// Calcula o hashing
			long chave = cpfOrganizado[cont];
			int pos = (int) (chave % vetHash.length);

			NoHash[] vet = no.pesquisarNo(pos, chave);
			if (vet[0] != null) {

				numeroConta = Integer.parseInt(vet[0].getInfo().getNumConta().substring(0, 3));

				line += "CPF " + vet[0].getInfo().getCpfTitular() + ":\n";

				if (numeroConta == 1) {
					tipoConta = ", Conta Corrente: ";
				} else {
					if (numeroConta == 2) {
						tipoConta = ", Conta Poupança: ";
					}
				}

				line += "Agencia: " + vet[0].getInfo().getAgencia() + tipoConta + vet[0].getInfo().getNumConta()
						+ ", Saldo: " + vet[0].getInfo().getSaldo() + "\n";

				saldo += vet[0].getInfo().getSaldo();

				for (int i = 1; i < vet.length; i++) {

					if (vet[i] != null) {

						numeroConta = Integer.parseInt(vet[i].getInfo().getNumConta().substring(0, 3));
						if (numeroConta == 1) {
							tipoConta = ", Conta Corrente: ";
						} else {
							if (numeroConta == 2) {
								tipoConta = ", Conta Poupança: ";
							}
						}

						line += "Agencia: " + vet[i].getInfo().getAgencia() + tipoConta + vet[i].getInfo().getNumConta()
								+ ", Saldo: " + vet[i].getInfo().getSaldo() + "\n";

						saldo += vet[i].getInfo().getSaldo();
					}
				}
				line += "Saldo Total: " + saldo + "\n\n";

			}

			else {
				line += "CPF: " + cpfOrganizado[cont] + ":\n";
				line += "NÃO HÁ CLIENTE COM O CPF " + cpfOrganizado[cont] + "\n\n";
			}

			cont++;
		}

		gravarCpf.gravarArquivo(line.trim());
		gravarCpf.fecharArquivo();
	}

}
