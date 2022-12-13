package com.getbook.controller;

import com.getbook.model.Postagem;

import com.getbook.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postagem")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());

	}

	@GetMapping("/titulo/{tituloLivro}")
	public ResponseEntity<List<Postagem>> getTitulo(@PathVariable String tituloLivro) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloLivroContainingIgnoreCase(tituloLivro));
	}

	@PostMapping
	public ResponseEntity<Postagem> getPostagem(@Valid @RequestBody Postagem postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}

	@PutMapping
	public ResponseEntity<Postagem> updatePostagem(@Valid @RequestBody Postagem postagem) {
		if (postagem.getId() == null) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> buscaId(@PathVariable Long id) {
		Optional<Postagem> buscaId = postagemRepository.findById(id);
		return buscaId.map(resposta -> ResponseEntity.ok(resposta)).orElse(ResponseEntity.notFound().build());

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Postagem> deletePostagem(@PathVariable Long id) {
		try {
			postagemRepository.deleteById(id);
			return ResponseEntity.status(204).build();

		} catch (Exception e) {
			return ResponseEntity.notFound().build();

		}
	}

}
