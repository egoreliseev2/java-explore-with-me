package ru.practicum.ewm.categories.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.categories.dto.CategoryDto;
import ru.practicum.ewm.categories.mapper.CategoryMapper;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.categories.repository.CategoryRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.CATEGORY_MAPPER.toCategory(categoryDto));
        log.info("Category created with id {}", category.getId());
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(category);
    }

    @Transactional
    public CategoryDto update(long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            throw new ObjectNotFoundException("Category not found");
        });
        if (categoryDto.getName().equals(category.getName())) {
            throw new ConflictException("Same category name");
        }
        category.setName(categoryDto.getName());
        log.info("Category with id {} updated", catId);
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    public void delete(long catId) {
        categoryRepository.findById(catId).orElseThrow(() -> {
            throw new ObjectNotFoundException("Category not found");
        });
        categoryRepository.deleteById(catId);
        log.info("Category with id {} deleted", catId);
    }

    public List<CategoryDto> findAll(Pageable pageable) {
        log.info("Categories sent");
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper.CATEGORY_MAPPER::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto findById(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            log.warn("Category not found");
            throw new ObjectNotFoundException("Category not found");
        });
        log.info("Category with id {} sent", catId);
        return CategoryMapper.CATEGORY_MAPPER.toCategoryDto(category);
    }
}
