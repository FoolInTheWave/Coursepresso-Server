package com.coursepresso.project.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Caleb Miller
 */
@Service
public class ExportServiceImpl implements ExportService {

  private static final Logger log = LoggerFactory.getLogger(
      ExportServiceImpl.class
  );

  @Inject
  private DriverManagerDataSource dataSource;

  @Override
  public List<String> getTableNames() {
    String query = "select table_name from information_schema.tables where table_schema='coursepresso'";
    List<String> results = new ArrayList<>();

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);

      while (rs.next()) {
        results.add(rs.getString(1));
      }
    } catch (SQLException e) {
      log.error("Get table names query failed: ", e);
    }

    return results;
  }

  @Override
  public String exportAppliances() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM appliances";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Appliance CSV file created successfully");
    } catch (Exception e) {
      log.error("Appliance CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportAuthorities() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM authorities";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Authority CSV file created successfully");
    } catch (Exception e) {
      log.error("Authority CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportCoursePrerequisites() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM course_prerequisites";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Course prerequisite CSV file created successfully");
    } catch (Exception e) {
      log.error("Course prerequisite CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportCourseSections(String term) {
    StringBuilder sb = new StringBuilder();

    try {
      Connection conn = dataSource.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
          "SELECT * FROM course_sections WHERE term like ?"
      );
      pstmt.setString(1, term + "%");
      ResultSet rs = pstmt.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Course section CSV file created successfully");
    } catch (Exception e) {
      log.error("Course section CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportCourses() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM courses";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Course CSV file created successfully");
    } catch (Exception e) {
      log.error("Course CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportDepartments() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM departments";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Department CSV file created successfully");
    } catch (Exception e) {
      log.error("Department CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportGroupAuthorities() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM group_authorities";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Group authority CSV file created successfully");
    } catch (Exception e) {
      log.error("Group authority CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportGroupMembers() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM group_members";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Group members CSV file created successfully");
    } catch (Exception e) {
      log.error("Group members CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportGroups() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM groups";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Group CSV file created successfully");
    } catch (Exception e) {
      log.error("Group CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportMeetingDays(String term) {
    StringBuilder sb = new StringBuilder();

    try {
      Connection conn = dataSource.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
          "SELECT * FROM meeting_days WHERE term like ?"
      );
      pstmt.setString(1, term + "%");
      ResultSet rs = pstmt.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Meeting day CSV file created successfully");
    } catch (Exception e) {
      log.error("Meeting day CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportProfessors() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM professors";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Professor CSV file created successfully");
    } catch (Exception e) {
      log.error("Professor CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportRooms() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM rooms";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Room CSV file created successfully");
    } catch (Exception e) {
      log.error("Room CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportTerms() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM terms";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("Term CSV file created successfully");
    } catch (Exception e) {
      log.error("Term CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  @Override
  public String exportUsers() {
    StringBuilder sb = new StringBuilder();
    String query = "SELECT * FROM users";

    try {
      Connection conn = dataSource.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();

      sb.append(getDelimitedColumns(rsmd, rsmd.getColumnCount()));
      sb.append(getDelimitedRows(rs, rsmd.getColumnCount()));

      conn.close();

      log.debug("User CSV file created successfully");
    } catch (Exception e) {
      log.error("User CSV file creation failed: ", e);
    }

    return sb.toString();
  }

  public String getDelimitedColumns(ResultSetMetaData rsmd, int numColumns)
      throws SQLException {
    StringBuilder sb = new StringBuilder();

    for (int i = 1; i <= numColumns; i++) {
      sb.append(rsmd.getColumnName(i));
      sb.append(';');
    }
    sb.setLength(sb.length() - 1);
    sb.append('\n');

    return sb.toString();
  }

  public String getDelimitedRows(ResultSet rs, int numColumns)
      throws SQLException {
    StringBuilder sb = new StringBuilder();

    while (rs.next()) {
      for (int i = 1; i <= numColumns; i++) {
        sb.append(rs.getString(i));
        sb.append(';');
      }
      sb.setLength(sb.length() - 1);
      sb.append('\n');
    }

    return sb.toString();
  }
}
