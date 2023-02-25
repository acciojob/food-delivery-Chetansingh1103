package com.driver.service.impl;

import com.driver.io.entity.FoodEntity;
import com.driver.io.repository.FoodRepository;
import com.driver.io.repository.OrderRepository;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;

    @Override
    public FoodDto createFood(FoodDto foodDto) {

            FoodEntity foodEntity = new FoodEntity();
            foodEntity.setFoodName(foodDto.getFoodName());
            foodEntity.setFoodCategory(foodDto.getFoodCategory());
            foodEntity.setFoodPrice(foodDto.getFoodPrice());

            foodRepository.save(foodEntity);

            foodDto.setFoodId(foodEntity.getFoodId());
            foodDto.setId(foodEntity.getId());

            return foodDto;
    }

    @Override
    public FoodDto getFoodById(String foodId) throws NullPointerException {

        if(foodRepository.findByFoodId(foodId) == null){
            throw new NullPointerException();
        }
            FoodEntity foodEntity = foodRepository.findByFoodId(foodId);


            FoodDto foodDto = new FoodDto();
            foodDto.setId(foodEntity.getId());
            foodDto.setFoodCategory(foodEntity.getFoodCategory());
            foodDto.setFoodName(foodEntity.getFoodName());
            foodDto.setFoodPrice(foodEntity.getFoodPrice());
            foodDto.setFoodId(foodEntity.getFoodId());

            return foodDto;


    }

    @Override
    public FoodDto updateFoodDetails(String foodId, FoodDto foodDetails) throws NullPointerException {

        if(foodRepository.findByFoodId(foodId) == null){
            throw new NullPointerException();
        }
            FoodEntity foodEntity = foodRepository.findByFoodId(foodId);

            foodEntity.setFoodPrice(foodDetails.getFoodPrice());
            foodEntity.setFoodCategory(foodDetails.getFoodCategory());
            foodEntity.setFoodName(foodDetails.getFoodName());

            foodRepository.save(foodEntity);


            // creating dto and returning
            FoodDto foodDto = new FoodDto();
            foodDto.setId(foodEntity.getId());
            foodDto.setFoodCategory(foodEntity.getFoodCategory());
            foodDto.setFoodName(foodEntity.getFoodName());
            foodDto.setFoodPrice(foodEntity.getFoodPrice());
            foodDto.setFoodId(foodEntity.getFoodId());

            return foodDto;
        }

    @Override
    public void deleteFoodItem(String foodId) throws NullPointerException {
        if(foodRepository.findByFoodId(foodId) == null){
            throw new NullPointerException();
        }
            FoodEntity foodEntity = foodRepository.findByFoodId(foodId);


            foodRepository.delete(foodEntity);

    }

    @Override
    public List<FoodDto> getFoods() {
        List<FoodEntity> foodEntityList = (List<FoodEntity>) foodRepository.findAll();

        List<FoodDto> foodDtoList = new ArrayList<>();

        for(FoodEntity foodEntity : foodEntityList){
            FoodDto foodDto = new FoodDto();
            foodDto.setId(foodEntity.getId());
            foodDto.setFoodCategory(foodEntity.getFoodCategory());
            foodDto.setFoodName(foodEntity.getFoodName());
            foodDto.setFoodPrice(foodEntity.getFoodPrice());
            foodDto.setFoodId(foodEntity.getFoodId());

            foodDtoList.add(foodDto);
        }

        return foodDtoList;
    }
}