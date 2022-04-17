package ru.learn.learnSpring.api.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class BaseResponse {
   public static final BaseResponse successResponse = new BaseResponse(true);

   public BaseResponse(boolean isSuccess) {
      this.isSuccess = isSuccess;
   }

   public BaseResponse() {
   }

   @JsonProperty("result")
   protected boolean isSuccess;

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
