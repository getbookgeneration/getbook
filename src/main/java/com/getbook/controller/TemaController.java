package com.getbook.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.getbook.model.Tema;
import com.getbook.repository.TemaRepository;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {

	@Autowired
	private TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.ok(temaRepository.findAll());

	}

	@GetMapping("/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {

		return temaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/genero/{genero}")
	public ResponseEntity<List<Tema>> getByGenero(@PathVariable String genero) {
		return ResponseEntity.ok(temaRepository.findAllByGeneroContainingIgnoreCase(genero));

	}

	@GetMapping("/categoria/{categoria}")
	public ResponseEntity<List<Tema>> getByCategoria(@PathVariable String categoria) {
		return ResponseEntity.ok(temaRepository.findAllByCategoriaContainingIgnoreCase(categoria));

	}

	@PostMapping
	public ResponseEntity<Tema> postTema(@Valid @RequestBody Tema tema) {
		return ResponseEntity.status(HttpStatus.CREATED).body(temaRepository.save(tema));

	}

	@PutMapping
	public ResponseEntity<Tema> putTema(@Valid @RequestBody Tema tema) {
		return temaRepository.findById(tema.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(temaRepository.save(tema)))
				.orElse(ResponseEntity.notFound().build());

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteTema(@PathVariable Long id) {
		Optional<Tema> recebeidTema = temaRepository.findById(id);

		if (recebeidTema.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		temaRepository.deleteById(id);

	}

}
