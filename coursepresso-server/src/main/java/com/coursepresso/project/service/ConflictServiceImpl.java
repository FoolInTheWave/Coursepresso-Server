package com.coursepresso.project.service;

import com.coursepresso.project.entity.Conflict;
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
 * @author Steve Foco, Caleb Miller
 */
@Service
public class ConflictServiceImpl implements ConflictService {

  private static final Logger log = LoggerFactory.getLogger(
      ConflictServiceImpl.class
  );

  @Inject
  private RoomRepository roomRepository;

  private List<Room> rooms = new ArrayList<>();
  private List<MeetingDay> meetingDays = new ArrayList<>();

  @Override
  public List<Conflict> getConflicts(Term term) {
    Set<Conflict> conflicts = new HashSet<>();
    rooms = roomRepository.getRoomsWithMeetingDays();

    for (Room room : rooms) {
      if (room.getRoomNumber().equals("A105") || room.getRoomNumber().equals("A102")) {

        meetingDays = room.getMeetingDayList();

        for (MeetingDay meetingDay : meetingDays) {

          for (MeetingDay meetingDayToCompare : meetingDays) {

            if (!Objects.equals(meetingDay.getId(), meetingDayToCompare.getId())) {

              if ((meetingDayToCompare.getDay().equals(meetingDay.getDay()))
                  && (meetingDayToCompare.getStartTime().before(meetingDay.getEndTime()))
                  && (meetingDayToCompare.getEndTime().after(meetingDay.getStartTime()))) {

                // Clear meeting day list to avoid stack overflow on client
                meetingDay.getCourseSection().setMeetingDayList(null);

                // Build conflict object and add it to conflicts collection
                conflicts.add(new Conflict(
                    meetingDay.getCourseSection(),
                    "Conflicts with line #: "
                    + meetingDayToCompare.getCourseSection().getId()
                ));
              }
            }
          }
        }
      }
    }

    return new ArrayList<>(conflicts);
  }
}
