package com.mobiquityinc.service;

import java.util.List;
import java.util.Map;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.PackagingItem;
import com.mobiquityinc.validation.PackagingValidator;

/**
 * Service Implementation for Packaging
 */
public class PackagingServiceImpl implements PackagingService {

	@Override
	public String createPackages(List<String> inputCases) throws APIException {
		final PackagingHelper packagingHelper = getPackagingHelper();
		final PackagingValidator packagingValidator = getPackagingValidator();
		final Map<Double, List<PackagingItem>> packagingItemsMap = packagingHelper.derivePackagingItems(inputCases);
		packagingValidator.performValidation(packagingItemsMap);
		packagingHelper.filterPackagingItems(packagingItemsMap);
		final Map<Double, List<List<PackagingItem>>> packagingCombinations = packagingHelper
				.derivePackagingItemCombinations(packagingItemsMap);
		return packagingHelper.deriveFinalPackages(packagingCombinations);
	}

	protected PackagingHelper getPackagingHelper() {
		return new PackagingHelper();
	}

	protected PackagingValidator getPackagingValidator() {
		return new PackagingValidator();
	}
}
