package com.coursepresso.project.service;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.MeetingDay;
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

  @Inject
  private CourseSectionRepository courseSectionRepository;

  private ArrayList<CourseSection> newSections;

  private static final Logger log = LoggerFactory.getLogger(
      CopyScheduleServiceImpl.class
  );

  @Override
  public void copySchedule(Term prevTerm, Term newTerm) {
    log.info("TEST 1");
    newSections = new ArrayList<>();

    for (CourseSection section : prevTerm.getCourseSectionList()) {

      for (MeetingDay day : section.getMeetingDayList()) {
        day.setId(null);
      }

      section.setId(null);
      section.setTerm(newTerm);
      newSections.add(section);
    }

    courseSectionRepository.save(newSections);
  }
}
