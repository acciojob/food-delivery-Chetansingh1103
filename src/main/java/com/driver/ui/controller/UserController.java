package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.model.response.UserResponse;
import com.driver.service.impl.UserServiceImpl;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@GetMapping("/getUser")
	public UserResponse getUser(@RequestParam("userId") String userid) throws Exception{
		try {
			UserDto userDto = userService.getUserByUserId(userid);
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(userDto.getUserId());
			userResponse.setEmail(userDto.getEmail());
			userResponse.setFirstName(userDto.getFirstName());
			userResponse.setLastName(userDto.getLastName());
			return userResponse;
		}
		catch (Exception e){
			throw new Exception();
		}
	}

	@PostMapping("/createUser")
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{

		try {
			UserDto userDto = new UserDto();
			userDto.setFirstName(userDetails.getFirstName());
			userDto.setLastName(userDetails.getLastName());
			userDto.setEmail(userDetails.getEmail());
			userDto = userService.createUser(userDto);

			// converting userDto into UserResponse
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(userDto.getUserId());
			userResponse.setEmail(userDto.getEmail());
			userResponse.setFirstName(userDto.getFirstName());
			userResponse.setLastName(userDto.getLastName());

			return userResponse;
		}
		catch (Exception e){
			throw new Exception();
		}
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{

		try {
			UserDto userDto = new UserDto();
			userDto.setFirstName(userDetails.getFirstName());
			userDto.setLastName(userDetails.getLastName());
			userDto.setEmail(userDetails.getEmail());

			userDto = userService.updateUser(id,userDto);


			// converting userDto into UserResponse if the id is correct
			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(userDto.getUserId());
			userResponse.setEmail(userDto.getEmail());
			userResponse.setFirstName(userDto.getFirstName());
			userResponse.setLastName(userDto.getLastName());

			return userResponse;
		}
		catch (Exception e){
			throw new Exception();
		}
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		try {
			userService.getUserByUserId(id);
			// if there is no exception then delete the user
			userService.deleteUser(id);
			operationStatusModel.setOperationName(String.valueOf(RequestOperationName.DELETE));
			operationStatusModel.setOperationResult(String.valueOf(RequestOperationStatus.SUCCESS));

			return operationStatusModel;
		}
		catch (Exception e){
			operationStatusModel.setOperationName(String.valueOf(RequestOperationName.DELETE));
			operationStatusModel.setOperationResult(String.valueOf(RequestOperationStatus.ERROR));
			return operationStatusModel;
		}
	}
	
	@GetMapping("/getAllUsers")
	public List<UserResponse> getUsers(){
		List<UserResponse> userResponseList = new ArrayList<>();
		List<UserDto> userDtoList = userService.getUsers();

		for(UserDto userDto : userDtoList){

			UserResponse userResponse = new UserResponse();
			userResponse.setUserId(userDto.getUserId());
			userResponse.setEmail(userDto.getEmail());
			userResponse.setFirstName(userDto.getFirstName());
			userResponse.setLastName(userDto.getLastName());

			userResponseList.add(userResponse);
		}

		return userResponseList;
	}
	
}
