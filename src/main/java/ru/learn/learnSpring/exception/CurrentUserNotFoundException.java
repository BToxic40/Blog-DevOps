package ru.learn.learnSpring.exception;

public class CurrentUserNotFoundException extends RuntimeException {
    public CurrentUserNotFoundException(){
       super("Текущий пользователь не найден в БД!");
    }
}
