package edu.fsu.cs.mobile.onethousandwords;

/**
 * Helper class for User database
 */

public class MyUser {
    private String email;
    private String id;
    private String name;

    public MyUser(){
        email = "";
        id = "";
        name = "";
    }

    public MyUser (String EMAIL, String ID, String NAME){
        email = EMAIL;
        id = ID;
        name = NAME;
    }

    public String getEmail(){

        return email;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }
}
