package com.crejo.fun.movie.review.exception;

import org.springframework.stereotype.Component;

@Component
public class DuplicateReviewException extends Exception{
	
	private String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DuplicateReviewException() {
	}
	
	public DuplicateReviewException(String msg) {
		this.message = msg;
	}
}
