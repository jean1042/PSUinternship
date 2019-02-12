package com.jiyoon.media;

import java.sql.*;

/*
@author jiyoon
* 1. 생성자에서 connect 함수 호출
* 2. connect() 함수 구현
* 3. close() 함수 구현
*
* */
public class ConnectDatabase {
    private Connection connection=null;

    //1. 생성자에서 connect 함수 호출
    public ConnectDatabase(){
        try{
            connect();
        }
        catch(Exception e){
            new RuntimeException("Database Connect Failed");
        }
    }

    //2.connect() function
    public void connect() {
    String url="jdbc:sqlite:C:\\Users\\jean1\\IdeaProjects\\media\\JRWT.sqlite3";

    try{
        connection= DriverManager.getConnection(url);

    } catch (SQLException e) {
        e.printStackTrace();
    }
    }


    //3. close() function
    public void close(){
        try{
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*executeQuery() 와 executeUpdate의 차이점
    * executeQuery() : ResultSet 을 얻기 위한 메소드로, SELECT 문이 이에 속한다
    * executeUpdate() : 적용된 행의 갯수를 얻기 위한 메소드로, INSERT, UPDATE, DELETE에 사용된다.
    * */
    public ResultSet executeQuery(String query) {
        try{
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery(query);
            return rs;

        }
        catch(SQLException e){
           e.printStackTrace();
           return null;
        }
    }

    public int executeUpdate(String query){
        try{
            Statement statement=connection.createStatement();
            int result=statement.executeUpdate(query);
            return result;
        }
        catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}

