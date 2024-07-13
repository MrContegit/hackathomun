package com.conte.hackothumun.service;

public interface ServiceCRUD<T>{
     T select(Long id);
     T insert(T value);
     T update(T value);
     void delete(Long value);

}
