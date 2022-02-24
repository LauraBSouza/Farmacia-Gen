package com.generation.farmacia.controller;

import java.math.BigDecimal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.generation.farmacia.model.Produto;
import com.generation.farmacia.repository.CategoriaRepository;
import com.generation.farmacia.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/nome/{nome}")
	public List<Produto> getByNome(@PathVariable String nome) {
		return produtoRepository.findAllByNomeContainingIgnoreCase(nome);

	}

	@PostMapping
	public ResponseEntity<Produto> postProduto(@Valid @RequestBody Produto produto) {
		if (categoriaRepository.existsById(produto.getCategoria().getId())) {
				return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
		}
		return ResponseEntity.notFound().build();

	}

	@PutMapping
	public ResponseEntity<Produto> putProduto(@Valid @RequestBody Produto produto) {
		if (produtoRepository.existsById(produto.getId())) {
			if (categoriaRepository.existsById(produto.getCategoria().getId())) {
				return ResponseEntity.ok(produtoRepository.save(produto));

			}
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		return produtoRepository.findById(id).map(resposta -> {
			produtoRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/nome/{nome}/laboratorio/{laboratorio}")
	public ResponseEntity<List<Produto>> getByNomeAndlaboratorio(@PathVariable String nome, @PathVariable String laboratorio) {
		return (ResponseEntity.ok(produtoRepository.findAllByNomeAndLaboratorioContainingIgnoreCase(nome, laboratorio)));	
	}
	
	@GetMapping("/preco_menor/{preco1}/preco_maior/{preco2}")
	public ResponseEntity<List<Produto>> getByPrecoBetWeenPreco1AndPreco2(@PathVariable BigDecimal preco1, @PathVariable BigDecimal preco2) {
		return (ResponseEntity.ok(produtoRepository.findAllByPrecoBetween(preco1, preco2)));
	}
}
