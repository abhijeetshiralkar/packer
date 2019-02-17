package com.mobiquityinc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
		final Map<Double, List<PackagingItem>> packagingItemsMap = new LinkedHashMap<>();
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
		final Map<Double, List<List<PackagingItem>>> packagingItemCombinations = new LinkedHashMap<>();
		for (final Map.Entry<Double, List<PackagingItem>> entry : packagingItemsMap.entrySet()) {
			final List<PackagingItem> items = entry.getValue();
			final List<List<PackagingItem>> combinations = getCombinations(items);
			packagingItemCombinations.put(entry.getKey(), combinations);
		}
		return packagingItemCombinations;
	}

	public String deriveFinalPackages(Map<Double, List<List<PackagingItem>>> packagingCombinations) {
		final Map<Double, List<PackagingItem>> bestCombinations = getBestCombinations(packagingCombinations);
		return createPackagesFromBestCombinations(bestCombinations);
	}

	protected List<List<PackagingItem>> getCombinations(List<PackagingItem> items) {
		final List<List<PackagingItem>> combinations = new ArrayList<>();
		items.forEach(item -> {
			final PackagingItem currentItem = item;
			// Temporary list to avoid concurrentmodificationexception
			final List<List<PackagingItem>> tempCombinations = new ArrayList<>(combinations);
			tempCombinations.forEach(combination -> {
				final List<PackagingItem> newCombination = new ArrayList<PackagingItem>(combination);
				newCombination.add(currentItem);
				combinations.add(newCombination);
			});
			final List<PackagingItem> current = new ArrayList<PackagingItem>();
			current.add(currentItem);
			combinations.add(current);
		});
		return combinations;
	}

	protected String createPackagesFromBestCombinations(Map<Double, List<PackagingItem>> bestCombinations) {
		final StringBuilder packages = new StringBuilder();
		for (final Map.Entry<Double, List<PackagingItem>> entry : bestCombinations.entrySet()) {
			final List<PackagingItem> combination = entry.getValue();
			if (combination.isEmpty()) {
				packages.append("-").append(System.lineSeparator());
			} else {
				combination.forEach(item -> {
					packages.append(item.getIndexNumber()).append(",");
				});
				packages.replace(packages.length() - 1, packages.length(), "");
				packages.append(System.lineSeparator());
			}
		}
		return packages.toString();
	}

	protected Map<Double, List<PackagingItem>> getBestCombinations(
			Map<Double, List<List<PackagingItem>>> packagingCombinations) {
		final Map<Double, List<PackagingItem>> bestCombinations = new LinkedHashMap<>();
		for (final Map.Entry<Double, List<List<PackagingItem>>> entry : packagingCombinations.entrySet()) {
			final Double weightLimit = entry.getKey();
			final List<List<PackagingItem>> combinations = entry.getValue();
			double bestPrice = 0;
			double bestWeight = weightLimit;
			List<PackagingItem> bestCombination = new ArrayList<>();
			for (final List<PackagingItem> combination : combinations) {
				final Double combinationWeight = getCombinationWeight(combination);
				if (combinationWeight <= weightLimit) {
					final Double combinationPrice = getCombinationPrice(combination);
					if (combinationPrice > bestPrice) {
						bestPrice = combinationPrice;
						bestWeight = combinationWeight;
						bestCombination = combination;
					} else if (combinationPrice == bestPrice && combinationWeight < bestWeight) {
						bestWeight = combinationWeight;
						bestCombination = combination;
					}
				}
			}
			bestCombinations.put(weightLimit, bestCombination);
		}
		return bestCombinations;
	}

	protected Double getCombinationPrice(List<PackagingItem> combination) {
		Double combinationPrice = Double.valueOf(0);
		for (final PackagingItem item : combination) {
			combinationPrice += item.getPrice();
		}
		return combinationPrice;
	}

	protected Double getCombinationWeight(List<PackagingItem> combination) {
		Double combinationWeight = Double.valueOf(0);
		for (final PackagingItem item : combination) {
			combinationWeight += item.getWeight();
		}
		return combinationWeight;
	}
}
