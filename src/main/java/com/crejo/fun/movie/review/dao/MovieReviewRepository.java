package com.crejo.fun.movie.review.dao;

import java.time.Year;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.crejo.fun.movie.review.entity.Movie;
import com.crejo.fun.movie.review.entity.Review;
import com.crejo.fun.movie.review.entity.User;
import com.crejo.fun.movie.review.entity.UserType;

@Repository
public class MovieReviewRepository {
	private Set<Movie> movies;
	private Set<User> users;
	private Set<Review> reviews;

	public MovieReviewRepository() {
		this.movies = new HashSet<>();
		this.users = new HashSet<>();
		this.reviews = new HashSet<>();
	}

	public boolean addMovie(Movie movie){
		return movies.add(movie);
	}

	public boolean addUser(User user) {
		return users.add(user);
	}

	public boolean addReview(Review review) throws Exception {
		Set<Review> reviewSet = this.reviews;

		Optional<Review> optionalReview = reviewSet.stream()
				.filter(r -> r.getUser().getUserName().equalsIgnoreCase(review.getUser().getUserName()))
				.filter(r -> r.getMovie().getMovieName().equalsIgnoreCase(review.getMovie().getMovieName()))
				.findAny();
		
		if(optionalReview.isPresent()) {
			throw new Exception(review.toString() + " Exception multiple reviews not allowed");
		}
		
		if(review.getMovie().getReleasedYear().isAfter(Year.of(2020))) {
			throw new Exception(review.toString() + " Exception movie yet to be released");
		}
		
		User user =updateUserDetails(review.getUser().getUserName());
		updateRating(review);
		review.setUser(user); //update the review again after user is updated
		return reviews.add(review);
	}

	private void updateRating(Review review) {
		Set<User> userSet = this.users;
		Optional<User> optionalUser = userSet.stream()
				.filter(u -> u.getUserName().equalsIgnoreCase(review.getUser().getUserName()))
				.filter(user -> !user.getUserType().equals(UserType.VIEWER))
				.findAny();
		if(optionalUser.isPresent()) {
			review.setRating(review.getRating()*2);
		}
	}

	private User updateUserDetails(String userName) {
		Set<User> userSet = this.users;
		Optional<User> optionalUser = userSet.stream()
				.filter(u -> u.getUserName().equalsIgnoreCase(userName))
				.findFirst();
		User user = null;
		if(optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		User updatedUser = null;	//as we want new user and not the reference of existing user
		if(user != null) {
			userSet.remove(user); //remove user from the list before updating it
			updatedUser = new User(user.getUserName());
			updatedUser.setReviewCount(user.getReviewCount() + 1);
			updatedUser.setUserType(user.getUserType());
			updateUserType(updatedUser);
			userSet.add(updatedUser); // added user back to set after updating it
		}
		return updatedUser;
	}

	private void updateUserType(User user) {
		if(user.getReviewCount() > 9) {
			user.setUserType(UserType.ADMIN);
		}else if(user.getReviewCount() > 6) {
			user.setUserType(UserType.EXPERT);
		}else if(user.getReviewCount() > 3) {
			user.setUserType(UserType.CRITIC);
		}
	}
	
	public Set<Review> getReviews(){
		return this.reviews;
	}
	
	public Set<User> getUsers(){
		return this.users;
	}
	
	public Set<Movie> getMovies(){
		return this.movies;
	}

}
