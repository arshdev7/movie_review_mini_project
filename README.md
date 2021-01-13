# movie_review_mini_project

NOTE: PLEASE ADD LOMBOK.JAR SUPPORT IN YOUR IDE BEFORE RUNNING THE PROJECT

Assignment Details:-

Assignment: Movie Review Service
The assignment i s to i mplement a service related to movie reviews, and following
is the description of i t. You can choose a ny language that you are comfortable
with. Please push your code to Github and share the link with us when you reply.
Overview
The movie review service collects reviews for movies from i ts users. Later these
movie reviews are used to derive i nsights which helps i n enriching the lives of i ts
customers with entertainment.
Internal Capabilities
1. Users of the service can review only movies which are released so far, they
cannot review upcoming movies.
2. Users can give a review-score between 1 to 10. (Higher the score the better
the liking for the movie). Currently we are not allowing a user to review the
same movie multiple times.
3. The service by default on-boards a user as a ‘ viewer’. Later a ‘ viewer’ can
be upgraded to a ‘ critic’ after he/she has published more than 3 reviews
for various movies.
4. Critics are considered as experts in the judgement here, so critics reviews
will be captured with more weightage. i .e. 6 review rating of a critic will be
considered as 12 (2x) NOTE: Older reviews by the user as ` viewer` shall not
be affected.
5. Good To Have: B ased on the users behaviour, the service should give the
capability to plugin more u ser upgradation policies. Eg. User -> Critic ->
Expert -> Admin.
Requirements
Each of the following features needs to be i mplemented:
1. Adding users and movies.
2. User to review a movie.
3. List top n movies by total review score by ‘ viewer’ i n a particular year of
release.
4. List top n movies by total review score by ‘ viewer’ i n a particular genre.
5. List top n movies by total review score by ‘ critics’ i n a particular year of
release.
6. List top n movies by total review score by ‘ critics’ i n a particular genre.
7. Average review score i n a particular year of release.
8. Average review score in a particular genre.
9. Average review score for a particular movie.
Expectations
1. Make sure that you have working and demonstrable code for all the above
requirements.
2. Feature requirements should be strictly followed. Work on the expected
output first and then add good-to-have features of your own.
3. Use of proper abstraction, separation of concerns i s required.
4. Code should easily accommodate new requirements with minimal
changes.
5. Proper exception handling i s required.
6. Code should be modular, readable and unit-testable.
Important Notes:
● Do not use any database store, use i n-memory data structure.
● Do not write an API on top of the service methods.
● Do not create any UI for the application.
● Do not build a Command line i nterface, executing test cases or via the
simple Main function should be sufficient.
Sample Test Cases:
1. Onboard 10 movies onto your platform in 3 different years.
a. Add Movie("Don" released i n Year 2006 for Genres “Action” &
“Comedy”)
b. Add Movie("Tiger" released i n Year 2008 for Genre “Drama”)
c. Add Movie("Padmaavat" released i n Year 2006 for Genre “Comedy”)
d. Add Movie("Lunchbox" released i n Year 2021 for Genre “Drama”)
e. Add Movie("Guru" released i n Year 2006 for Genre “Drama”)
f. Add Movie("Metro" released i n Year 2006 for Genre “Romance”)
2. Add users to the system:
a. Add User(“SRK”)
b. Add User(“Salman”)
c. Add User(“Deepika”)
3. Add Reviews:
a. add_review(“SRK”, “Don”, 2)
b. add_review(“SRK”, “Padmavaat”, 8)
c. add_review(“Salman”, “Don”, 5)
d. add_review(“Deepika”, “Don”, 9)
e. add_review(“Deepika”, “Guru”, 6)
f. add_review(“SRK”,”Don”, 10) - Exception multiple reviews not
allowed
g. add_review(“Deepika”, “Lunchbox”, 5) - Exception movie yet to be
released
h. add_review(“SRK”, “Tiger”, 5). Since ‘ SRK’ has published 3 reviews, he
is promoted to ‘ critic’ now.
i. add_review(“SRK”, “Metro”, 7)
4. List top 1 movie by review score in “2006” year:
  a. Top i n Year “2006”:
  Output: Don - 16 ratings (sum of all ratings of Deepika, Salman &
  SRK)
  b. Top i n Year “2006” by “critics_preferred”:
  Output: Metro - 14 ratings (only SRK gave 7 for Metro as critic)
5. List top 1 movie by review score in “Drama” genre:
Output: Guru - 6 ratings
6. List top movie by average review score in “2006” year:
Output: (2 + 8 + 5 + 9 + 6 + 14) / 6 = 7.33 ratings (7 of “Metro”
considered twice since i t was submitted by critic)
