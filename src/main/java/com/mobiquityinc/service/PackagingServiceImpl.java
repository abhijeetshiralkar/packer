package com.mobiquityinc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	public String createPackagesFromInputFile(String filePath) throws APIException {
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
			final List<String> inputCases = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				inputCases.add(line);
			}

			return createPackages(inputCases);
		} catch (final IOException e) {
			throw new APIException(String.format("Technical exception occurred while processing file: %s", filePath),
					e);
		}
	}

	protected String createPackages(List<String> inputCases) throws APIException {
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
