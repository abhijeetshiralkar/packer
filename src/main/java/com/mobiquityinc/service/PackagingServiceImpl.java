package com.mobiquityinc.service;

import java.util.List;
import java.util.Map;

import com.mobiquityinc.model.PackagingItem;;

public class PackagingServiceImpl implements PackagingService {

	@Override
	public String createPackage(List<String> inputCases) {
		final PackagingHelper packagingHelper = new PackagingHelper();
		final Map<Double, List<PackagingItem>> packagingItemsMap = packagingHelper.derivePackagingItems(inputCases);
		packagingHelper.filterPackagingItems(packagingItemsMap);
		final Map<Double, List<List<PackagingItem>>> packagingCombinations = packagingHelper
				.derivePackagingItemCombinations(packagingItemsMap);
		return packagingHelper.deriveFinalPackage(packagingCombinations);
	}
}
