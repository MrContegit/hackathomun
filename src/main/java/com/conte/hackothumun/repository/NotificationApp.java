package com.conte.hackothumun.repository;

import com.conte.hackothumun.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationApp extends JpaRepository<Notification, Long> {
}
