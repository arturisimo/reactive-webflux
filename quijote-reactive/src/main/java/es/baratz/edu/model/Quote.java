package es.baratz.edu.model;

public final class Quote {

    private String id;
    private String book;
    private String content;

    // Empty constructor is required by the data layer and JSON de/ser
    public Quote() {
    }

    public Quote(String id, String book, String content) {
        this.id = id;
        this.book = book;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getBook() {
        return book;
    }

    public String getContent() {
        return content;
    }

	@Override
	public String toString() {
		return "Quote [id=" + id + ", book=" + book + ", content=" + content + "]";
	}
    
}