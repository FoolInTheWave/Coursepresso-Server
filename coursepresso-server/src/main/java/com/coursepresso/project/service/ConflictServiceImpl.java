package com.coursepresso.project.service;

import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Room;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Caleb Miller
 */
@Service
public class ConflictServiceImpl implements ConflictService {
  
  private static final Logger log = LoggerFactory.getLogger(
      ConflictServiceImpl.class
  );
    
  @Inject
  private RoomRepository roomRepository;
  
  private List<Room> rooms;
  private List<MeetingDay> meetingDays;
  private List<String> conflicts;
  
  @Override
  public List<String> getConflicts(Term term) {
    
    conflicts = new ArrayList<>();
    
    conflicts.add("BLAH");
   
    return conflicts;
  }
}