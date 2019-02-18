package com.mobiquityinc.service;

import java.util.List;

import com.mobiquityinc.exception.APIException;

/**
 * Interface for packaging related methods like createPackages etc
 *
 * @author abhijeetshiralkar
 *
 */
public interface PackagingService {

	public String createPackages(List<String> inputCases) throws APIException;

}
