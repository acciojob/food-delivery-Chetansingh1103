package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.impl.FoodServiceImpl;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {

	@Autowired
	FoodServiceImpl foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id){
		try {
			FoodDto foodDto = foodService.getFoodById(id);

			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			foodDetailsResponse.setFoodId(foodDto.getFoodId());
			foodDetailsResponse.setFoodName(foodDto.getFoodName());
			foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
			foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());

			return foodDetailsResponse;
		}
		catch (Exception e){
			return new FoodDetailsResponse();
		}
	}

	@PostMapping("/createFood")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {

			FoodDto foodDto = new FoodDto();
			foodDto.setFoodName(foodDetails.getFoodName());
			foodDto.setFoodPrice(foodDetails.getFoodPrice());
			foodDto.setFoodCategory(foodDetails.getFoodCategory());

			foodDto = foodService.createFood(foodDto);

			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			foodDetailsResponse.setFoodId(foodDto.getFoodId());
			foodDetailsResponse.setFoodName(foodDto.getFoodName());
			foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
			foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());

			return foodDetailsResponse;


	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails){

		try {
			FoodDto foodDto = new FoodDto();
			foodDto.setFoodName(foodDetails.getFoodName());
			foodDto.setFoodPrice(foodDetails.getFoodPrice());
			foodDto.setFoodCategory(foodDetails.getFoodCategory());

			foodDto = foodService.updateFoodDetails(id,foodDto);

			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			foodDetailsResponse.setFoodId(foodDto.getFoodId());
			foodDetailsResponse.setFoodName(foodDto.getFoodName());
			foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
			foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());

			return foodDetailsResponse;
		}
		catch (Exception e){
			return new FoodDetailsResponse();
		}
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id){

		try {
			OperationStatusModel operationStatusModel = new OperationStatusModel();
			foodService.deleteFoodItem(id);
			operationStatusModel.setOperationResult(String.valueOf(RequestOperationStatus.SUCCESS));
			operationStatusModel.setOperationName(String.valueOf(RequestOperationName.DELETE));

			return operationStatusModel;
		}
		catch (Exception e){
			OperationStatusModel operationStatusModel = new OperationStatusModel();
			operationStatusModel.setOperationResult(String.valueOf(RequestOperationStatus.ERROR));
			operationStatusModel.setOperationName(String.valueOf(RequestOperationName.DELETE));
			return operationStatusModel;
		}
	}
	
	@GetMapping("/getAllFood")
	public List<FoodDetailsResponse> getFoods() {

		List<FoodDto> foodDtoList = foodService.getFoods();
		List<FoodDetailsResponse> foodDetailsResponseList = new ArrayList<>();

		for(FoodDto foodDto : foodDtoList){

			FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
			foodDetailsResponse.setFoodId(foodDto.getFoodId());
			foodDetailsResponse.setFoodName(foodDto.getFoodName());
			foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
			foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());

			foodDetailsResponseList.add(foodDetailsResponse);
		}

		return foodDetailsResponseList;
	}
}
