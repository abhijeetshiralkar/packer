package com.mobiquityinc.service;

import java.util.List;
import java.util.Map;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.PackagingItem;
import com.mobiquityinc.validation.PackagingValidator;;

public class PackagingServiceImpl implements PackagingService {

	@Override
	public String createPackages(List<String> inputCases) throws APIException {
		final PackagingHelper packagingHelper = new PackagingHelper();
		final PackagingValidator packagingValidator = new PackagingValidator();
		final Map<Double, List<PackagingItem>> packagingItemsMap = packagingHelper.derivePackagingItems(inputCases);
		packagingValidator.performValidation(packagingItemsMap);
		packagingHelper.filterPackagingItems(packagingItemsMap);
		final Map<Double, List<List<PackagingItem>>> packagingCombinations = packagingHelper
				.derivePackagingItemCombinations(packagingItemsMap);
		return packagingHelper.deriveFinalPackages(packagingCombinations);
	}
}
