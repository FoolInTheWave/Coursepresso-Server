package com.coursepresso.project.service;

import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Room;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.RoomRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Caleb Miller, Steve Foco
 */
@Service
public class ConflictServiceImpl implements ConflictService {
  
  private static final Logger log = LoggerFactory.getLogger(
      ConflictServiceImpl.class
  );
    
  @Inject
  private RoomRepository roomRepository;
  
  private List<Room> rooms = new ArrayList<>();;
  private List<MeetingDay> meetingDays = new ArrayList<>();;
  private Set<String> conflicts = new HashSet<>();
  
  @Override
  public List<String> getConflicts(Term term) {
       
    rooms = roomRepository.getRoomsWithMeetingDays();
    
    for(Room room : rooms) {
      if(room.getRoomNumber().equals("A105")) {

        meetingDays = room.getMeetingDayList();
        
        for(MeetingDay meetingDay : meetingDays) {
                      
          for(MeetingDay meetingDayToCompare : meetingDays) {

            if(!Objects.equals(meetingDay.getId(), meetingDayToCompare.getId())) {

              if((meetingDayToCompare.getStartTime().before(meetingDay.getEndTime())) && 
                 (meetingDayToCompare.getEndTime().after(meetingDay.getStartTime()))) {

                conflicts.add(
                    meetingDay.getCourseSectionId().toString() + "#" + 
                    meetingDayToCompare.getCourseSectionId().toString()
                );

              }
            }
          }
        }
      }
    }
    
    return new ArrayList<>(conflicts);
  }
}