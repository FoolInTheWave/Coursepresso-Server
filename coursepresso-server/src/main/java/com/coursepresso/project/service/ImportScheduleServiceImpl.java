/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coursepresso.project.service;

import com.coursepresso.project.entity.CourseSection;
import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.CourseRepository;
import com.coursepresso.project.repository.CourseSectionRepository;
import com.coursepresso.project.repository.DepartmentRepository;
import com.coursepresso.project.repository.MeetingDayRepository;
import com.coursepresso.project.repository.ProfessorRepository;
import com.coursepresso.project.repository.RoomRepository;
import com.coursepresso.project.repository.TermRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Steve
 */
public class ImportScheduleServiceImpl implements ImportScheduleService {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(
          ImportScheduleServiceImpl.class
  );

  @Inject
  private TermRepository termRepository;
  @Inject
  private DepartmentRepository departmentRepository;
  @Inject
  private CourseRepository courseRepository;
  @Inject
  private CourseSectionRepository courseSectionRepository;
  @Inject
  private ProfessorRepository professorRepository;
  @Inject
  private MeetingDayRepository meetingDayRepository;
  @Inject
  private RoomRepository roomRepository;

  @Override
  public void importSchedule(Term term, String line) {

    String splitBy = ",";
    String prevCourseNumber = "";
    int sectionNumber;

    try {
      sectionNumber = 1;

      String[] column = line.split(splitBy);

      CourseSection courseSection = new CourseSection();

      String[] courseNum = column[0].split("\\*");
      courseSection.setCourse(
              courseRepository.findByCourseNumber(courseNum[0] + courseNum[1])
      );

      courseSection.setSectionNumber(Integer.parseInt(column[4]));
      courseSection.setAvailable(true);
      courseSection.setCapacity(Integer.parseInt(column[3]));
      courseSection.setSeatsAvailable(courseSection.getCapacity());
      courseSection.setStatus("Open");
      courseSection.setTerm(term);
      courseSection.setStudentCount(Integer.parseInt(column[2]));
      courseSection.setType(column[6]);

      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      courseSection.setStartDate(formatter.parse(column[10]));
      courseSection.setEndDate(formatter.parse(column[11]));

      courseSection.setDepartment(
              departmentRepository.findByAbbreviation(courseNum[0])
      );

      courseSection.setProfessor(
              professorRepository.findById(Integer.parseInt(column[1]))
      );

      log.info(courseNum[0] + courseNum[1]);
      log.info(courseSection.getCourse().getCourseNumber());

      courseSection = courseSectionRepository.save(courseSection);

      String[] daysToMeet = column[13].split(";");

      for (String days : daysToMeet) {

        MeetingDay day = new MeetingDay();

        DateFormat df = new SimpleDateFormat("hh:mma");
        String[] times = column[12].split("-");
        day.setStartTime(df.parse(times[0]));
        day.setEndTime(df.parse(times[1]));
        day.setDay(days);

        String[] rooms = column[9].split(";");
        day.setRoom(
                roomRepository.findOne(rooms[0])
        );

        // Save MeetingDays for CourseSection
        day.setCourseSection(courseSection);
        day.setTerm(courseSection.getTerm());

        meetingDayRepository.save(day);

      }
    } catch (ParseException ex) {
    }
  }
}
