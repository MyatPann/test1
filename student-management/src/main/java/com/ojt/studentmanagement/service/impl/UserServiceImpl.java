package com.ojt.studentmanagement.service.impl;



import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.ojt.studentmanagement.entity.User;
import com.ojt.studentmanagement.repository.UserRepository;
import com.ojt.studentmanagement.service.UserService;



@Service
public class UserServiceImpl implements UserService{
         
    @Autowired
    private UserRepository userRepository;



   @Override
    public List<User> getByKeyword(String keyword) {
        return userRepository.findByKeyword(keyword);
    }



   @Override
    public List<User> getAllUsers() {
          List<User> list =  (List<User>)userRepository.findAll();
          return list;
    }



}