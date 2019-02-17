package com.mobiquityinc.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.mobiquityinc.model.PackagingItem;

public class PackagingHelperTest {

	PackagingHelper packaginHelper;

	@Before
	public void init() {
		packaginHelper = new PackagingHelper();
	}

	@Test
	public void testDerivePackagingItems() {
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
		final List<PackagingItem> combination = new ArrayList<>();
		combination.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		combination.add(new PackagingItem(2, 88.62, Double.valueOf(98)));
		assertEquals(Double.valueOf(142), packaginHelper.getCombinationWeight(combination));
	}

	@Test
	public void testGetCombinationPrice() {
		final List<PackagingItem> combination = new ArrayList<>();
		combination.add(new PackagingItem(1, 53.38, Double.valueOf(45)));
		combination.add(new PackagingItem(2, 88.62, Double.valueOf(98)));
		assertEquals(Double.valueOf(143), packaginHelper.getCombinationPrice(combination));
	}

	@Test
	public void testgetBestCombinations() {
		final Map<Double, List<List<PackagingItem>>> packagingCombinations = new HashMap<>();
		// 81 : (1,53.38,€45) (4,72.30,€76)
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
}
