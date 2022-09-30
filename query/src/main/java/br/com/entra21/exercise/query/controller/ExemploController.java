package br.com.entra21.exercise.query.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.entra21.exercise.query.model.Exemplo;
import br.com.entra21.exercise.query.model.ItemNivel3;
import br.com.entra21.exercise.query.repository.IExemploRepository;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/exemplos")
public class ExemploController {

	@Autowired // Instanciar a variavel no mesmo tempo que a classe controller é inicializada
	private IExemploRepository exemploRepository;

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<Exemplo> listAll() {

		List<Exemplo> response = exemploRepository.findAll();
		response.forEach(exemplo ->{
			setMaturidadeNivel3(exemplo);
		});
		
		return response;
	}
	
//	@GetMapping()
//	@ResponseStatus(HttpStatus.OK)
//	public List<Exemplo> listAll() {
//
//		return exemploRepository.findAll();
//	}

	@GetMapping(value = "/byage/{age}")
	public List<Exemplo> getByAge(@PathVariable("age") Integer age) {

		return exemploRepository.findByAge(age);
	}
	
	@GetMapping(value = "/nomeidade/{nome}/{idade}")
	public List<Exemplo> getByAgeName(@PathVariable("nome") String nome, @PathVariable("idade") Integer idade) {
		
		return exemploRepository.findByFirstNameAndAge(nome, idade);
	}
	
	@GetMapping(value = "/menoridadeigual/{idade}")
	public List<Exemplo> getByAgeLessThanEqual(@PathVariable("idade") Integer idade) {
		
		return exemploRepository.findByAgeLessThanEqual(idade);
	}
	
	@GetMapping(value = "/comecacom/{prefixo}")
	public List<Exemplo> getStartWith(@PathVariable("prefixo") String prefixo) {
		
		return exemploRepository.findByLastNameStartingWith(prefixo);
	}
	
	
	
	
	
	
	

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Exemplo add(@RequestBody Exemplo newExemplo) {

		return exemploRepository.save(newExemplo);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Exemplo> search(@PathVariable("id") int param) {

		List<Exemplo> response = exemploRepository.findById(param).stream().toList();

		return response;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody Optional<Exemplo> update(@PathVariable("id") int param, @RequestBody Exemplo newDataExemplo) {

		Exemplo current = exemploRepository.findById(param).get();
		current.setFirstName(newDataExemplo.getFirstName());
		current.setLastName(newDataExemplo.getLastName());
		current.setAge(newDataExemplo.getAge());
		current.setActive(newDataExemplo.getActive());

		exemploRepository.save(current);

		return exemploRepository.findById(param);
	}


	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody boolean delete(@PathVariable("id") int id) {
		exemploRepository.deleteById(id);

		return !exemploRepository.existsById(id);
	}

	private void setMaturidadeNivel3(Exemplo exemplo) {

		final String PATH = "localhost:8080/exemplo";

		exemplo.setLinks(new ArrayList<>());

		exemplo.getLinks().add(new ItemNivel3("GET", PATH));

		exemplo.getLinks().add(new ItemNivel3("GET", PATH + "/" + exemplo.getId()));

		exemplo.getLinks().add(new ItemNivel3("DELETE", PATH + "/" + exemplo.getId()));

		exemplo.getLinks().add(new ItemNivel3("POST", PATH));

		exemplo.getLinks().add(new ItemNivel3("PUT", PATH + "/" + exemplo.getId()));

	}

}
