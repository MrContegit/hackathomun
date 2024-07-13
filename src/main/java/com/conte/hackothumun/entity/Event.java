//13c0c801-f600-4e16-a756-88aae6e50065

package com.conte.hackothumun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private String title;
    private boolean privated;
    private String category;
    private int capacity;
    private boolean approve;
    private String description;
    private Date datedeb;
    private Date datefin;
    private String location;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<FileApp> filePathList;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Billet> billetList;
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private UserApp organizerId;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<UserApp> participants;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<UserApp> inviteList;

}
