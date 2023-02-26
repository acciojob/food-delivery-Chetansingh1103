package com.driver.service.impl;

import com.driver.io.entity.OrderEntity;
import com.driver.io.entity.UserEntity;
import com.driver.io.repository.OrderRepository;
import com.driver.io.repository.UserRepository;
import com.driver.service.OrderService;
import com.driver.shared.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;


    @Override
    public OrderDto createOrder(OrderDto orderDto)  {

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setCost(orderDto.getCost());
            orderEntity.setUserId(orderDto.getUserId());
            orderEntity.setItems(orderDto.getItems());
            orderEntity.setStatus(true);

            orderRepository.save(orderEntity);

            // setting the orderDto to return it
            orderDto.setOrderId(orderEntity.getOrderId());
            orderDto.setId(orderEntity.getId());
            orderDto.setStatus(orderEntity.isStatus());

            return orderDto;
    }

    @Override
    public OrderDto getOrderById(String orderId) throws NullPointerException {


            OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

            // putting the values of order into orderDto and returning it
            OrderDto orderDto = new OrderDto();
            orderDto.setId(orderEntity.getId());
            orderDto.setUserId(orderEntity.getUserId());
            orderDto.setOrderId(orderEntity.getOrderId());
            orderDto.setCost(orderEntity.getCost());
            orderDto.setItems(orderEntity.getItems());
            orderDto.setStatus(orderEntity.isStatus());

            return orderDto;

    }

    @Override
    public OrderDto updateOrderDetails(String orderId, OrderDto orderDto) throws NullPointerException {


            OrderEntity orderEntity = orderRepository.findByOrderId(orderId);

            orderEntity.setItems(orderDto.getItems());
            orderEntity.setCost(orderDto.getCost());
            orderEntity.setStatus(true);

            orderRepository.save(orderEntity);

            // extracting the order details from the repository to get updated details
            orderDto.setOrderId(orderEntity.getOrderId());
            orderDto.setId(orderEntity.getId());
            orderDto.setUserId(orderEntity.getUserId());

            return orderDto;

    }

    @Override
    public void deleteOrder(String orderId) throws NullPointerException {
       if(orderRepository.findByOrderId(orderId) == null){
           throw new NullPointerException();
       }
            OrderEntity orderEntity = orderRepository.findByOrderId(orderId);


            orderRepository.delete(orderEntity);


    }

    @Override
    public List<OrderDto> getOrders() {

        List<OrderEntity> orderEntityList = (List<OrderEntity>) orderRepository.findAll();
        List<OrderDto> orderDtoList = new ArrayList<>();

        for(OrderEntity orderEntity : orderEntityList){

            OrderDto orderDto = new OrderDto();
            orderDto.setId(orderEntity.getId());
            orderDto.setUserId(orderEntity.getUserId());
            orderDto.setOrderId(orderEntity.getOrderId());
            orderDto.setCost(orderEntity.getCost());
            orderDto.setItems(orderEntity.getItems());
            orderDto.setStatus(orderEntity.isStatus());

            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }
}