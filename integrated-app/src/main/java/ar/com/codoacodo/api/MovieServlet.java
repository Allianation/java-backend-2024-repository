package ar.com.codoacodo.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.com.codoacodo.database.MovieDAO;
import ar.com.codoacodo.domain.Movie;

@WebServlet("/movies")
public class MovieServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4050782523085903936L;
	
	private MovieDAO movieDAO = new MovieDAO();
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Configurar cabeceras CORS
	    resp.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
	    resp.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
	    resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
	    
	    // Establecer la codificación de caracteres
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	        
	    // Leer JSON del cuerpo de la solicitud y convertirlo en un objeto Movie
	    Movie movie = objectMapper.readValue(req.getInputStream(), Movie.class);
	        
	    // Insertar la película en la base de datos
	    Long id = movieDAO.addMovie(movie);
	        
	    // Convertir el id a json
	    String jsonResponse = objectMapper.writeValueAsString(id);
	        
	    // Establecer el tipo de contenido de la respuesta a JSON
	    resp.setContentType("application/json");
	        
	    // Escribir la respuesta JSON
	    resp.getWriter().write(jsonResponse);
	        
	    // Establecer el estado de la respuesta a 201 (Creado)
	    resp.setStatus(HttpServletResponse.SC_CREATED);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	
		// Configurar cabeceras CORS
	    resp.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
	    resp.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
	    resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
	        
	    // Establecer la codificación de caracteres
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    
	    // Obtener los parámetros de la URL
	    String idParam = req.getParameter("id");
	    String titleParam = req.getParameter("title");
	    
	    try {
	        	
	    	if (idParam != null) {
	    		// Buscar película por ID
	            Long id = Long.parseLong(idParam);
	            Movie movie = movieDAO.getMovieById(id);
	                
	            if (movie != null) {
	            	String jsonResponse = objectMapper.writeValueAsString(movie);
	                resp.setContentType("application/json");
	                resp.getWriter().write(jsonResponse);
	            } else {
	                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Pelicula no encontrada");
	            }
	                
	    	} else if (titleParam != null && !titleParam.isEmpty()) {
	    		// Buscar películas por título
	            List<Movie> movieList = movieDAO.getMovieByTitle(titleParam);
	            String jsonResponse = objectMapper.writeValueAsString(movieList);
	            resp.setContentType("application/json");
	            resp.getWriter().write(jsonResponse);
	                
	    	} else {
	    		// Obtener todas las películas
	            List<Movie> movieList = movieDAO.getAll();
	            String jsonResponse = objectMapper.writeValueAsString(movieList);
	            resp.setContentType("application/json");
	            resp.getWriter().write(jsonResponse);
	    	}
	            
	    } catch (NumberFormatException e) {
	    	resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
	    }
	}
	    
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Configurar cabeceras CORS
	    resp.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
	    resp.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
	    resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
	    
	    // Establecer la codificación de caracteres
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    
	    try {

	    	// Leer JSON del cuerpo de la solicitud y convertirlo en un objeto Movie
	        Movie movie = objectMapper.readValue(req.getInputStream(), Movie.class);
	        
	        // levantar el id del queryParams e insertarlo en el objeto pelicula
	        String idParam = req.getParameter("id");
	        
	        if (idParam != null) {
	        	Long id = Long.parseLong(idParam);
	        	movie.setId(id);
	        }
	    
	        // Verificar que la película tenga un ID
	        if (movie.getId() == null) {
	        	resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID de la película es necesario para actualizar.");
	            return;
	        }
	    
	        // Actualizar la película en la base de datos
	        movieDAO.updateMovie(movie);
	    
	        // Establecer el tipo de contenido de la respuesta a JSON
	        resp.setContentType("application/json");
	    
	        // Escribir la respuesta JSON
	        resp.getWriter().write("{\"message\": \"Pelicula actualizada exitosamente\"}");
	    
	        // Establecer el estado de la respuesta a 200 (OK)
	        resp.setStatus(HttpServletResponse.SC_OK);
	        
	    } catch (IllegalArgumentException e) {
	    	resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
	    } catch (Exception e) {
	    	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar la película");
	    	e.printStackTrace();
	    }
	}
	    
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Configurar cabeceras CORS
		resp.setHeader("Access-Control-Allow-Origin", "*"); // Permitir acceso desde cualquier origen
		resp.setHeader("Access-Control-Allow-Methods", "*"); // Métodos permitidos
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type"); // Cabeceras permitidas
		
		// Establecer la codificación de caracteres
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		// Obtener el parámetro 'id' de la URL
		String idParam = req.getParameter("id");
		
		if (idParam != null) {
			
			try {
				Long id = Long.parseLong(idParam);
				movieDAO.deleteMovie(id);
	                
				// Establecer el tipo de contenido de la respuesta a JSON
				resp.setContentType("application/json");
	                
				// Escribir la respuesta JSON
				resp.getWriter().write("{\"message\": \"Pelicula eliminada exitosamente\"}");
	                
				// Establecer el estado de la respuesta a 200 (OK)
				resp.setStatus(HttpServletResponse.SC_OK);
				
			} catch (NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
			}
			
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID no proporcionado");
		}
	}
	
}
