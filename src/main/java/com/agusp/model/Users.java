/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agusp.model;

import io.github.millij.poi.ss.model.annotations.Sheet;
import io.github.millij.poi.ss.model.annotations.SheetColumn;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Sheet
@Entity
@Data
@Table(name = "users")
public class Users implements Serializable {

  @Id
  @Column(name = "Id")
  @SheetColumn("Id")
  private String id;

  @SheetColumn("Email")
  @Column(name = "Email", nullable = false)
  private String email;
  
  @SheetColumn("First_name")
  @Column(name = "First_name", nullable = false)
  private String first_name;
  
  @SheetColumn("Last_name")
  @Column(name = "Last_name", nullable = false)
  private String last_name;

  @SheetColumn("Password")
  @Column(name = "Password", nullable = false)
  private String password;

  @Column(name = "Balance")
  @SheetColumn("Balance")
  private BigDecimal balance = new BigDecimal(0.00);

}
