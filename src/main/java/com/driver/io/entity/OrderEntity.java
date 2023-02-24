package com.driver.io.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

@Entity(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;


	@Column(nullable = false,unique = true)
	private String orderId = UUID.randomUUID().toString();

	@Column(nullable = false)
	private float cost;

	@Column(nullable = false)
	private String[] items;

	@ManyToOne
	@JoinColumn(name = "userId")
	private UserEntity userEntity;

//	@OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL)
//	private List<String> foodEntityList = new ArrayList<>();


	public String[] getItems() {
		return items;
	}

	public void setItems(String[] items) {
		this.items = items;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	@Column(nullable = false)
	private boolean status;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
