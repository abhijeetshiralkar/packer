package com.mobiquityinc.service;

import com.mobiquityinc.exception.APIException;

/**
 * Interface for packaging related methods like createPackagesFromInputFile etc
 *
 * @author abhijeetshiralkar
 *
 */
public interface PackagingService {

	public String createPackagesFromInputFile(String filePath) throws APIException;

}
