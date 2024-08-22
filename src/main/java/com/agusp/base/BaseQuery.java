
package com.agusp.base;

import static java.lang.ProcessBuilder.Redirect.to;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.boot.autoconfigure.web.ServerProperties.Reactive.Session;

@MappedSuperclass
public abstract class BaseQuery extends Session {

    public Map<String, Object> singleResult(String sql) {
    session = em.unwrap(org.hibernate.Session.class);
    Map<String, Object> res = (Map<String, Object>) session.createNativeQuery(sql).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).getSingleResult();
    return res;
  }
   
    public long countResult(String sql) {
    session = em.unwrap(org.hibernate.Session.class);
    return ((Number) session.createNativeQuery(sql).getSingleResult()).intValue();
  }
      
  public String toString(Object obj, String key) {
    HashMap<String, String> data = (HashMap<String, String>) obj;
    return data.get(key);
  }

  public String paging(long page, long size) {
    String limit = " LIMIT " + String.valueOf(fromPaging(page, size)) + "," + String.valueOf(toPaging(page, size));
    return limit;
  }

  public Long totalPage(long count, long size) {
    long total = count / size;
    return total + 1;
  }

  public Long fromPaging(long page, long size) {
    long from = page * size;
    return from;
  }

  public Long toPaging(long page, long size) {
    long to = size;
    return to;
  }

  public String getTableName(Class<?> clazz) {
    Class<?> c = clazz;
    Table table = c.getAnnotation(Table.class);
    return table.name();
  }

  public String getStatus(String field, String status) {
    if (status == null) {
      return "";
    }

    if (status.equals("true")) {
      return " AND " + field + " = TRUE ";
    }

    if (status.equals("false")) {
      return " AND " + field + " = FALSE ";
    }
    return "";
  }

}

