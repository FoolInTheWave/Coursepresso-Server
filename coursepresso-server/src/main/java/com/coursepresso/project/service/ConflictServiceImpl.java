package com.coursepresso.project.service;

import com.coursepresso.project.entity.MeetingDay;
import com.coursepresso.project.entity.Room;
import com.coursepresso.project.entity.Term;
import com.coursepresso.project.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Caleb Miller
 */
@Service
public class ConflictServiceImpl implements ConflictService {
  
  @Inject
  private RoomRepository roomRepository;
  
  private static List<Room> rooms;
  private static List<MeetingDay> meetingDays;
  private static ArrayList conflicts;
  
  @Override
  public List<String> getConflicts(Term term) {
    
    rooms = roomRepository.getRoomsWithMeetingDays();
    
    for(Room room : rooms) {
      meetingDays = room.getMeetingDayList();
      
      for(MeetingDay meetingDay : meetingDays) {
        for(MeetingDay meetingDayToCompare : meetingDays) {
          
          if(!Objects.equals(meetingDay.getId(), meetingDayToCompare.getId())) {
            if(  
              (meetingDay.getStartTime().before(meetingDayToCompare.getEndTime())) 
              &&
              (meetingDayToCompare.getStartTime().before(meetingDay.getEndTime()))  
            ) 
            {
              conflicts.add(meetingDay.getCourseSectionId().toString() + " " + meetingDayToCompare.getCourseSectionId().toString());
            }
          }
        }
      }
    }
    
    return conflicts;
  }
  
}
