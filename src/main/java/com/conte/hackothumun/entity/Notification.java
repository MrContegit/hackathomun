package com.conte.hackothumun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;
    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false, updatable = false)
    private UserApp userID;
    private String message;
    private Date date;
    private boolean isRead;
}
