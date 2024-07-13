package com.conte.hackothumun.service;

public interface UserAppService <T>{
    T login(T user);
    void logout();
}
