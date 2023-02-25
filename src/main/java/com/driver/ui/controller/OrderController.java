package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.io.entity.OrderEntity;
import com.driver.model.request.OrderDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.OrderDetailsResponse;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.RequestOperationStatus;
import com.driver.service.impl.OrderServiceImpl;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	OrderServiceImpl orderService;

	@GetMapping(path="/{id}")
	public OrderDetailsResponse getOrder(@PathVariable String id) throws Exception{
		try {
			OrderDto orderDto = orderService.getOrderById(id);
			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();

			orderDetailsResponse.setOrderId(orderDto.getOrderId());
			orderDetailsResponse.setCost(orderDto.getCost());
			orderDetailsResponse.setItems(orderDto.getItems());
			orderDetailsResponse.setStatus(true);
			orderDetailsResponse.setUserId(orderDto.getUserId());

			return orderDetailsResponse;
		}
		catch (Exception e){
			throw new Exception();
		}
	}
	
	@PostMapping("/createOrder")
	public OrderDetailsResponse createOrder(@RequestBody OrderDetailsRequestModel orderDetailsRequestModel) {
		OrderDto orderDto = new OrderDto();
		orderDto.setCost(orderDetailsRequestModel.getCost());
		orderDto.setItems(orderDetailsRequestModel.getItems());
		orderDto.setUserId(orderDetailsRequestModel.getUserId());
		orderDto = orderService.createOrder(orderDto);

		// setting the values of response that will be returned back to the client
		OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
		orderDetailsResponse.setOrderId(orderDto.getOrderId());
		orderDetailsResponse.setCost(orderDto.getCost());
		orderDetailsResponse.setItems(orderDto.getItems());
		orderDetailsResponse.setStatus(true);
		orderDetailsResponse.setUserId(orderDto.getUserId());

		return orderDetailsResponse;
	}
		
	@PutMapping(path="/{id}")
	public OrderDetailsResponse updateOrder(@PathVariable String id, @RequestBody OrderDetailsRequestModel orderDetailsRequestModel) throws Exception{

		try {
			OrderDto orderDto = new OrderDto();
			orderDto.setCost(orderDetailsRequestModel.getCost());
			orderDto.setItems(orderDetailsRequestModel.getItems());
			orderDto.setUserId(orderDetailsRequestModel.getUserId());

			orderDto = orderService.updateOrderDetails(id,orderDto);

			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
			orderDetailsResponse.setOrderId(orderDto.getOrderId());
			orderDetailsResponse.setCost(orderDto.getCost());
			orderDetailsResponse.setItems(orderDto.getItems());
			orderDetailsResponse.setStatus(true);
			orderDetailsResponse.setUserId(orderDto.getUserId());

			return orderDetailsResponse;
		}
		catch (Exception e){
			throw new Exception();
		}
	}
	
	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteOrder(@PathVariable String id) throws Exception {
		OperationStatusModel operationStatusModel = new OperationStatusModel();
		try {
			orderService.getOrderById(id);
			orderService.deleteOrder(id);
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
	
	@GetMapping("/getAllOrders")
	public List<OrderDetailsResponse> getOrders() {
		List<OrderDto> orderDtoList = orderService.getOrders();
		List<OrderDetailsResponse> orderDetailsResponseList = new ArrayList<>();

		for(OrderDto orderDto : orderDtoList){

			OrderDetailsResponse orderDetailsResponse = new OrderDetailsResponse();
			orderDetailsResponse.setOrderId(orderDto.getOrderId());
			orderDetailsResponse.setCost(orderDto.getCost());
			orderDetailsResponse.setItems(orderDto.getItems());
			orderDetailsResponse.setStatus(true);
			orderDetailsResponse.setUserId(orderDto.getUserId());

			orderDetailsResponseList.add(orderDetailsResponse);
		}

		return orderDetailsResponseList;
	}
}
