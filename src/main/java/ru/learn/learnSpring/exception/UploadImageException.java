package ru.learn.learnSpring.exception;

public class UploadImageException extends RuntimeException{

    public UploadImageException(Exception e){
        super(e);
    }
}
