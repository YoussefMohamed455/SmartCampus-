package com.SmartCampus.org.service;

import com.SmartCampus.org.Entity.CanteenItem;
import com.SmartCampus.org.dto.CanteenItemDTO;
import com.SmartCampus.org.mapper.CanteenItemMapper;
import com.SmartCampus.org.repositories.CanteenItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanteenItemServiceImpl implements CanteenItemService {

    private final CanteenItemRepository itemRepository;
    private final CanteenItemMapper itemMapper;

    @Autowired
    public CanteenItemServiceImpl(CanteenItemRepository itemRepository, CanteenItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public CanteenItemDTO addItem(CanteenItemDTO dto) {
        CanteenItem item = itemMapper.toEntity(dto);
        if (item.getIsAvailable() == null) {
            item.setIsAvailable(true); // Default to available
        }
        return itemMapper.toDTO(itemRepository.save(item));
    }

    @Override
    public List<CanteenItemDTO> getMenu() {
        return itemRepository.findByIsAvailableTrue().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CanteenItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CanteenItemDTO updateAvailability(Long id, Boolean isAvailable) {
        CanteenItem item = itemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));

        item.setIsAvailable(isAvailable);
        return itemMapper.toDTO(itemRepository.save(item));
    }
}