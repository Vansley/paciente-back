package br.com.treinamentospring.aula.requests;


public class LoginPostRequest {
	
	private String login;
	private String senha;
	
public LoginPostRequest() {
	// TODO Auto-generated constructor stub
}

public String getLogin() {
	return login;
}

public void setLogin(String login) {
	this.login = login;
}

public String getSenha() {
	return senha;
}

public void setSenha(String senha) {
	this.senha = senha;
}

}
