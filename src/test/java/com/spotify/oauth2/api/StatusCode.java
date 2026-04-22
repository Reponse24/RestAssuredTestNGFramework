package com.spotify.oauth2.api;

public enum StatusCode {
    CODE_200(200, ""),
    CODE_201(201, ""),
    CODE_400(400, "Missing required field: name"),
    CODE_401(401, "Invalid access token");
////constant variables
//    private final int code;
//    private final String message;
//
//    StatusCode(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }
////There is another way of accessign constants. This is using public keyword instead of pivate to the variables.
//// If if we use this way, we will eed ot remove these getter methods, and access variable values by durectly using .code or .message, i place of getCode() and getMessage methods
//    public int getCode() {
//        return code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//*************************************
        //New way with public keyword

    public final int code;
    public final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
