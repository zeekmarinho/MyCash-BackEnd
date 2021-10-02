package com.mycash.mycash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.mycash.mycash.model.Receita;
import com.mycash.mycash.repository.ReceitaRepository;

@CrossOrigin()
@RestController
@RequestMapping({"/receita"})
public class ReceitaController {
	
	@Autowired
	private ReceitaRepository repository;
	
//	http://localhost:8095/receita
	@GetMapping
	public List<?> findAllInvoices() {
		return repository.findAll();
	}
	
//	http://localhost:8095/receita/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id){
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
//	http://localhost:8095/despesa
	@PostMapping
	public Receita create(@RequestBody Receita receita) {
		return repository.save(receita);
	}
	
//	http://localhost:8095/despesa/{id}
	@PutMapping(value = "{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Receita receita) {
		return repository.findById(id)
				.map(record -> {
					record.setData(receita.getData());
					record.setDescricao(receita.getDescricao());
					record.setFixo(receita.isFixo());
					record.setTipo(receita.getTipo());
					record.setValor(receita.getValor());
					Receita update = repository.save(record);
					return ResponseEntity.ok().body(update);
				}).orElse(ResponseEntity.notFound().build());
	}
	
//	http://localhost:8095/despesa/{id}
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().body("Receita Deletada com Sucesso");
				}).orElse(ResponseEntity.notFound().build());
	}
}
