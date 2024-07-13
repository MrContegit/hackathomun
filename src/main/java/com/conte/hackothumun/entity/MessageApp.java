package com.conte.hackothumun.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;
    private String senderID;
    private String receiverID;
    private String content;
    private Timestamp timestamp;
    @ManyToOne
    private UserApp sender;
    @ManyToOne
    private UserApp receiver;

}
