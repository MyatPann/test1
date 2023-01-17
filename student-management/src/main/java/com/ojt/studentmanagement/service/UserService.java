package com.ojt.studentmanagement.service;



import java.util.List;



import com.ojt.studentmanagement.entity.User;



public interface UserService {
    
    List<User> getByKeyword(String keyword);
    List<User> getAllUsers();



}