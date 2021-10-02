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

import com.mycash.mycash.model.Despesa;
import com.mycash.mycash.repository.DespesaRepository;

@CrossOrigin()
@RestController
@RequestMapping({"/despesa"})
public class DespesaController {
	
	@Autowired
	private DespesaRepository repository;
	
//	http://localhost:8095/despesa
	@GetMapping
	public List<?> findAllInvoices() {
		return repository.findAll();
	}
	
//	http://localhost:8095/despesa/{id}
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id){
		return repository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
//	http://localhost:8095/despesa
	@PostMapping
	public Despesa create(@RequestBody Despesa despesa) {
		return repository.save(despesa);
	}
	
//	http://localhost:8095/despesa/{id}
	@PutMapping(value = "{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Despesa despesa) {
		return repository.findById(id)
				.map(record -> {
					record.setData(despesa.getData());
					record.setDescricao(despesa.getDescricao());
					record.setFixo(despesa.isFixo());
					record.setTipo(despesa.getTipo());
					record.setValor(despesa.getValor());
					Despesa update = repository.save(record);
					return ResponseEntity.ok().body(update);
				}).orElse(ResponseEntity.notFound().build());
	}
	
//	http://localhost:8095/despesa/{id}
	@DeleteMapping(path = "{id}")
	public ResponseEntity<?> delete(@PathVariable long id){
		return repository.findById(id)
				.map(record -> {
					repository.deleteById(id);
					return ResponseEntity.ok().body("Despesa Deletada com Sucesso");
				}).orElse(ResponseEntity.notFound().build());
	}

}
