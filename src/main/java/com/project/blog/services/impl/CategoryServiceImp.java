package com.project.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.blog.entities.Category;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.CategoryDto;
import com.project.blog.repo.CategoryRepo;
import com.project.blog.services.CategoryService;

public class CategoryServiceImp implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category newCategory = this.categoryRepo.save(category);
		return this.categoryToCategoryDto(newCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		category.setCategoryDesc(categoryDto.getCategoryDesc());
		category.setCategoryTitle(categoryDto.getCategoryTitle());

		Category updatedCategory = this.categoryRepo.save(category);
		return this.categoryToCategoryDto(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		this.categoryRepo.delete(category);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		return this.categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> allCategories = this.categoryRepo.findAll();
		List<CategoryDto> allCategoriesDto = allCategories.stream().map(c -> this.categoryToCategoryDto(c)).collect(Collectors.toList());
		return allCategoriesDto;
	}

	public Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}

	public CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
