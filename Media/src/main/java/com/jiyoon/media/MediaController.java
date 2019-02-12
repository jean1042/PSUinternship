package com.jiyoon.media;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
/*
* @author : jiyoon
* date : 01/31/19
* 1. GetMapping for GET Request
* 2. PostMapping for POST Request
* 3. PutMapping for PUT Request
* 4. DeleteMapping for Delete Request*/

@RestController
public class MediaController {

    //1. GetMapping
    @RequestMapping(value={"/media", "/media/{id}"}, method=RequestMethod.GET)
    // check media function

    public ResponseEntity<?> checkMedia(@PathVariable Optional<Long> id){
        ConnectDatabase database=new ConnectDatabase();
        String query="";

        //1.id가 있는 경우
        if(id.isPresent()){
            query=String.format("SELECT * FROM media WHERE id=\"%s\"", id.get());
            ResultSet rs=database.executeQuery(query);

            try{

                if(rs.next()){
                    System.out.println("id"+rs.getInt("id"));
                    Media type=new Media(rs.getInt("id"), rs.getString("title"),rs.getString("description"), rs.getString("filename"), rs.getInt("media_type_id"));
                    return ResponseEntity.status(HttpStatus.OK).body(type);
                }

                else{
                    HttpErrorResponse e=new HttpErrorResponse(404,"Not found", "ID NOT FOUND","/media"+id.get());
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
                }
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            finally{
                database.close();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpErrorResponse.BadRequest("/media22"+id.get()));

        }

        //2. id 없는 경우 -> 모든 객체 모두 반환해주기
        else{
            ArrayList<Media> found=new ArrayList<Media>();
            query="SELECT * FROM media";

            ResultSet rs=database.executeQuery(query);

            try{
                while(rs.next()){
                    found.add(new Media(rs.getInt("id"), rs.getString("title"),rs.getString("description"), rs.getString("filename"), rs.getInt("media_type_id")));
                }
                return ResponseEntity.status(HttpStatus.OK).body(found.toArray());
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            finally{
                database.close();
            }

        }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpErrorResponse.BadRequest("/media"));
    }

    //2. POST MAPPING
    @RequestMapping(value={"/media","/media/{id}"},method=RequestMethod.POST)

    public ResponseEntity<?> insertMedia(@RequestBody String data){

        //1) database 연결
        ConnectDatabase database=new ConnectDatabase();

        try{
        //2)objectiveMapper 사용해서 json 을 object 로 바꿔줌
            ObjectMapper objectMapper=new ObjectMapper();

            //3) object를 jsonnode로 바꿔줌
            JsonNode node=objectMapper.readTree(data);

            //4) post 요청 중 데이터베이스에 넣을 자료 파싱
            int id=node.get("id").asInt();
            String title=node.get("title").asText();
            String description=node.get("description").asText();
            String filename=node.get("filename").asText();
            int media_type_id=node.get("media_type_id").asInt();
            //5) query 세팅
            String query=String.format("INSERT INTO media(\"id\",\"title\",\"description\",\"filename\",\"media_type_id\") VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")",id,title,description,filename,media_type_id);

            int result=database.executeUpdate(query);
            //6) 행 삽입 성공, 실패 판단
            if(result>0){ //테이블에 행 삽입 성공
                return ResponseEntity.status(HttpStatus.CREATED).body("");
            }
            else if(result==0){ //테이블에 행 삽입 실패
                HttpErrorResponse e=new HttpErrorResponse(409,"conflict","title already exists","/media");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
            } //데이터베이스 오류
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpErrorResponse.DatabaseError("/media"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            database.close();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(HttpErrorResponse.BadRequest("/media"));
    }
}






