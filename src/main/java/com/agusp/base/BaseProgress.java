
package com.agusp.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.MappedSuperclass;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.MappedSuperclass;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@MappedSuperclass
public abstract class BaseProgress {

  public Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
}

