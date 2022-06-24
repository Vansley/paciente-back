package br.com.treinamentospring.aula.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.treinamentospring.aula.entities.Paciente;


@Repository
public interface IPacienteRepository extends CrudRepository<Paciente, Integer>{

}
