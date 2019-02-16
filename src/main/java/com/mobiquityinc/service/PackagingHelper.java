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
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		inputCases.forEach(input -> {
			final String[] arr = input.split(":");
			final Double weightLimit = Double.parseDouble(arr[0]);
			final List<String> itemsList = Arrays.asList(arr[1].trim().split(" "));
			final List<PackagingItem> packagingItems = new ArrayList<>();
			itemsList.forEach(item -> {
				final String[] itemDetails = item.split(",");
				final PackagingItem packagingItem = new PackagingItem(Integer.valueOf(itemDetails[0].substring(1)),
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
		for (final Map.Entry<Double, List<PackagingItem>> entry : packagingItemsMap.entrySet()) {
			final List<PackagingItem> items = entry.getValue();
			final Double weightLimit = entry.getKey();
			packagingItemsMap.put(weightLimit,
					items.stream().filter(s -> s.getWeight() <= weightLimit).collect(Collectors.toList()));
		}
	}

	public Map<Double, List<List<PackagingItem>>> derivePackagingItemCombinations(
			Map<Double, List<PackagingItem>> packagingItemsMap) {
		final Map<Double, List<List<PackagingItem>>> packagingItemCombinations = new HashMap<>();
		for (final Map.Entry<Double, List<PackagingItem>> entry : packagingItemsMap.entrySet()) {
			final List<PackagingItem> items = entry.getValue();
			final List<List<PackagingItem>> combinations = getCombinations(items);
		}
		return packagingItemCombinations;
	}

	protected List<List<PackagingItem>> getCombinations(List<PackagingItem> items) {
		final List<List<PackagingItem>> combinations = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			final PackagingItem currentItem = items.get(i);
			final int combinationSize = combinations.size();
			for (int j = 0; j < combinationSize; j++) {
				final List<PackagingItem> combination = combinations.get(j);
				final List<PackagingItem> newCombination = new ArrayList<PackagingItem>(combination);
				newCombination.add(currentItem);
				combinations.add(newCombination);
			}
			final List<PackagingItem> current = new ArrayList<PackagingItem>();
			current.add(currentItem);
			combinations.add(current);
		}
		return combinations;
	}

}
