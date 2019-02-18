package com.mobiquityinc.packer;

import org.apache.commons.lang3.StringUtils;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.service.PackagingServiceImpl;

/**
 * Class that would be used by programs to get the packages which are best
 * combinations based on different criterias like price and weight
 *
 * @author abhijeetshiralkar
 *
 */
public class Packer {
	private Packer() {
	}

	public static String pack(String filePath) throws APIException {
		if (StringUtils.isEmpty(filePath)) {
			throw new APIException("Please provide the filepath");
		}
		return new PackagingServiceImpl().createPackagesFromInputFile(filePath);
	}
}
