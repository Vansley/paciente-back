package br.com.treinamentospring.aula.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.treinamentospring.aula.entities.Usuario;
import br.com.treinamentospring.aula.repositories.IUsuarioRepository;
import br.com.treinamentospring.aula.requests.LoginPostRequest;
import br.com.treinamentospring.security.MD5Cryptography;
import br.com.treinamentospring.security.TokenSecurity;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class LoginController {	

	@Autowired
	private IUsuarioRepository usuarioRepository;

	private static final String ENDPOINT = "/api/login";

	@ApiOperation("Serviço para autenticação de usuário.")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String> post(@RequestBody LoginPostRequest request) {

		try {
			
			//pesquisar no banco de dados o usuario atraves do login e senha
			Usuario usuario = usuarioRepository.findByLoginAndSenha
					(request.getLogin(), MD5Cryptography.encrypt(request.getSenha()));
			
			//verificar se o usuario foi encontrado
			if(usuario != null) {
				
				return ResponseEntity.status(HttpStatus.OK)
						.body(TokenSecurity.generateToken(usuario.getLogin()));
			}
			else {
				
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("Acesso negado.");
			}
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
	}
}
