package com.mobiquityinc.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.PackagingItem;
import com.mobiquityinc.validation.PackagingValidator;

public class PackagingServiceImplTest {

	@Test
	public void testCreatePackages() throws APIException {
		final List<String> inputCases = new ArrayList<>();
		final PackagingHelper packagingHelper = Mockito.mock(PackagingHelper.class);
		final PackagingValidator packagingValidator = Mockito.mock(PackagingValidator.class);
		final PackagingServiceImpl packagingServiceImpl = Mockito.spy(PackagingServiceImpl.class);
		doReturn(packagingHelper).when(packagingServiceImpl).getPackagingHelper();
		doReturn(packagingValidator).when(packagingServiceImpl).getPackagingValidator();
		final Map<Double, List<PackagingItem>> packagingItemsMap = new HashMap<>();
		doReturn(packagingItemsMap).when(packagingHelper).derivePackagingItems(inputCases);
		final Map<Double, List<List<PackagingItem>>> packagingCombinations = new HashMap<>();
		doReturn(packagingCombinations).when(packagingHelper).derivePackagingItemCombinations(packagingItemsMap);
		final String result = "result";
		doReturn(result).when(packagingHelper).deriveFinalPackages(packagingCombinations);
		assertEquals(result, packagingServiceImpl.createPackages(inputCases));
		verify(packagingHelper, times(1)).derivePackagingItems(inputCases);
		verify(packagingValidator, times(1)).performValidation(packagingItemsMap);
		verify(packagingHelper, times(1)).filterPackagingItems(packagingItemsMap);
		verify(packagingHelper, times(1)).derivePackagingItemCombinations(packagingItemsMap);
		verify(packagingHelper, times(1)).deriveFinalPackages(packagingCombinations);
	}

}
