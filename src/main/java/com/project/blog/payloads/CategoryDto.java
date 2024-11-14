package com.project.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank(message = "Category title is required")
	@Size(min = 3, message = "Category title must be minimun 3 characters")
	private String categoryTitle;
	
	@NotBlank(message = "Category description is required")
	@Size(min = 10, message = "Category description must be minimun 10 characters")
	private String categoryDesc;
	
}
