package ru.learn.learnSpring.api.response.postPreview;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.learn.learnSpring.api.response.BaseResponse;

@Getter
@Setter
@AllArgsConstructor
public class  UserResponse extends BaseResponse {

  private int id;
  private String name;

//  private String email;
//  private types type;
//  public enum types {MODERATOR, ADMIN}

}
