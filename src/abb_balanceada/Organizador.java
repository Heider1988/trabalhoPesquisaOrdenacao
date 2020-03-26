package abb_balanceada;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Organizador {

	public void ordernaArquivos() throws IOException {

		LerArquivoAbbCliente ler = null;
		Arvore cliente = null;
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

					ler = new LerArquivoAbbCliente(nomeArquivo);

					cliente = ler.lerArquivo(tamanho(nomeArquivo));

					cliente.arvoreBalanceada();

					cliente.compararCPFS(tamanho, tipoArquivo);

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

		gravar = new GravarArquivo("./src/relatorios/relatorio_ABB.txt");
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

}
