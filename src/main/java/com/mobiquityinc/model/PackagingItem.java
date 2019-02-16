package com.mobiquityinc.model;

public class PackagingItem {
	
	private Integer indexNumber;
	
	private Double weight, price;

	public PackagingItem(Integer indexNumber, Double weight, Double price) {
		super();
		this.indexNumber = indexNumber;
		this.weight = weight;
		this.price = price;
	}

	public Integer getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(Integer indexNumber) {
		this.indexNumber = indexNumber;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	

}
