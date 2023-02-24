package com.driver.io.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.driver.io.entity.FoodEntity;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<FoodEntity, Long> {
	FoodEntity findByFoodId(String foodId);

	@Query(value = "select * from foods f where f.food_name=:name",nativeQuery = true)
	FoodEntity findByFoodName(String name);

	@Query(value = "select * from foods",nativeQuery = true)
	List<FoodEntity> findAllFoods();
}
