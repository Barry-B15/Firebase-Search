package com.example.barry.firebasesearch;

/**
 * Created by barry on 12/27/2017.
 */

public class Users {
    //1. add 3 strings since we have 3 elements in our layout and db
    // they must be same with the keys used in the db, case sensitive
    public String name, image, status, like, imageV;
    // &&&&&&&&& like for LikeBtn added &&&&&&&&&&&&&&&&  02/23
    //@@@@@@ imageV added to click 2 c bigger image @@@@@@ 02/24

    //4. create an empty constructor for the user class
    public Users() {

    }

    //3. add the getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLike() {  // &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&
        return like;
    }

    public void setLike(String like) { // &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&
        this.like = like;
    }

    public String getImageV() {  //@@@@@@ to click 2 c bigger image @@@@@@
        return imageV;
    }

    public void setImageV(String imageV) {
        this.imageV = imageV;  //@@@@@@ to click 2 c bigger image @@@@@@
    }

    //2. add the constructor  (I am adding like to the constructor)

    public Users(String name, String image, String status, String like, String imageV) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.like = like;  // &&&&&&&&& LikeBtn &&&&&&&&&&&&&&&&&&&&&&
        this.imageV = imageV; //@@@@@@ to click 2 c bigger image @@@@@@
    }

    /*
    public String name, image, status;

    public Users(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String name, String image, String status) {
        this.name = name;
        this.image = image;
        this.status = status;
    }
    */
}
