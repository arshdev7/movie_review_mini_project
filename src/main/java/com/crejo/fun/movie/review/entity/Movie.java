package com.crejo.fun.movie.review.entity;

import java.time.Year;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {
	private String movieName;
	private Year releasedYear;
	private Set<Genre> genres;
}
