package com.inventory.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.inventory.dto.InventoryDto;
import com.inventory.entity.Inventory;
import com.inventory.exception.ResourceNotFoundException;
import com.inventory.mapper.InventoryMapper;
import com.inventory.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {

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
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("inventory does not exists"));
		return InventoryMapper.mapToInventoryDto(inventory);
	}

	@Override
	public List<InventoryDto> getAllInventory() {
		List<Inventory> allInventory = inventoryRepository.findAll();
		return allInventory.stream().map((inventory) -> InventoryMapper.mapToInventoryDto(inventory))
				.collect(Collectors.toList());
	}

	@Override
	public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Inventory does not exists"));

		inventory.setAvailableQuantity(inventoryDto.availableQuantity());
		inventory.setReservedQuantity(inventoryDto.reservedQuantity());
		inventory.setUpdatedAt(LocalDateTime.now());

		Inventory updatedInventory = inventoryRepository.save(inventory);
		return InventoryMapper.mapToInventoryDto(updatedInventory);
	}

	@Override
	public void deleteInventory(Long id) {
		if (!inventoryRepository.existsById(id)) {
			throw new ResourceNotFoundException("Inventory does not exists");
		}
		inventoryRepository.deleteById(id);
	}

	@Override
	public void reserveStock(Long productId, Integer quantity) {
		Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product: " + productId));
		
		if (inventory.getAvailableQuantity() < quantity) {
			throw new RuntimeException("Insufficient stock for produt: " + productId);
		}

		inventory.setAvailableQuantity(inventory.getAvailableQuantity() - quantity);

		inventory.setReservedQuantity(inventory.getReservedQuantity() + quantity);

		inventory.setUpdatedAt(LocalDateTime.now());

		inventoryRepository.save(inventory);
	}
}
