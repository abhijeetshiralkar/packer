package com.mobiquityinc.service;

import java.util.List;

import com.mobiquityinc.exception.APIException;

public interface PackagingService {

	public String createPackages(List<String> inputCases) throws APIException;

}
