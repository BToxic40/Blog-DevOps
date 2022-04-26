package ru.learn.learnSpring.exception;

public class CanNotCreateCaptchaException extends RuntimeException {
    public CanNotCreateCaptchaException(Exception e){
       super("Ошибка при создании каптчи!", e);
    }
}
