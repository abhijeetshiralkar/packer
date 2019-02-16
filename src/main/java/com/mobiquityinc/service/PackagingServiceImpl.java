package com.mobiquityinc.service;

import java.util.List;
import java.util.Map;

import com.mobiquityinc.model.PackagingItem;;

public class PackagingServiceImpl implements PackagingService {

	@Override
	public String createPackage(List<String> inputCases) {
		PackagingHelper packagingHelper = new PackagingHelper();
		Map<Double, List<PackagingItem>> packagingItemsMap = packagingHelper.derivePackagingItems(inputCases);
		packagingHelper.filterPackagingItems(packagingItemsMap);
		Map<Double, List<List<PackagingItem>>> packaginCombinations = packagingHelper
				.derivePackagingItemCombinations(packagingItemsMap);

		return null;
	}
}
