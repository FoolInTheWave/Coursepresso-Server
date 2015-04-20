package com.coursepresso.project.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
  public String exportCourseSections(String term) {
    String query = "select * from course_sections";
    StringBuilder sb = new StringBuilder();

    try {
      Connection conn = dataSource.getConnection();
      PreparedStatement pstmt = conn.prepareStatement(
          "SELECT * FROM course_sections WHERE term like ?"
      );
      pstmt.setString(1, term + "%");
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        sb.append(rs.getInt("id"));
        sb.append(';');
        sb.append(rs.getString("course_number"));
        sb.append(';');
        sb.append(rs.getInt("section_number"));
        sb.append(';');
        sb.append(rs.getBoolean("available"));
        sb.append(';');
        sb.append(rs.getInt("capacity"));
        sb.append(';');
        sb.append(rs.getInt("seats_available"));
        sb.append(';');
        sb.append(rs.getString("status"));
        sb.append(';');
        sb.append(rs.getString("term"));
        sb.append(';');
        sb.append(rs.getInt("student_count"));
        sb.append(';');
        sb.append(rs.getString("type"));
        sb.append(';');
        sb.append(rs.getDate("start_date"));
        sb.append(';');
        sb.append(rs.getDate("end_date"));
        sb.append(';');
        sb.append(rs.getString("department"));
        sb.append(';');
        sb.append(rs.getInt("professor_id"));
        sb.append(';');
        sb.append(rs.getTimestamp("updated_at"));
        sb.append('\n');
      }
      
      conn.close();

      log.debug("Course section CSV file created successfully");
    } catch (Exception e) {
      log.error("Course section CSV file creation failed: ", e);
    }

    return sb.toString();
  }
}
