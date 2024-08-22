
package com.agusp.base;

import org.springframework.http.HttpStatus;

public class ResponsBody {
  private Object status = 200;
  private Object message = "";
  private Object data = "";

  public ResponsBody(Object data) {
    this.data = data;
  }

  public ResponsBody(Object data, Object message) {
    this.data = data;
  }

  public ResponsBody(Object data, Object message, HttpStatus status) {
    this.status = status.value();
    this.message = message;
    this.data = data;
  }

  public Object getStatus() {
    return status;
  }

  public Object getMessage() {
    return message;
  }

  public Object getData() {
    return data;
  }
  
}
