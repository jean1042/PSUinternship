package com.jiyoon.media;


public class Media {
    public int id;
    public String title;
    public String description;
    public String filename;
    public int media_type_id;
    public Media(){

    }

    public Media(int id, String title, String description, String filename, int media_type_id){
        this.id=id;
        this.title=title;
        this.description=description;
        this.filename=filename;
        this.media_type_id=media_type_id;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public int getMedia_type_ids(){
        return media_type_id;
    }

    public void setMedia_type_id(int media_type_id){
        this.media_type_id=media_type_id;
    }

    public String getfilename(){
        return filename;
    }

    public void setFilename(String filename){
        this.filename=filename;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getDescription(){
        return description;
    }


    public void setDescription(String description){
        this.description=description;
    }
}
