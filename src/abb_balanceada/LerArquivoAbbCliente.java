package abb_balanceada;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dados.Cliente;

public class LerArquivoAbbCliente {

	// objeto do tipo Scanner para realizar a leitura dos dados do arquivo.
	private Scanner entrada;

	public LerArquivoAbbCliente(String nome) throws FileNotFoundException {

		try {

			// Instanciamento do objeto do tipo Scanner, tendo como argumento File que será
			// o arquivo que será lido
			this.entrada = new Scanner(new File(nome));

		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("ARQUIVO NAO ENCONTRADO");
		}

	}

	// Metodo para ler os dados contidos no arquivo
	public Arvore lerArquivo(int tamanho) throws NoSuchElementException, ArrayIndexOutOfBoundsException {
		Arvore cadastro = new Arvore();
		String linha;

		try {
			// indica se ainda existe uma String para ser lida.
			while (this.entrada.hasNext()) {

				linha = this.entrada.nextLine();

				cadastro.inserirNoArv(separarDados(linha.trim()));

			}

			return cadastro;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Arquivo corrompido");
		}
	}

	// Metodo para transformar uma linha do arquivo em um objeto do tipo Cliente
	private Cliente separarDados(String linha) {
		String[] dados;
		int agencia;
		String numConta;
		String cpfTitular;
		double saldo;

		try {
			// O método split quebra uma String em várias substrings a partir do caracter
			// definido,
			// cria um vetor de String e armazena cada substring em um posicao
			dados = linha.split(";");

			agencia = Integer.parseInt(dados[0].trim());
			numConta = dados[1].trim();
			saldo = Double.parseDouble(dados[2].trim());
			cpfTitular = dados[3].trim();

			return (new Cliente(agencia, numConta, saldo, cpfTitular));

		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("ARQUIVO DIFERENTE DO REGISTRO");
		}

	}

	public void fecharArquivo() throws IllegalStateException {
		try {
			this.entrada.close();
		} catch (IllegalStateException e) {
			throw new IllegalStateException("ERRO AO FECHAR O ARQUIVO");
		}
	}

}
