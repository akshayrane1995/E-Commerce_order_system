package com.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.InventoryDto;
import com.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	private InventoryService inventoryService;
	
	@PostMapping("/create")
	public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto){
		InventoryDto save = inventoryService.createInventory(inventoryDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<InventoryDto> getInventoryById(@PathVariable Long id){
		InventoryDto inventoryDto  = inventoryService.getInventoryById(id);
		return ResponseEntity.ok(inventoryDto);
	}
	
	@GetMapping()
	public ResponseEntity<List<InventoryDto>> getAllInventory(){
		List<InventoryDto> allInventory  = inventoryService.getAllInventory();
		return ResponseEntity.ok(allInventory);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<InventoryDto> updateInventory(@PathVariable Long id, @RequestBody InventoryDto inventoryDto){
		InventoryDto updatedInventory = inventoryService.updateInventory(id,inventoryDto);
		return ResponseEntity.ok(updatedInventory);
	}
	
	@DeleteMapping("/{id}/remove")
	public ResponseEntity<String> deleteInventory(@PathVariable Long id){
		inventoryService.deleteInventory(id);
		return ResponseEntity.ok("Inventory is deleted sucessfully");
	}
}
