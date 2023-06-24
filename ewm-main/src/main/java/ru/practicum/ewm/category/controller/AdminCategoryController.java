package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.Create;
import ru.practicum.ewm.category.Update;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final String path = "/{catId}";

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto createCategory(@Validated({Create.class}) @RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path)
    public void deleteCategoryById(@PathVariable("catId") @Positive Long catId) {
        categoryService.deleteCategoryById(catId);
    }

    @PatchMapping(path)
    public CategoryDto updateCategoryById(@PathVariable("catId") @Positive Long categoryId,
                                          @Validated({Update.class}) @RequestBody NewCategoryDto newCategoryDto) {
        return categoryService.updateCategoryById(categoryId, newCategoryDto);
    }
}