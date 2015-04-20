package com.coursepresso.project.service;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseSectionRepository;
import java.util.ArrayList;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Steve Foco
 */
@Service
public class CopyScheduleServiceImpl implements CopyScheduleService {
  
  private static final Logger log = LoggerFactory.getLogger(
      CopyScheduleServiceImpl.class
  );
  
  @Inject
  private CourseSectionRepository courseSectionRepository;
  
  private ArrayList<CourseSection> newSections;
  
  @Override
  public void copySchedule(Term prevTerm, Term newTerm) {
    log.info("TEST 1");
    
    for (CourseSection section : prevTerm.getCourseSectionList()) {
      section.setId(null);
      section.setTerm(newTerm);
      newSections.add(section);
    }
    
    courseSectionRepository.save(newSections);
  }
}
