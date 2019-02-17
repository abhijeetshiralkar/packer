package com.mobiquityinc.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.PackagingItem;

public class PackagingValidatorTest {

	PackagingValidator packagingValidator;

	@Before
	public void init() {
		packagingValidator = new PackagingValidator();
	}

	@Test(expected = APIException.class)
	public void testPerformValidationPackageWeightLimit() throws APIException {
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		packagingItemsMap.put(Double.valueOf(101), items);
		packagingValidator.performValidation(packagingItemsMap);
	}

	@Test(expected = APIException.class)
	public void testPerformValidationItemsSize() throws APIException {
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(2, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(3, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(4, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(5, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(6, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(7, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(8, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(9, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(10, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(11, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(12, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(13, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(14, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(15, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(16, 53.38, Double.valueOf(45)));
		packagingItemsMap.put(Double.valueOf(99), items);
		packagingValidator.performValidation(packagingItemsMap);
	}

	@Test(expected = APIException.class)
	public void testPerformValidationItemWeight() throws APIException {
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 103.38, Double.valueOf(45)));
		packagingItemsMap.put(Double.valueOf(99), items);
		packagingValidator.performValidation(packagingItemsMap);
	}

	@Test(expected = APIException.class)
	public void testPerformValidationItemPrice() throws APIException {
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 93.38, Double.valueOf(105)));
		packagingItemsMap.put(Double.valueOf(99), items);
		packagingValidator.performValidation(packagingItemsMap);
	}

}
