package com.test.mycommentapic;

public class Model {
    private String Name, Comment;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public Model(String namee, String comment) {
        Name = namee;
        Comment = comment;
    }

    public Model() {

    }

}
