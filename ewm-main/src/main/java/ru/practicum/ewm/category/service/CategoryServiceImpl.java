package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.category.dao.CategoryRepository;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dao.EventRepository;
import ru.practicum.ewm.exceptions.ConflictException;
import ru.practicum.ewm.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.category.CategoryMapper.toCategory;
import static ru.practicum.ewm.category.CategoryMapper.toCategoryDto;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final String categoryMessage = "Category with id=%s was not found";

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = toCategory(newCategoryDto);

        return toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(Long catId) {
        categoryRepository.findById(catId).orElseThrow(() -> {
            throw new ObjectNotFoundException(String.format(categoryMessage, catId));
        });

        if (!eventRepository.findAllByCategoryId(catId).isEmpty()) {
            throw new ConflictException("The category is not empty");
        }

        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategoryById(Long catId, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            throw new ObjectNotFoundException(String.format(categoryMessage, catId));
        });

        category.setName(newCategoryDto.getName());

        return toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);

        return categoryRepository.findAll(pageable).stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long catId) {

        Category category = categoryRepository.findById(catId).orElseThrow(() -> {
            throw new ObjectNotFoundException(String.format(categoryMessage, catId));
        });

        return toCategoryDto(category);
    }
}
