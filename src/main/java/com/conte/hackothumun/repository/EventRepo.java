package com.conte.hackothumun.repository;
import com.conte.hackothumun.entity.Event;
import com.conte.hackothumun.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {
    Page<Event> findByTitleContaining(String title, Pageable pageable);
    Event findByTitle(String title);

    @Query("select ev.inviteList from Event ev where str(ev.eventId) like :x")
    List<UserApp> findInviteByEvent(@Param("x") String eventId);

    @Query("select ev from Event ev where ev.organizerId.email like :x")
    List<Event> findByOrganizerId(@Param("x") String email);

}
