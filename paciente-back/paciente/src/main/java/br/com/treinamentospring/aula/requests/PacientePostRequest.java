package br.com.treinamentospring.aula.requests;


public class PacientePostRequest {

	private Integer idPaciente;
	private String nome;
	private String cpf;
	private String dataNascimento;
	private String sexo;
	
public PacientePostRequest() {
	// TODO Auto-generated constructor stub
}

public Integer getIdPaciente() {
	return idPaciente;
}

public void setIdPaciente(Integer idPaciente) {
	this.idPaciente = idPaciente;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

public String getCpf() {
	return cpf;
}

public void setCpf(String cpf) {
	this.cpf = cpf;
}

public String getDataNascimento() {
	return dataNascimento;
}

public void setDataNascimento(String dataNascimento) {
	this.dataNascimento = dataNascimento;
}

public String getSexo() {
	return sexo;
}

public void setSexo(String sexo) {
	this.sexo = sexo;
}


	
}