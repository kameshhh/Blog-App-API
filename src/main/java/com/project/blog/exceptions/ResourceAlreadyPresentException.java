package com.project.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceAlreadyPresentException extends RuntimeException{
	String resourceName;
	String fieldName;
	String fieldValue;
	public ResourceAlreadyPresentException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s is already present with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
}
