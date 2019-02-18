package com.mobiquityinc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import com.mobiquityinc.model.PackagingItem;

public class PackagingHelperTest {

	@Test
	public void testDerivePackagingItems() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final List<String> inputCases = new ArrayList<>();
		inputCases.add("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3)");
		inputCases.add("75 : (1,85.31,€29) (2,14.55,€74)");
		final Map<Double, List<PackagingItem>> packagingItemsMap = packaginHelper.derivePackagingItems(inputCases);
		assertEquals(2, packagingItemsMap.size());
		assertNotNull(packagingItemsMap.get(Double.valueOf(81)));
		assertNotNull(packagingItemsMap.get(Double.valueOf(75)));
		assertEquals(3, packagingItemsMap.get(Double.valueOf(81)).size());
		assertEquals(2, packagingItemsMap.get(Double.valueOf(75)).size());
	}

	@Test
	public void testFilterPackagingItems() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(2, 88.62, Double.valueOf(98)));
		items.add(new PackagingItem(3, Double.valueOf(81), Double.valueOf(76)));
		packagingItemsMap.put(Double.valueOf(81), items);
		packaginHelper.filterPackagingItems(packagingItemsMap);
		assertEquals(2, packagingItemsMap.get(Double.valueOf(81)).size());
	}

	@Test
	public void testGetCombinations() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		items.add(new PackagingItem(2, 88.62, Double.valueOf(98)));
		items.add(new PackagingItem(3, Double.valueOf(81), Double.valueOf(76)));
		items.add(new PackagingItem(5, 30.18, Double.valueOf(9)));
		final List<List<PackagingItem>> combinations = packaginHelper.getCombinations(items);
		assertEquals(15, combinations.size());
	}

	@Test
	public void testGetCombinationWeight() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final List<PackagingItem> combination = new ArrayList<>();
		combination.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		combination.add(new PackagingItem(2, 88.62, Double.valueOf(98)));
		assertEquals(Double.valueOf(142), packaginHelper.getCombinationWeight(combination));
	}

	@Test
	public void testGetCombinationPrice() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final List<PackagingItem> combination = new ArrayList<>();
		combination.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		combination.add(new PackagingItem(2, 88.62, Double.valueOf(98)));
		assertEquals(Double.valueOf(143), packaginHelper.getCombinationPrice(combination));
	}

	@Test
	public void testgetBestCombinations() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final Map<Double, List<List<PackagingItem>>> packagingCombinations = new HashMap<>();
		final List<PackagingItem> combination1 = new ArrayList<>();
		combination1.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		final List<PackagingItem> combination2 = new ArrayList<>();
		combination2.add(new PackagingItem(4, 72.30, Double.valueOf(76)));
		final List<List<PackagingItem>> combinations = new ArrayList<>();
		combinations.add(combination1);
		combinations.add(combination2);
		packagingCombinations.put(Double.valueOf(81), combinations);
		final Map<Double, List<PackagingItem>> bestCombination = packaginHelper
				.getBestCombinations(packagingCombinations);
		assertEquals(combination2, bestCombination.get(Double.valueOf(81)));
	}

	@Test
	public void testCreatePackagesFromBestCombinations() {
		final PackagingHelper packaginHelper = new PackagingHelper();
		final Map<Double, List<PackagingItem>> bestCombinations = new LinkedHashMap<>();
		final List<PackagingItem> firstCombination = new ArrayList<>();
		firstCombination.add(new PackagingItem(4, 72.30, Double.valueOf(76)));
		bestCombinations.put(Double.valueOf(81), firstCombination);
		final List<PackagingItem> secondCombination = new ArrayList<>();
		bestCombinations.put(Double.valueOf(8), secondCombination);
		final List<PackagingItem> thirdCombination = new ArrayList<>();
		thirdCombination.add(new PackagingItem(2, 14.55, Double.valueOf(74)));
		thirdCombination.add(new PackagingItem(7, 60.02, Double.valueOf(74)));
		bestCombinations.put(Double.valueOf(75), thirdCombination);
		final String result = "4\n-\n2,7\n";
		assertEquals(result, packaginHelper.createPackagesFromBestCombinations(bestCombinations));
	}

	@Test
	public void testderivePackagingItemCombinations() {
		final PackagingHelper packagingHelper = Mockito.spy(PackagingHelper.class);
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		final List<PackagingItem> items = new ArrayList<>();
		items.add(new PackagingItem(1, 85.31, Double.valueOf(29)));
		items.add(new PackagingItem(2, 14.55, Double.valueOf(74)));
		items.add(new PackagingItem(7, 60.02, Double.valueOf(74)));
		packagingItemsMap.put(Double.valueOf(75), items);
		final List<List<PackagingItem>> combinations = new ArrayList<>();
		final List<PackagingItem> firstCombination = new ArrayList<>();
		firstCombination.add(new PackagingItem(2, 14.55, Double.valueOf(74)));
		firstCombination.add(new PackagingItem(7, 60.02, Double.valueOf(74)));
		combinations.add(firstCombination);
		doReturn(combinations).when(packagingHelper).getCombinations(items);
		final Map<Double, List<List<PackagingItem>>> packaginItemCombinations = packagingHelper
				.derivePackagingItemCombinations(packagingItemsMap);
		assertNotNull(packaginItemCombinations);
		assertEquals(combinations, packaginItemCombinations.get(Double.valueOf(75)));

	}
}
