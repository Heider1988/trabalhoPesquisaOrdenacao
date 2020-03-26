package arvore_avl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GravarArquivo {

	// // objeto que representara o �gravador� de caracteres.
	private FileWriter writer;

	// objeto que possibilita escrever Strings no arquivo
	// utilizando os m�todos print() e println().
	private PrintWriter saida;

	public GravarArquivo(String nome) throws IOException {
		try {

			// false significa que se o arquivo ja existir, ele sera
			// sobrescrito caso queira apenas acrescentar dados ao final
			// do arquivo, deve usar true. Se o arquivo nao existir, cria um.
			this.writer = new FileWriter(new File(nome), false);

			// esse objeto significa que significando que 0 arquivo poder�
			// sofrer inclus�o de dados. O segundo argumento (opcional) indica
			// (true) que os dados ser�o enviados para o arquivo a toda
			// chamada do m�todo println(), caso contr�rio, os dados s� s�o
			// enviados quando voce enviar uma quebra de linha, fechar o
			// arquivo ou mandar ele atualizar as mudan�as (modo autoflush).
			this.saida = new PrintWriter(writer);

		} catch (IOException e) {
			throw new IOException("ARQUIVO NAO PODE SER ABERTO PARA" + " GRAVACAO");

		}
	}

	// Este metodo grava uma String qualquer em um arquivo tipo texto
	// str => String a ser gravada no arquivo
	public void gravarArquivo(String str) {
		this.saida.println(str);
	}

	public void fecharArquivo() throws IOException {
		try {
			this.writer.close();
			this.saida.close();
		} catch (IOException e) {
			throw new IOException("ERRO AO FECHAR O ARQUIVO");
		}
	}

}
