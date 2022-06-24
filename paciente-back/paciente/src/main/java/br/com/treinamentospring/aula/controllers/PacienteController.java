package br.com.treinamentospring.aula.controllers;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import br.com.treinamentospring.aula.entities.Paciente;
import br.com.treinamentospring.aula.repositories.IPacienteRepository;
import br.com.treinamentospring.aula.requests.PacienteGetResponse;
import br.com.treinamentospring.aula.requests.PacientePostRequest;
import br.com.treinamentospring.aula.requests.PacientePutRequest;
import io.swagger.annotations.ApiOperation;

@Controller
@Transactional
public class PacienteController {
	
	@Autowired
	private IPacienteRepository pacientesRepository;
	
	//definido o endereço do serviço
	private static final String ENDPOINT = "/api/pacientes";
	
	//método para realizar o serviço de cadastro de paciente
	@ApiOperation("Serviço para cadastro de Paciente")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@CrossOrigin
	public ResponseEntity<String>post(@RequestBody PacientePostRequest request){
		try {
			Paciente p = new Paciente();
			p.setNome(request.getNome());
			p.setCpf(request.getCpf());
			p.setDataNascimento(request.getDataNascimento());
			p.setSexo(request.getSexo());
			
			pacientesRepository.save(p);
			
			return ResponseEntity.status(HttpStatus.OK).body("Paciente cadastrado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERRO: "+e.getMessage());
		}
	}
	
	//método para realizar o serviço de edição de paciente
	@ApiOperation("Serviço para atualização dos dados de um Paciente")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@CrossOrigin
	public ResponseEntity<String>put(@RequestBody PacientePutRequest request){
		try {
			//consultar o produto no banco de  dados através do ID
			Optional<Paciente> item = pacientesRepository.findById(request.getIdPaciente());
			
			//verifica se o produto não foi encontrado
			if(item.isEmpty()){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Paciente não encontrado, por favor verifique o código digitado");
			}else {
				/*método de salvar... cria uma instância do objeto do tipo
				 * paciente e recebe as informações*/
				Paciente p = item.get();
				p.setNome(request.getNome());
				p.setCpf(request.getCpf());
				p.setDataNascimento(request.getDataNascimento());
				p.setSexo(request.getSexo());
				
				pacientesRepository.save(p);
				return ResponseEntity.status(HttpStatus.OK)
						.body("Paciente atualizado com sucesso");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("ERRO: "+e.getMessage());
		}
	}
	//método para realizar o serviço de exclusão de paciente
	@ApiOperation("Serviço para exclusão de um Paciente")
	@RequestMapping(value = ENDPOINT + "/{idPaciente}", method = RequestMethod.DELETE)
	@CrossOrigin
	public ResponseEntity<String>delete(@PathVariable("idPaciente")Integer idPaciente){
		try {
			//consultar o produto no banco de dados através do ID
			Optional<Paciente> item = pacientesRepository.findById(idPaciente);
			//verificar se o produto não foi encontrado 
			if(item.isEmpty()){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Paciente não encontrado, por favor verifique o código digitado");
			}else {
				Paciente pac = item.get();
				pacientesRepository.delete(pac);
				return ResponseEntity.status(HttpStatus.OK)
						.body("Paciente excluído com sucesso!!!");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("ERROR: "+e.getMessage());
		}
	}
	//método para realizar o serviço de consulta de Paciente
	@ApiOperation("Serviço para consultar um paciente")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@CrossOrigin
	public ResponseEntity<List<PacienteGetResponse>>get(){
		//lista vaziapara receber produtos vindo do banco
		List<PacienteGetResponse> response = new ArrayList<PacienteGetResponse>();
		//enquanto existir paciente. . . .
		for(Paciente p: pacientesRepository.findAll()) {
			//objeto item instancia na memória
			PacienteGetResponse item = new PacienteGetResponse();
			//elemento de produto sendo "colocado" em item
			item.setIdPaciente(p.getIdPaciente());
			item.setNome(p.getNome());
			item.setCpf(p.getCpf());
			item.setDataNascimento(p.getDataNascimento());
			item.setSexo(p.getSexo());
			
			response.add(item);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	//método para consultar 1 Paciente baseado por ID
	    @ApiOperation("Serviço para consultar 1 Paciente através do ID")
		@RequestMapping(value = ENDPOINT +"/{idPaciente}", method = RequestMethod.GET)
	    @CrossOrigin
		public ResponseEntity<PacienteGetResponse> getById(@PathVariable("idPaciente")Integer idPaciente){
			//consultar o produto no banco de dados através do id
			Optional<Paciente> item = pacientesRepository.findById(idPaciente);
			
			//verificar se o Pacientenão foi encontrado
			if(item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}else {
				PacienteGetResponse response = new PacienteGetResponse();
				Paciente p = item.get();
				
				response.setIdPaciente(p.getIdPaciente());
				response.setNome(p.getNome());
				response.setCpf(p.getCpf());
				response.setDataNascimento(p.getDataNascimento());
				response.setSexo(p.getSexo());
				
				
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
}