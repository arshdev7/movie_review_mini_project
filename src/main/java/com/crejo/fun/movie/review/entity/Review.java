package com.crejo.fun.movie.review.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Review {
	private User user;
	private Movie movie;
	private int rating;
}
