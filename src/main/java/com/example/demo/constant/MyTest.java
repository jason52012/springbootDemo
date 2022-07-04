package com.example.demo.constant;

public class MyTest {
	public static void main(String[] args) {
		ProductCategory category = ProductCategory.FOOD;
		System.out.println(category.name());
		
		String c = "CAR";
		ProductCategory category1 = ProductCategory.valueOf(c); // if c can be found in enum, return this enum class
		System.out.println(category1);
	}
}
