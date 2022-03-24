package ru.learn.learnSpring.api.response;

import lombok.Data;

@Data
public class UserCheckResponse {
    private int id;
    private String name;
    private String photo;
    private String email;
    private boolean moderation;
    private int moderationCount;
    private boolean settings;

//    "id": 576,
//            "name": "Дмитрий Петров",
//            "photo": "/avatars/ab/cd/ef/52461.jpg",
//            "email": "petrov@petroff.ru",
//            "moderation": true,
//            "moderationCount": 56,
//            "settings": true
}
