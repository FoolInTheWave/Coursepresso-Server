package com.coursepresso.project.service;

import com.coursepresso.project.entity.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Caleb Miller
 */
@Service
public class SearchServiceImpl implements SearchService {
  
  private static final Logger log = LoggerFactory.getLogger(
      SearchServiceImpl.class
  );
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Override
  public List<CourseSection> searchSections(Map<String, Object> params) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery();
    Root<CourseSection> section = cq.from(CourseSection.class);
    Join<CourseSection, MeetingDay> day = section.join("meetingDayList");
    Join<CourseSection, Course> course = section.join("courseNumber");

    List<Predicate> andClauses = new ArrayList<>();
    List<Predicate> orClauses = new ArrayList<>();

    if (params.containsKey("department")) {
      andClauses.add(cb.equal(
              section.get("department"),
              (Department) params.get("department"))
      );
    }
    if (params.containsKey("term")) {
      andClauses.add(cb.equal(
              section.get("term"),
              (Term) params.get("term"))
      );
    }
    if (params.containsKey("course")) {
      andClauses.add(cb.equal(
              section.get("courseNumber"),
              (Course) params.get("course"))
      );
    }
    if (params.containsKey("professor")) {
      andClauses.add(cb.equal(
              section.get("professorId"),
              (Professor) params.get("professor"))
      );
    }
    if (params.containsKey("courseLevel")) {
      andClauses.add(cb.equal(
              course.get("academicLevel"),
              (String) params.get("courseLevel"))
      );
    }
    if (params.containsKey("courseNumber")) {
      andClauses.add(cb.equal(
              course.get("courseNumber"),
              (String) params.get("courseNumber"))
      );
    }
    if (params.containsKey("lineNumber")) {
      andClauses.add(cb.equal(
              section.get("id"),
              (Integer) params.get("lineNumber"))
      );
    }
    if (params.containsKey("credits")) {
      andClauses.add(cb.equal(
              course.get("credits"),
              (Integer) params.get("credits"))
      );
    }
    if (params.containsKey("monday")) {
      orClauses.add(cb.or(cb.equal(day.get("day"), "M")));
    }
    if (params.containsKey("tuesday")) {
      orClauses.add(cb.or(cb.equal(day.get("day"), "T")));
    }
    if (params.containsKey("wednesday")) {
      orClauses.add(cb.or(cb.equal(day.get("day"), "W")));
    }
    if (params.containsKey("thursday")) {
      orClauses.add(cb.or(cb.equal(day.get("day"), "TH")));
    }
    if (params.containsKey("friday")) {
      orClauses.add(cb.or(cb.equal(day.get("day"), "F")));
    }

    if (!orClauses.isEmpty()) {
      andClauses.add(cb.equal(day.get("courseSectionId"), section));
    }

    Predicate[] orArray = new Predicate[orClauses.size()];
    orArray = orClauses.toArray(orArray);
    Predicate orClause = cb.or(orArray);

    Predicate[] andArray = new Predicate[andClauses.size()];
    andArray = andClauses.toArray(andArray);
    Predicate andClause = cb.and(andArray);

    if ((!andClauses.isEmpty()) && (!orClauses.isEmpty())) {
      cq.select(section).where(andClause, orClause).distinct(true);
    } else {
      cq.select(section).where(andClause).distinct(true);
    }
    
    log.debug(cq.toString());
    
    List<CourseSection> results = entityManager.createQuery(cq).getResultList();
    
    log.debug(results.toString());
    
    return results;
  }
  
}
