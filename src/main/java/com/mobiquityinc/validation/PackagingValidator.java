package com.mobiquityinc.validation;

import java.util.List;
import java.util.Map;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.PackagingItem;

public class PackagingValidator {

	/**
	 * Validations to be performed: 1. Max weight that a package can take is ≤ 100
	 * 2. There might be up to 15 items you need to choose from 3. Max weight and
	 * cost of an item is ≤ 100
	 *
	 * @param items
	 * @return
	 * @throws APIException
	 */
	public void performValidation(Map<Double, List<PackagingItem>> packagingItemsMap) throws APIException {
		int i = 1;
		for (final Map.Entry<Double, List<PackagingItem>> entry : packagingItemsMap.entrySet()) {
			final Double weightLimit = entry.getKey();
			final List<PackagingItem> items = entry.getValue();
			if (weightLimit > 100) {
				throw new APIException(
						String.format("Weight limit %.2f is greater than 100 on line %d", weightLimit, i));
			}
			if (items.size() > 15) {
				throw new APIException(String.format(
						"Number of items to chose from is %d which is more than 15 on line %d", items.size(), i));
			}
			for (final PackagingItem item : items) {
				final Double weight = item.getWeight();
				final Double price = item.getPrice();
				if (weight > 100) {
					throw new APIException(String.format("Weight of item %d is %.2f which is more than 100 on line %d",
							item.getIndexNumber(), item.getWeight(), i));
				}
				if (price > 100) {
					throw new APIException(String.format("Price of item %d is %.2f which is more than 100 on line %d",
							item.getIndexNumber(), item.getPrice(), i));
				}
			}
			i++;
		}
	}

}
