package com.example.queenlivestock;

public class UserClass {
    private String id;
    private String name;
    private String email;
    private String phone_no;
    private String address;
    private String image;
    private String role;
    private String password;

    public UserClass(String id, String name, String email, String phone_no, String address, String image, String role, String password) {
        this.id = id != null ? id : "";
        this.name = name != null ? name : "";
        this.email = email != null ? email : "";
        this.phone_no = phone_no != null ? phone_no : "";
        this.address = address != null ? address : "";
        this.image = image != null ? image : "";
        this.role = role != null ? role : "";
        this.password = password != null ? password : "";
    }

    @Override
    public String toString() {
        return "UserClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
