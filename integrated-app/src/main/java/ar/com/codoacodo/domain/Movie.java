package ar.com.codoacodo.domain;

public class Movie {
	
	private Long id;
    private String title;
    private String duration;
    private String genre;
    private String image;
    
	public Movie() {
		super();
	}

	public Movie(Long id, String title, String duration, String genre, String image) {
		super();
		this.id = id;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", duration=" + duration + ", genre=" + genre + ", image="
				+ image + "]";
	}
	
}
