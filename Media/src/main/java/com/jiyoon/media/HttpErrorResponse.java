package com.jiyoon.media;

public class HttpErrorResponse {
    private final int status;
    private final String message;
    private final String path;
    private final String error;

    public HttpErrorResponse(int status, String error, String message, String path){
        this.status=status;
        this.error=error;
        this.message=message;
        this.path=path;
    }

    public static HttpErrorResponse BadRequest(String path){
        return new HttpErrorResponse(400, "Bad Request","error", path);
    }

    public static HttpErrorResponse DatabaseError(String path){
        return new HttpErrorResponse(400, "Bad Request", "database error", path);
    }
}
