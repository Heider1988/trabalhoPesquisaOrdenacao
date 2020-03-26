package dados;

public class Cliente {

	private int agencia;
	private String numConta;
	private double saldo;
	private String cpfTitular;

	public Cliente(int agencia, String numConta, double saldo, String cpfTitular) {
		this.agencia = agencia;
		this.numConta = numConta;
		this.saldo = saldo;
		this.cpfTitular = cpfTitular;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public String getNumConta() {
		return numConta;
	}

	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getCpfTitular() {
		return cpfTitular;
	}

	public void setCpfTitular(String cpfTitular) {
		this.cpfTitular = cpfTitular;
	}

	public long compareTo(Cliente cli) {
		long cpf = Long.parseLong(this.cpfTitular);
		long cpf2 = Long.parseLong(cli.cpfTitular);
		if (cpf < cpf2) {
			return -1;
		} else if (cpf > cpf2)
			return 1;
		else if (Integer.parseInt(this.numConta) < Integer.parseInt(cli.numConta))
			return -1;
		else if (Integer.parseInt(this.numConta) > Integer.parseInt(cli.numConta))
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "Cliente [agencia=" + agencia + ", numConta=" + numConta + ", saldo=" + saldo + ", cpfTitular="
				+ cpfTitular + "]";
	}

	public long chaveCpf() {
		long chave = Long.parseLong(this.cpfTitular);
		return chave;
	}
	
	public long numContaLong() {
		long numConta = Long.parseLong(this.numConta);
		return numConta;
	}

}
