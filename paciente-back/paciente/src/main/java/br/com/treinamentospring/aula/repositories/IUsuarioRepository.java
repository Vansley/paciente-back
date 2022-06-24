package br.com.treinamentospring.aula.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.treinamentospring.aula.entities.Usuario;

public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	@Query("select u from Usuario u where u.login =:login")
	Usuario findByLogin(@Param("login") String login);
	
	//obter 1 usuário de dados 
	//através do login e senha
	@Query("select u from Usuario u where u.login = :login and u.senha = :senha")
	Usuario findByLoginAndSenha(@Param("login") String login, @Param("senha") String senha);

}
