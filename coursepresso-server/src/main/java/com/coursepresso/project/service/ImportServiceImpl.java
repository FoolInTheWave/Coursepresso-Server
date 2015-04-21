package com.coursepresso.project.service;

import com.coursepresso.project.entity.*;
import com.coursepresso.project.repository.*;
import java.util.ArrayList;
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
  CourseRepository courseRepository;
  @Inject
  CourseSectionRepository courseSectionRepository;
  @Inject
  DepartmentRepository departmentRepository;
  @Inject
  ProfessorRepository professorRepository;
  @Inject
  TermRepository termRepository;

  @Override
  public void importCourseSections(List<CourseSection> courseSections) {
    for (CourseSection section : courseSections) {
      // Set transient entities for each section to save
      section.setCourse(courseRepository.findOne(
          section.getCourse().getCourseNumber())
      );
      section.setDepartment(departmentRepository.findOne(
          section.getDepartment().getName())
      );
      section.setProfessor(professorRepository.findOne(
          section.getProfessor().getId())
      );
    }
    
    // Save courses to database using repository
    courseSectionRepository.save(courseSections);
  }
;
}
