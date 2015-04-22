package com.coursepresso.project.service;

import com.coursepresso.project.entity.Conflict;
import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Professor;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.MeetingDayRepository;
import com.coursepresso.project.repository.ProfessorRepository;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Steve Foco, Caleb Miller
 */
@Service
public class ConflictServiceImpl implements ConflictService {

  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private MeetingDayRepository meetingDayRepository;
  @Inject
  private ProfessorRepository professorRepository;

  private static final Logger log = LoggerFactory.getLogger(
      ConflictServiceImpl.class
  );

  @Override
  @Transactional
  public List<Conflict> getConflicts(Term term) {
    Set<Conflict> conflicts = new HashSet<>();
    List<MeetingDay> meetingDays = meetingDayRepository.findAllByTermWithRoom(term);

    // Initialize each course section object
    for (MeetingDay day : meetingDays) {
      day.getCourseSection().getId();
    }

    for (MeetingDay meetingDay : meetingDays) {

      for (MeetingDay meetingDayToCompare : meetingDays) {

        if (!meetingDay.equals(meetingDayToCompare)) {

          if (meetingDayToCompare.getRoom().equals(meetingDay.getRoom())
              && meetingDayToCompare.getDay().equals(meetingDay.getDay())
              && (meetingDayToCompare.getStartTime().before(
                  meetingDay.getEndTime()))
              && (meetingDayToCompare.getEndTime().after(
                  meetingDay.getStartTime()
              ))) {

            // Clear meeting day list to avoid stack overflow on client
            meetingDay.getCourseSection().setMeetingDayList(null);

            // Build conflict object and add it to conflicts collection
            conflicts.add(new Conflict(
                meetingDay.getCourseSection(),
                "Conflicts with line #: "
                + meetingDayToCompare.getCourseSection().getId()
                + " Room"
            ));
          }
        }
      }
    }

    List<Professor> professors = Lists.newArrayList(professorRepository.findAll());
    // Initialize each course section list
    for (Professor professor : professors) {
      professor.getCourseSectionList().size();
    }
    
    List<MeetingDay> professorMeetingDays = new ArrayList<>();

    for (Professor professor : professors) {
      professorMeetingDays.clear();
      
      // Get list of meeting days for the professor
      for (CourseSection courseSection : professor.getCourseSectionList()) {
        
        for (MeetingDay day : courseSection.getMeetingDayList()) {
          System.out.println(day.getId().toString() + day.getCourseSection() + day.getRoom());
        }
        
        for (MeetingDay day : courseSection.getMeetingDayList()) {
          day = meetingDayRepository.findOne(day.getId());
          // Initialize lazy loaded objects
          day.getRoom().getRoomNumber();
          day.setCourseSection(courseSection);
          
          if (day.getTerm().equals(term)) {
            professorMeetingDays.add(day);
          }
        }
      }

      for (MeetingDay meetingDay : professorMeetingDays) {

        for (MeetingDay meetingDayToCompare : professorMeetingDays) {

          if (!(meetingDay.equals(meetingDayToCompare))) {

            if (meetingDayToCompare.getRoom().equals(meetingDay.getRoom())
                && meetingDayToCompare.getDay().equals(meetingDay.getDay())
                && (meetingDayToCompare.getStartTime().before(
                    meetingDay.getEndTime()))
                && (meetingDayToCompare.getEndTime().after(
                    meetingDay.getStartTime()
                ))) {

              // Clear meeting day list to avoid stack overflow on client
              meetingDay.getCourseSection().setMeetingDayList(null);

              // Build conflict object and add it to conflicts collection
              conflicts.add(new Conflict(
                  meetingDay.getCourseSection(),
                  "Conflicts with line #: "
                  + meetingDayToCompare.getCourseSection().getId()
                  + " Professor"
              ));
            }
          }
        }
      }
    }
    return new ArrayList<>(conflicts);
  }
}
