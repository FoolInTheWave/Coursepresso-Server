package com.coursepresso.project.service;

import com.coursepresso.project.entity.Term;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Caleb Miller
 */
@Service
public class ConflictServiceImpl implements ConflictService {
  
  @Override
  public List<String> getConflicts(Term term) {
    //TODO
    return new ArrayList<>();
  }
  
}
