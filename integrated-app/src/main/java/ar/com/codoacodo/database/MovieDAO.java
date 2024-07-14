package ar.com.codoacodo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.codoacodo.domain.Movie;

public class MovieDAO {
	
	public Long addMovie(Movie movie) {
		
        String INSERT_MOVIE_SQL = "INSERT INTO movie (title, duration, genre, image) VALUES (?, ?, ?, ?)";
       
        try (Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MOVIE_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
           
            preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDuration());
            preparedStatement.setString(3, movie.getGenre());
            preparedStatement.setString(4, movie.getImage());

            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    Long id = rs.getLong(1);
                    System.out.println("Pelicula insertada exitosamente. ID: " + id);
                    return id;
                } else {
                    System.out.println("Error al obtener el ID de la pelicula insertada.");
                    return null;
                }
            } else {
                System.out.println("Error al insertar la pelicula.");
                return null;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al insertar la pelicula: " + e.getMessage());
            return null;
        }
    }

    public List<Movie> getAll() {
    	
        String SELECT_ALL_SQL = "SELECT * FROM movie";
        
        List<Movie> movieList = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SQL)) {
        	
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                String genre = resultSet.getString("genre");
                String image = resultSet.getString("image");

                Movie movie = new Movie(id, title, duration, genre, image);
                movieList.add(movie);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener las peliculas: " + e.getMessage());
        }
        
        return movieList; 
    }

    public Movie getMovieById(Long id) {
    	
        Movie movie = null;
     
        try (Connection connection = DatabaseConnection.getConnection(); 
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?")) {
        	
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	 Long idMovie = resultSet.getLong("id");
                 String title = resultSet.getString("title");
                 String duration = resultSet.getString("duration");
                 String genre = resultSet.getString("genre");
                 String image = resultSet.getString("image");

                movie = new Movie(idMovie, title, duration, genre, image);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener la pelicula: " + e.getMessage());
        }
        
        return movie;
    }
    
    public void updateMovie(Movie movie) {
    	
    	if (movie.getId() == null) {
            throw new IllegalArgumentException("El ID de la película no puede ser nulo para actualizarla.");
        }
    
        String UPDATE_MOVIE_SQL = "UPDATE movie SET title = ?, duration = ?, genre = ?, image = ? WHERE id = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOVIE_SQL)) {
        	
        	preparedStatement.setString(1, movie.getTitle());
            preparedStatement.setString(2, movie.getDuration());
            preparedStatement.setString(3, movie.getGenre());
            preparedStatement.setString(4, movie.getImage());
            preparedStatement.setLong(5, movie.getId());
    
            int result = preparedStatement.executeUpdate(); // Ejecuta la consulta y devuelve el número de filas afectadas.
            
            if (result > 0) {
                System.out.println("Pelicula actualizada exitosamente.");
            } else {
                System.out.println("Error al actualizar la pelicula.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar la pelicula: " + e.getMessage());
        }
    }
    
    public void deleteMovie(Long id) {
    	
    	String DELETE_MOVIE_SQL = "DELETE FROM movie WHERE id = ?";
       
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MOVIE_SQL)) {
        	
        	preparedStatement.setLong(1, id);

            int result = preparedStatement.executeUpdate();
            
            if (result > 0) {
                System.out.println("Pelicula eliminada exitosamente.");
            } else {
                System.out.println("Error al eliminar la pelicula.");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar la pelicula: " + e.getMessage());
        }
    }
    
    public List<Movie> getMovieByTitle(String title) {
    	
        String MOVIE_BY_TITLE_SQL = "SELECT * FROM movie WHERE title LIKE ?";
        
        List<Movie> movieList = new ArrayList<>();
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(MOVIE_BY_TITLE_SQL)) {
        	
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
            	 Long id = resultSet.getLong("id");
                 String titleMovie = resultSet.getString("title");
                 String duration = resultSet.getString("duration");
                 String genre = resultSet.getString("genre");
                 String image = resultSet.getString("image");

                Movie movie = new Movie(id, titleMovie, duration, genre, image);
                movieList.add(movie);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar las peliculas: " + e.getMessage());
        }
        
        return movieList; 
    }

}
