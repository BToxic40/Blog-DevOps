package ru.learn.learnSpring.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  UserResponse extends BaseResponse {

  private int id;
  private String name;
  private String email;
  private types type;
  public enum types {MODERATOR, ADMIN}

}
