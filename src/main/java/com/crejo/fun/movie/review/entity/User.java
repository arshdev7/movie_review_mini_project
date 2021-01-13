package com.crejo.fun.movie.review.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	private String userName;
	private int reviewCount;
	private UserType userType;
	
	public User(String userName) {
		this.userName = userName;
		this.reviewCount = 0;
		this.userType = UserType.VIEWER;
	}
}
