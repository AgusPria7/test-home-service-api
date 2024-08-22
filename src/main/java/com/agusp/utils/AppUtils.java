
package com.agusp.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AppUtils {

  @Autowired
  public ResourceLoader resourceLoader;
  @Autowired
  public DataSource dataSource;

  public static String modelToJson(Object obj) throws SQLException, JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  public String getEncode(String obj) {
    return Base64.getEncoder().encodeToString(obj.toString().getBytes());
  }

  public String getDecode(String obj) {
    byte[] decodedBytes = Base64.getDecoder().decode(obj);
    return new String(decodedBytes);
  }

  public static String toStatus(String status) {
    if (status.equals("Active")) {
      status = "1";
    } else if (status.equals("InActive")) {
      status = "0";
    } else {
      status = "";
    }
    return status;
  }

  public static String toString(JSONObject obj, String str) {
    if (obj.isEmpty()) {
      str = "";
    } else if (obj.has(str)) {
      str = obj.get(str).toString();
    } else {
      str = "";
    }
    return str;
  }

  public static int toInteger(JSONObject obj, String str) {
    int val = 0;
    if (obj.isEmpty()) {
      val = 0;
    } else if (obj.has(str)) {
      val = obj.getInt(str);
    } else {
      val = 0;
    }
    return val;
  }

  public static BigDecimal toBigDecimal(JSONObject obj, String str) {
    BigDecimal val = new BigDecimal("0");
    if (obj.isEmpty()) {
      val = new BigDecimal("0");
    } else if (obj.has(str)) {
      val = new BigDecimal(obj.getString(str));
    } else {
      val = new BigDecimal("0");
    }
    return val;
  }

  public static boolean validationExt(String file, String[] ext) {
    int res = Arrays.binarySearch(ext, file);
    return res > 0 ? true : false;
  }

  public static String getExttention(String fileName) {
    int index = fileName.lastIndexOf('.');
    if (index > 0) {
      fileName = fileName.substring(index + 1);
    }
    return fileName;
  }

  public static Pageable getPageable(Integer offset, Integer limit) {
    Pageable pageable;
    if (offset == null || limit == null) {
      offset = 0;
      limit = 10;
      pageable = PageRequest.of(offset, limit);
    } else {
      pageable = PageRequest.of(offset, limit);
    }
    return pageable;
  }

  public static Pageable pageableWithSort(Integer offset, Integer limit, String[] sort) {
    Pageable pageable;
    List<Sort.Order> orders = new ArrayList<Sort.Order>();
    if (sort[0].contains(",")) {
      // will sort more than 2 fields
      // sortOrder="field, direction"
      for (String sortOrder : sort) {
        String[] _sort = sortOrder.split(",");
        orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
      }
    } else {
      // sort=[field, direction]
      orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
    }

    if (offset == null || limit == null) {
      offset = 0;
      limit = 10;
      pageable = PageRequest.of(offset, limit, Sort.by(orders));
    } else {
      pageable = PageRequest.of(offset, limit, Sort.by(orders));
    }
    return pageable;
  }

  private static Sort.Direction getSortDirection(String direction) {
    if (direction.equals("ASC")) {
      return Sort.Direction.ASC;
    } else if (direction.equals("DESC")) {
      return Sort.Direction.DESC;
    }

    return Sort.Direction.ASC;
  }

  public static Date atStartOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return localDateTimeToDate(startOfDay);
  }

  public static Date atEndOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
  }

  public static Date subTractFromCurrentDate(Date date, Long days) {
    LocalDateTime localDateTime = dateToLocalDateTime(date).minusDays(days);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
  }

  private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  private static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static String calculateAgeYearMonthDayfromDOB(Date birthDate) {
    // direct age calculation
    String ageYearMonthDay = null;
    if (birthDate != null) {

      Calendar c = Calendar.getInstance();
      c.setTime(birthDate);
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH) + 1;
      int date = c.get(Calendar.DATE);
      LocalDate l1 = LocalDate.of(year, month, date);
      LocalDate now1 = LocalDate.now();
      Period diff = Period.between(l1, now1);
      ageYearMonthDay = diff.getYears() + "Y " + diff.getMonths() + "M " + diff.getDays() + "D ";
    }
    return ageYearMonthDay;
  }

  public static Integer calculateAgeInYearsfromDOB(Date birthDate) {
    // direct age calculation
    Integer ageInYears = null;
    if (birthDate != null) {

      Calendar c = Calendar.getInstance();
      c.setTime(birthDate);
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH) + 1;
      int date = c.get(Calendar.DATE);
      LocalDate l1 = LocalDate.of(year, month, date);
      LocalDate now1 = LocalDate.now();
      Period diff = Period.between(l1, now1);
      ageInYears = diff.getYears();
    }
    return ageInYears;
  }

  public static String getDateWithoutTime(Date date) {
    if (date != null) {
      try {
        SimpleDateFormat formatterWithOutTime = new SimpleDateFormat("dd/MM/yyyy");
        return formatterWithOutTime.format(date);
      } catch (Exception ex) {
      }
    }
    return null;
  }

  public static String getDateWithTimeAMorPM(Date date) {

    if (date != null) {
      try {
        SimpleDateFormat formatterWithTime = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        return formatterWithTime.format(date);
      } catch (Exception ex) {
      }

    }
    return null;
  }

  public static String convertDateToLocalDateTime(Date date) {
    try {
      /* output is 17-02-2019 05:33 PM */
      return new SimpleDateFormat("dd-MM-yyyy hh:mm aaa").format(date);
    } catch (Exception e) {
      return null;
    }
  }

  public static Date convertLocalDateTimeToDate(String string) {
    try {
      /* 17-02-2019 */
      return new SimpleDateFormat("dd-MM-yyyy hh:mm aaa").parse(string);
    } catch (Exception e) {
      return null;
    }
  }

  public static String convertOnlyDateToString(Date date) {
    if (date != null) {
      try {
        SimpleDateFormat formatterWithOutTime = new SimpleDateFormat("dd-MM-yyyy");
        return formatterWithOutTime.format(date);
      } catch (Exception ex) {
      }
    }
    return null;
  }

  public static Date convertOnlyStringToDate(String string) {
    try {
      /* 17-02-2019 */
      return new SimpleDateFormat("dd-MM-yyyy").parse(string);
    } catch (Exception e) {
      return null;
    }
  }

  public static String convertDateToString(Date date, String pattern) {
    if (date != null) {
      try {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
      } catch (Exception ex) {
        ex.printStackTrace();
        return null;
      }
    }
    return null;
  }

  public static Date dateFromStringDDMMYY(String date) {
    try {
      return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static Date convertStringToDate(String dateStr, String pattern) {
    try {
      return new SimpleDateFormat(pattern).parse(dateStr);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static Date addHourMinutesSeconds(int hour, int minute, int second, Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    calendar.add(Calendar.HOUR_OF_DAY, hour);
    calendar.add(Calendar.MINUTE, minute);
    calendar.add(Calendar.SECOND, second);

    return calendar.getTime();
  }

  public static Date yesterday() {
    final Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    return cal.getTime();
  }

  public static String getRandomUUID() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  public static boolean isStringMatchWithDateFormat(String input) {

    boolean checkFormat;

    checkFormat = input.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})");
    return checkFormat;
  }

  /**
   * @param filePath
   * @return
   */
  public String getResoucePath(String filePath) {
    Resource resource = new ClassPathResource(filePath);
    try {
      return resource.getFile().getAbsolutePath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * @param filePath
   * @return
   */
  public String getResouceAbsolutePath(String filePath) throws FileNotFoundException {
    Resource resource = resourceLoader.getResource(filePath);
    try {
      return resource.getFile().getAbsolutePath() + "/";
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Long stringToLong(String input) {
    long longValue = 0;
    try {
      longValue = Long.parseLong(input);
    } catch (Exception e) {
//      logger.info("Error while convert String to long :" + e);
    }
    return longValue;
  }

  public static BigInteger stringToBigInteger(String input) {
    BigInteger bigIntegerValue = null;
    try {
      bigIntegerValue = new BigInteger(input);
    } catch (Exception e) {
//      logger.info("Error while convert String to BigInteger :" + e);
    }
    return bigIntegerValue;
  }

  public static BigInteger integerToBiginteger(Integer input) {
    BigInteger bigIntegerValue = null;
    try {
      bigIntegerValue = BigInteger.valueOf(input);
    } catch (Exception e) {
//      logger.info("Error while convert Integer to BigInteger :" + e);
    }
    return bigIntegerValue;
  }

  public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
    return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
  }

  public static Date monthStartDate(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, 1);
    return c.getTime();
  }

  public static Date monthEndDate(Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    return c.getTime();
  }

  public static int getWeekOfYear(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.WEEK_OF_YEAR);
  }

  public static Date convertToDateViaSqlDate(LocalDate dateToConvert) {
    return java.sql.Date.valueOf(dateToConvert);
  }

  public static Date convertToDateViaInstant(LocalDate dateToConvert) {
    return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static <E extends Enum<E>> E lookupEnum(Class<E> e, String id) {
    E r;
    try {
      r = Enum.valueOf(e, id);
    } catch (IllegalArgumentException ex) {
      throw new RuntimeException("Invalid value for enum " + e.getSimpleName() + ": " + id);
    }
    return r;
  }

  public static Long providePrimaryKeyByMaxId(Long primaryKey) {
    return primaryKey == null ? 1 : primaryKey + 1;
  }

  public static JsonNode provideBodyFrmStringResponse(ResponseEntity<String> responseEntity) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jasonbody = objectMapper.readTree(responseEntity.getBody()).get("data");
    return jasonbody;
  }

  public static URI provideRequestUrl(String delegatorUri, String appBasePath, String apiUri)
          throws URISyntaxException {
    String url = delegatorUri + appBasePath + apiUri;
    return new URI(url);
  }

  // Non api
  public void finallyOutputStream(ByteArrayOutputStream baos) {
    if (baos != null) {
      try {
        baos.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

