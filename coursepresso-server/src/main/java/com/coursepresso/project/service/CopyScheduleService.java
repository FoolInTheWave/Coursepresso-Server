package com.coursepresso.project.service;

import com.coursepresso.project.entity.Term;

/**
 *
 * @author Steve Foco
 */
public interface CopyScheduleService {
  
  void copySchedule(Term prevTerm, Term newTerm);
}
