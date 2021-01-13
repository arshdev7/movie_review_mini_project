package com.crejo.fun.movie.review.service;

import java.time.Year;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crejo.fun.movie.review.dao.MovieReviewRepository;
import com.crejo.fun.movie.review.entity.Genre;
import com.crejo.fun.movie.review.entity.Movie;
import com.crejo.fun.movie.review.entity.Review;
import com.crejo.fun.movie.review.entity.User;
import com.crejo.fun.movie.review.entity.UserType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovieReviewService {
	private MovieReviewRepository repo;
	
	@Autowired
	public MovieReviewService(MovieReviewRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Covers requirement:-
	 * 	- 1. Adding users and movies.
	 * @param movie
	 * @return
	 */
	public boolean addMovie(Movie movie) {
		return repo.addMovie(movie);
	}
	
	/**
	 * Covers requirement:-
	 * 	- 1. Adding users and movies.
	 * @param user
	 * @return
	 */
	public boolean addUser(User user) {
		return repo.addUser(user);
	}
	
	/**
	 * Covers requirement:
	 *  - 2. User to review a movie.
	 * @param userName
	 * @param movieName
	 * @param rating
	 * @return
	 */
	public boolean addReview(String userName, String movieName, int rating){
		User user = getUserByUserName(userName);
		Movie movie = getMovieByMovieName(movieName);
		Review review = new Review(user, movie, rating);
		try {
			return repo.addReview(review);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return false;
	}
	
	private Movie getMovieByMovieName(String movieName) {
		Optional<Movie> movieOp = repo.getMovies().stream()
				.filter(m -> m.getMovieName().equalsIgnoreCase(movieName))
				.findFirst();
		return movieOp.isPresent() ? movieOp.get() : null;
	}

	private User getUserByUserName(String userName) {
		Optional<User> userOp = repo.getUsers().stream()
				.filter(m -> m.getUserName().equalsIgnoreCase(userName))
				.findFirst();
		return userOp.isPresent() 
				? new User(userOp.get().getUserName(), userOp.get().getReviewCount(), userOp.get().getUserType()) 
						: null;
	}

	public String topMovieByReviewScoreUsingGenre(Genre genre) {
		Set<Movie> movieSet = repo.getMovies().stream().filter(movie -> movie.getGenres().contains(genre))
				.collect(Collectors.toSet());
		Map<String, Integer> ratingsMap = getMapByMovieRatings(movieSet);

		Map.Entry<String, Integer> maxEntry = null;

		for (Map.Entry<String, Integer> entry : ratingsMap.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		
		return constructMovieRatingString(maxEntry);
	}
	
	/**
	 * Covers requirement:-
	 *  - 7. Average review score i n a particular year of release.
	 * @param year
	 * @return
	 */
	public float averageScoreOfMoviesByYear(Year year) {
		Set<Movie> movieSet = repo.getMovies().stream().filter(movie -> movie.getReleasedYear().equals(year))
				.collect(Collectors.toSet());
		return getAverageRatingOfMovies(movieSet);
	}
	
	/**
	 * Covers requirement:-
	 *  - 8. Average review score in a particular genre.
	 * @param year
	 * @return
	 */
	public float averageScoreOfMoviesByGenre(Genre genre) {
		Set<Movie> movieSet = repo.getMovies().stream().filter(movie -> movie.getGenres().contains(genre))
				.collect(Collectors.toSet());
		return getAverageRatingOfMovies(movieSet);
	}
	
	/**
	 * Covers requirement:-
	 *  - 9. Average review score for a particular movie.
	 * @param year
	 * @return
	 */
	public float averageScoreOfMoviesByParticularMovie(String movieName) {
		Set<Movie> movieSet = repo.getMovies().stream().filter(movie -> movie.getMovieName().equalsIgnoreCase(movieName))
				.collect(Collectors.toSet());
		return getAverageRatingOfMovies(movieSet);
	}
	
	private float getAverageRatingOfMovies(Set<Movie> movieSet) {
		Map<String, Integer> ratingsMap = getMapByMovieRatings(movieSet);

		float average = 0f;
		float sum = 0f;
		for (Map.Entry<String, Integer> entry : ratingsMap.entrySet()) {
			sum += entry.getValue();
		}
		average = sum / ratingsMap.size();
		return average;
	}

	public String topMovieByReviewScoreInYear(Year year) {
		Set<Movie> movieSet = repo.getMovies().stream().filter(movie -> movie.getReleasedYear().equals(year))
				.collect(Collectors.toSet());
		Map<String, Integer> ratingsMap = getMapByMovieRatings(movieSet);
		Map.Entry<String, Integer> maxEntry = null;

		for (Map.Entry<String, Integer> entry : ratingsMap.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		
		return constructMovieRatingString(maxEntry);
	}

	private Map<String, Integer> getMapByMovieRatings(Set<Movie> movieSet) {
		 Map<String, Integer> ratingsMap = new HashMap<>();
			movieSet.forEach(m -> ratingsMap.put(m.getMovieName(), 0));

			repo.getReviews().stream().forEach(r -> {
				if (ratingsMap.containsKey(r.getMovie().getMovieName())) {
					ratingsMap.put(r.getMovie().getMovieName(), ratingsMap.get(r.getMovie().getMovieName()) + r.getRating());
				}
			});
		return ratingsMap;
	}

	private String constructMovieRatingString(Entry<String, Integer> maxEntry) {
		return  maxEntry.getKey() + " - " + maxEntry.getValue() + " ratings";
	}
	
	/**
	 * Givers Top N movies by review score using userType and Movie release year
	 * Covers requirements:-
	 * 	- 3. List top n movies by total review score by ‘ viewer’ i n a particular year of release.
	 *  - 5. List top n movies by total review score by ‘ critics’ i n a particular year of release.
	 * @param number
	 * @param userType
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	public Set<Movie> topNMoviesByReviewScore(int number, UserType userType, Year year){
		Set<Review> filteredMovies = repo.getReviews().stream()
				.filter(review -> review.getMovie().getReleasedYear().equals(year))
				.filter(review -> review.getUser().getUserType().equals(userType))
				.sorted(Comparator.comparing(Review::getRating, Comparator.reverseOrder()))
				.collect(Collectors.toSet());

		Set<Movie> linkedMovieSet = new LinkedHashSet<Movie>();
		filteredMovies.forEach(r -> linkedMovieSet.add(r.getMovie()));
		
		//If size of movies is greater then 'number' and delete from end
		if(!filteredMovies.isEmpty() && number<filteredMovies.size()) {
			int diff = filteredMovies.size() - number;
			for(int i=0; i<diff; i++) {
				Movie toRemove = (Movie) filteredMovies.toArray()[filteredMovies.size()-1];
				filteredMovies.remove(toRemove);
			}
		}
		
		return linkedMovieSet;
	}
	
	
	/**
	 * Givers Top N movies by review score using userType and Movie Genre
	 * Covers requirements:-
	 * 	- 4. List top n movies by total review score by ‘ viewer’ i n a particular genre.
	 *  - 6. List top n movies by total review score by ‘ critics’ i n a particular genre.
	 * @param number
	 * @param userType
	 * @param genre
	 * @return
	 */
	@SuppressWarnings("unlikely-arg-type")
	public Set<Movie> topNMoviesByReviewScore(int number, UserType userType, Genre genre){
		Set<Review> filteredMovies = repo.getReviews().stream()
				.filter(review -> review.getMovie().getGenres().contains(genre))
				.filter(review -> review.getUser().getUserType().equals(userType))
				.sorted(Comparator.comparing(Review::getRating, Comparator.reverseOrder()))
				.collect(Collectors.toSet());

		Set<Movie> linkedMovieSet = new LinkedHashSet<Movie>();
		filteredMovies.forEach(r -> linkedMovieSet.add(r.getMovie()));
		
		//If size of movies is greater then 'number' and delete from end
		if(!filteredMovies.isEmpty() && number<filteredMovies.size()) {
			int diff = filteredMovies.size() - number;
			for(int i=0; i<diff; i++) {
				filteredMovies.remove((Movie) filteredMovies.toArray()[filteredMovies.size()-1]);
			}
		}
		
		return linkedMovieSet;
	}
}
