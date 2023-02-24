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
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto = foodService.getFoodById(id);

		FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();
		foodDetailsResponse.setFoodId(foodDto.getFoodId());
		foodDetailsResponse.setFoodName(foodDto.getFoodName());
		foodDetailsResponse.setFoodCategory(foodDto.getFoodCategory());
		foodDetailsResponse.setFoodPrice(foodDto.getFoodPrice());

		return foodDetailsResponse;
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
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
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

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		try {
			foodService.getFoodById(id);
		}
		catch (Exception e){
			operationStatusModel.setOperationResult(String.valueOf(RequestOperationStatus.ERROR));
			operationStatusModel.setOperationName(String.valueOf(RequestOperationName.DELETE));
			return operationStatusModel;
		}

		foodService.deleteFoodItem(id);
		operationStatusModel.setOperationResult(String.valueOf(RequestOperationStatus.SUCCESS));
		operationStatusModel.setOperationName(String.valueOf(RequestOperationName.DELETE));

		return operationStatusModel;
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
