# PopularMovie
PopularMovie

- UI contains an element (e.g., a spinner or settings menu) to toggle the sort order of the movies by: most popular, highest rated.
- Movies are displayed in the main layout via a grid of their corresponding movie poster thumbnails.
- UI contains a screen for displaying the details for a selected movie.
- Movie Details layout contains title, release date, movie poster, vote average, and plot synopsis.
- Movie Details layout contains a section for displaying trailer videos and user reviews.
- In the movies detail screen, a user can tap a heart buttonto mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.
- The titles and IDs of the user’s favorite movies are stored in a native SQLite database and exposed via a ContentProvider
- Data is updated whenever the user favorites or unfavorites a movie
- When the "favorites" setting option is selected, the main view displays the entire favorites collection based on movie ids stored in the database.
- Extended the favorites database to store the movie poster, synopsis, user rating, and release date, and display them even when offline.
- Implemented sharing functionality to allow the user to share the first trailer’s YouTube URL from the movie details screen.

# How to Run the project

In order to run this project, please provide the movie DB Api key.

Click on gradle.properties file and put the following:

API_KEY = "YOUR MOVIE DB KEY"
