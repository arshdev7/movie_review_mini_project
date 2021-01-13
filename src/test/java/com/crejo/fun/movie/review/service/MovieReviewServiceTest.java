package com.crejo.fun.movie.review.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.crejo.fun.movie.review.entity.Genre;
import com.crejo.fun.movie.review.entity.Movie;
import com.crejo.fun.movie.review.entity.User;
import com.crejo.fun.movie.review.entity.UserType;

@SpringBootTest
public class MovieReviewServiceTest {

	@Autowired
	private MovieReviewService service;

	@BeforeEach
	public void applicationTest(){
		service.addMovie(
				new Movie("Don", Year.of(2006), new HashSet<Genre>(Arrays.asList(Genre.ACTION, Genre.COMEDY))));
		service.addMovie(new Movie("Tiger", Year.of(2008), new HashSet<Genre>(Arrays.asList(Genre.DRAMA))));
		service.addMovie(new Movie("Padmaavat", Year.of(2006), new HashSet<Genre>(Arrays.asList(Genre.COMEDY))));
		service.addMovie(new Movie("LunchBox", Year.of(2021), new HashSet<Genre>(Arrays.asList(Genre.DRAMA))));
		service.addMovie(new Movie("Guru", Year.of(2006), new HashSet<Genre>(Arrays.asList(Genre.DRAMA))));
		service.addMovie(new Movie("Metro", Year.of(2006), new HashSet<Genre>(Arrays.asList(Genre.ROMANCE))));

		service.addUser(new User("SRK"));
		service.addUser(new User("Salman"));
		service.addUser(new User("Deepika"));

		service.addReview("SRK", "Don", 2);
		service.addReview("SRK", "Padmaavat", 8);
		service.addReview("Salman", "Don", 5);
		service.addReview("Deepika", "Don", 9);
		service.addReview("Deepika", "Guru", 6);
		service.addReview("SRK", "Tiger", 5);
		service.addReview("SRK", "Metro", 7);

	}
	
	@Test
	public void printsExceptionMessages() {
		service.addReview("SRK", "Don", 10);
		service.addReview("Deepika", "Lunchbox", 5);
		assertNotNull(service);
	}

	@Test
	public void topMovieInYear2006() {
		assertEquals("Don - 16 ratings", service.topMovieByReviewScoreInYear(Year.of(2006)));
	}

	@Test
	public void averageScoreOfMoviesIn2006() {
		assertEquals(11.0, service.averageScoreOfMoviesByYear(Year.of(2006)));
	}

	@Test
	public void topMovieByReviewScoreInDrama() {
		assertEquals("Guru - 6 ratings", service.topMovieByReviewScoreUsingGenre(Genre.DRAMA));
	}
	
	@Test
	public void topMoviesByReview() {
		Set<Movie> movies = service.topNMoviesByReviewScore(10, UserType.VIEWER, Year.of(2006));
		movies.forEach(System.out::println);
		assertNotNull(movies);
		
	}
}
