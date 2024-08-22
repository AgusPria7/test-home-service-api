
package com.agusp.base;

import com.agusp.exeption.CustomException;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class Session {

  @PersistenceContext
  public EntityManager em;


  public String FAILED = "FAILED";
  public String SUCCESS = "SUCCESS";

  public String INSERT = "INSERT";
  public String UPDATE = "UPDATE";
  public String DELETE = "DELETE";

  public Query query;
  public org.hibernate.Session session;
  public Transaction transaction;

  @Value("${server.servlet.contextPath}")
  public String perfix;


}

