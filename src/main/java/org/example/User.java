package org.example;

public class User {
    public String username;
    public String phone;
    public long id;

    public long getId() {
        return id;
    }

    public User(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public String getName() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(long id) {
        this.id = id;
    }

}
