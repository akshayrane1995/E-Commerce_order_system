package com.inventory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.inventory.dto.InventoryDto;
import com.inventory.entity.Inventory;
import com.inventory.mapper.InventoryMapper;
import com.inventory.repository.InventoryRepository;

public class InventoryServiceImpl implements InventoryService{

	private InventoryRepository inventoryRepository;
	
	public InventoryServiceImpl(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}
	
	
	@Override
	public InventoryDto createInventory(InventoryDto inventoryDto) {
		Inventory inventory = InventoryMapper.mapToInventory(inventoryDto);
		LocalDateTime now = LocalDateTime.now();
		inventory.setCreatedAt(now);
		inventory.setUpdatedAt(now);
		Inventory save = inventoryRepository.save(inventory);
		return InventoryMapper.mapToInventoryDto(save);
	}


	@Override
	public InventoryDto getInventoryById(Long id) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("inventory does not exists"));
		return InventoryMapper.mapToInventoryDto(inventory);
	}


	@Override
	public List<InventoryDto> getAllInventory() {
		List<Inventory> allInventory = inventoryRepository.findAll();
		return allInventory.stream().map((inventory) -> InventoryMapper.mapToInventoryDto(inventory)).collect(Collectors.toList());
	}


	@Override
	public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) {
		Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory does not exists"));
		
		inventory.setAvailableQuantity(inventoryDto.availableQuantity());
		inventory.setReservedQuantity(inventoryDto.reservedQuantity());
		inventory.setUpdatedAt(LocalDateTime.now());
		
		Inventory updatedInventory = inventoryRepository.save(inventory);
		return InventoryMapper.mapToInventoryDto(updatedInventory);
	}


	@Override
	public void deleteInventory(Long id) {
		if(!inventoryRepository.existsById(id)){
			throw new RuntimeException("Inventory does not exists");
		}
			inventoryRepository.deleteById(id);
	}


	

}
