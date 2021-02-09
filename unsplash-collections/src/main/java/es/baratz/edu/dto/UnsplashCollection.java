package es.baratz.edu.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnsplashCollection implements Serializable {
	
	private static final long serialVersionUID = -7211846252615120119L;
	
	private String id;
	
	private String title;

	private String description;
	
	@JsonAlias("cover_photo_id")
	private String coverPhotoId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCoverPhotoId() {
		return coverPhotoId;
	}

	@JsonProperty("cover_photo")
	public void setCoverPhotoId(Map<String, ?> covers) {
		coverPhotoId = (String) covers.get("id");
	}
	
}
