package ru.learn.learnSpring.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class BaseResponse {
   @JsonProperty("result")
   private boolean isSuccess;

   @JsonIgnore
   public boolean isSuccess() {
      return isSuccess;
   }

   public void success(){
      isSuccess = true;
   }

   public void fail(){
      isSuccess = false;
   }
}
