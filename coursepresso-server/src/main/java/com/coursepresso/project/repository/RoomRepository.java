package com.coursepresso.project.repository;

import com.coursepresso.project.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Caleb Miller
 */
@Repository
public interface RoomRepository extends CrudRepository<Room, String> {
  
  @Query("SELECT r FROM Room r JOIN FETCH r.meetingDayList")
  public List<Room> getRoomsWithMeetingDays();
}
