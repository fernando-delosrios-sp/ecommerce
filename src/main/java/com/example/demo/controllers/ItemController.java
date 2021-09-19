package com.example.demo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

@Transactional
@RestController
@RequestMapping("/api/item")
public class ItemController {
	private static final Logger log = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		try {
			return ResponseEntity.ok(itemRepository.findAll());
		} catch (Exception e) {
			log.error("Exception found.", e);
			throw e;
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		try {
			return ResponseEntity.of(itemRepository.findById(id));
		} catch (Exception e) {
			log.error("Exception found.", e);
			throw e;
		}
		
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		try {
			List<Item> items = itemRepository.findByName(name);
			return items == null || items.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(items);
		} catch (Exception e) {
			log.error("Exception found.", e);
			throw e;
		}
	}
}
