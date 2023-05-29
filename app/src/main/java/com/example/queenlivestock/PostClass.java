package com.example.queenlivestock;

public class PostClass {
    private String id;
    private String title;
    private String description;
    private String price;
    private String user_id;
    private String active;
    private String image;


    public PostClass(String id, String title, String description, String price, String user_id, String active, String image) {
        this.id = id != null ? id : "";
        this.title = title != null ? title : "";
        this.description = description != null ? description : "";
        this.price = price != null ? price : "";
        this.user_id = user_id != null ? user_id : "";
        this.active = active != null ? active : "1";
        this.image = image != null ? image : "";
    }


    @Override
    public String toString() {
        return "PostClass{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", user_id='" + user_id + '\'' +
                ", active='" + active + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

