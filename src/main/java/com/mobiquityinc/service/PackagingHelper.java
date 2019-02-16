package com.mobiquityinc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mobiquityinc.model.PackagingItem;

public class PackagingHelper {

	/**
	 * Derive packaging Items collection with the weighlimit as key and the
	 * packaging items list as value
	 * 
	 * @param inputCases
	 * @return
	 */
	public Map<Double, List<PackagingItem>> derivePackagingItems(List<String> inputCases) {
		Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		inputCases.forEach(input -> {
			String[] arr = input.split(":");
			Double weightLimit = Double.parseDouble(arr[0]);
			List<String> itemsList = Arrays.asList(arr[1].trim().split(" "));
			List<PackagingItem> packagingItems = new ArrayList<>();
			itemsList.forEach(item -> {
				String[] itemDetails = item.split(",");
				PackagingItem packagingItem = new PackagingItem(Integer.valueOf(itemDetails[0].substring(1)),
						Double.parseDouble(itemDetails[1]),
						Double.parseDouble(itemDetails[2].substring(1, itemDetails[2].length() - 1)));
				packagingItems.add(packagingItem);
			});
			packagingItemsMap.put(weightLimit, packagingItems);
		});
		return packagingItemsMap;
	}

	/**
	 * Remove items with weight more than the weight limit
	 * 
	 * @param packagingItemsMap
	 * @return
	 */
	public void filterPackagingItems(Map<Double, List<PackagingItem>> packagingItemsMap) {
		for (Map.Entry<Double, List<PackagingItem>> entry : packagingItemsMap.entrySet()) {
			List<PackagingItem> items = entry.getValue();
			Double weightLimit = entry.getKey();
			packagingItemsMap.put(weightLimit,
					items.stream().filter(s -> s.getWeight() <= weightLimit).collect(Collectors.toList()));
		}
	}

	public Map<Double, List<List<PackagingItem>>> derivePackagingItemCombinations(
			Map<Double, List<PackagingItem>> packagingItemsMap) {
		Map<Double, List<List<PackagingItem>>> packagingItemCombinations = new HashMap<>();
		for (Map.Entry<Double, List<PackagingItem>> entry : packagingItemsMap.entrySet()) {
			List<PackagingItem> items = entry.getValue();
			List<List<PackagingItem>> combinations = getCombinations(items);
		}
		return packagingItemCombinations;
	}

	protected List<List<PackagingItem>> getCombinations(List<PackagingItem> items) {
		List<List<PackagingItem>> combinations = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			List<PackagingItem> selfCombination = new ArrayList<>();
			selfCombination.add(items.get(i));
			combinations.add(selfCombination);
			for (int j = i + 1; j < items.size(); j++) {
				List<PackagingItem> combination = new ArrayList<>();
				for (int k = i; k <= j; k++) {
					combination.add(items.get(k));
				}
				combinations.add(combination);
			}
		}
		return combinations;
	}

}
