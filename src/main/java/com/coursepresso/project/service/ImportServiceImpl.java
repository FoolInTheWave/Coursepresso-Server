package com.coursepresso.project.service;

import com.coursepresso.project.entity.*;
import com.coursepresso.project.repository.*;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Caleb Miller
 */
@Service
public class ImportServiceImpl implements ImportService {

  @Inject
  ApplianceRepository applianceRepository;
  @Inject
  AuthorityRepository authorityRepository;
  @Inject
  CourseRepository courseRepository;
  @Inject
  CourseSectionRepository courseSectionRepository;
  @Inject
  CoursePrerequisiteRepository coursePrerequisiteRepository;
  @Inject
  DepartmentRepository departmentRepository;
  @Inject
  MeetingDayRepository meetingDayRepository;
  @Inject
  ProfessorRepository professorRepository;
  @Inject
  RoomRepository roomRepository;
  @Inject
  TermRepository termRepository;
  @Inject
  UserRepository userRepository;

  @Override
  public void importAppliances(List<Appliance> appliances) {
    for (Appliance appliance : appliances) {
      // Set transient entities for each object to save
      appliance.setRoom(roomRepository.findOne(
          appliance.getRoom().getRoomNumber()
      ));
    }

    // Save objects to database using repository
    applianceRepository.save(appliances);
  }

  @Override
  public void importAuthorities(List<Authority> authorities) {
    for (Authority authority : authorities) {
      // Set transient entities for each object to save
      authority.setUser(userRepository.findOne(
          authority.getUser().getUsername()
      ));
    }

    // Save objects to database using repository
    authorityRepository.save(authorities);
  }

  @Override
  public void importCourses(List<Course> courses) {
    for (Course course : courses) {
      // Set transient entities for each object to save
      course.setDepartment(departmentRepository.findOne(
          course.getDepartment().getName()
      ));
    }

    // Save objects to database using repository
    courseRepository.save(courses);
  }

  @Override
  public void importCoursePrerequisites(List<CoursePrerequisite> prerequisites) {
    for (CoursePrerequisite prerequisite : prerequisites) {
      // Set transient entities for each object to save
      prerequisite.setCourse(courseRepository.findOne(
          prerequisite.getCourse().getCourseNumber()
      ));
      prerequisite.setPrerequisite(courseRepository.findOne(
          prerequisite.getPrerequisite().getCourseNumber()
      ));
    }

    // Save objects to database using repository
    coursePrerequisiteRepository.save(prerequisites);
  }

  @Override
  public void importCourseSections(List<CourseSection> courseSections) {
    for (CourseSection section : courseSections) {
      // Set transient entities for each object to save
      section.setCourse(courseRepository.findOne(
          section.getCourse().getCourseNumber()
      ));
      section.setDepartment(departmentRepository.findOne(
          section.getDepartment().getName()
      ));
      section.setProfessor(professorRepository.findOne(
          section.getProfessor().getId()
      ));
    }

    // Save objects to database using repository
    courseSectionRepository.save(courseSections);
  }

  @Override
  public void importMeetingDays(List<MeetingDay> meetingDays) {
    for (MeetingDay day : meetingDays) {
      // Set transient entities for each object to save
      day.setCourseSection(courseSectionRepository.findOne(
          day.getCourseSection().getId()
      ));
      day.setRoom(roomRepository.findOne(
          day.getRoom().getRoomNumber()
      ));
    }

    // Save objects to database using repository
    meetingDayRepository.save(meetingDays);
  }

  @Override
  public void importProfessors(List<Professor> professors) {
    for (Professor professor : professors) {
      // Set transient entities for each object to save
      professor.setDepartment(departmentRepository.findOne(
          professor.getDepartment().getName()
      ));
    }

    // Save objects to database using repository
    professorRepository.save(professors);
  }

  @Override
  public void importUsers(List<User> users) {
    for (User user : users) {
      // Set transient entities for each object to save
      user.setDepartment(departmentRepository.findOne(
          user.getDepartment().getName()
      ));
    }

    // Save objects to database using repository
    userRepository.save(users);
  }

}
