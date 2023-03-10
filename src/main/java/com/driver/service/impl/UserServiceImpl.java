package com.driver.service.impl;

import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {

           UserEntity userEntity= new UserEntity();
           userEntity.setFirstName(userDto.getFirstName());
           userEntity.setLastName(userDto.getLastName());
           userEntity.setEmail(userDto.getEmail());
           userRepository.save(userEntity);

           // again extracting the user details from db to get the id and userId details too
           userDto.setUserId(userEntity.getUserId());
           userDto.setId(userEntity.getId());
           return userDto;

    }

    @Override
    public UserDto getUser(String email) throws NullPointerException {

            UserEntity userEntity = userRepository.findByEmail(email);

            UserDto userDto = new UserDto();
            userDto.setId(userEntity.getId());
            userDto.setEmail(userEntity.getEmail());
            userDto.setLastName(userEntity.getLastName());
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setUserId(userEntity.getUserId());

            return userDto;


    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {


           UserEntity userEntity = userRepository.findByUserId(userId);

           // setting the userDto values to return it
           UserDto userDto = new UserDto();
           userDto.setId(userEntity.getId());
           userDto.setEmail(userEntity.getEmail());
           userDto.setLastName(userEntity.getLastName());
           userDto.setFirstName(userEntity.getFirstName());
           userDto.setUserId(userEntity.getUserId());

           return userDto;

    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) throws Exception {

        if(userRepository.isUserIdPresent(userId)){
            throw new NullPointerException();
        }
            UserEntity userEntity = userRepository.findByUserId(userId);


            userEntity.setFirstName(userDto.getFirstName());
            userEntity.setLastName(userDto.getLastName());
            userEntity.setEmail(userDto.getEmail());

            userRepository.save(userEntity);

            // setting the userDto values again to return it
            userDto.setId(userEntity.getId());
            userDto.setEmail(userEntity.getEmail());
            userDto.setLastName(userEntity.getLastName());
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setUserId(userEntity.getUserId());

            return userDto;

    }

    @Override
    public void deleteUser(String userId) throws NullPointerException {


            UserEntity userEntity = userRepository.findByUserId(userId);


            userRepository.delete(userEntity);

    }

    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntityList = (List<UserEntity>) userRepository.findAll();
        List<UserDto> userDtoList= new ArrayList<>();

        for (UserEntity userEntity : userEntityList) {
            UserDto userDto = new UserDto();
            userDto.setId(userEntity.getId());
            userDto.setEmail(userEntity.getEmail());
            userDto.setLastName(userEntity.getLastName());
            userDto.setFirstName(userEntity.getFirstName());
            userDto.setUserId(userEntity.getUserId());

            userDtoList.add(userDto);
        }

        return userDtoList;
    }
}