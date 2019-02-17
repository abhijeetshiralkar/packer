package com.mobiquityinc.packer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.service.PackagingService;
import com.mobiquityinc.service.PackagingServiceImpl;

public class Packer {

	public static String pack(String filePath) throws APIException {
		if (filePath.isEmpty()) {
			throw new APIException("Please provide the filepath");
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
			final List<String> inputCases = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				inputCases.add(line);
			}
			final PackagingService packagingService = new PackagingServiceImpl();
			return packagingService.createPackages(inputCases);
		} catch (final IOException e) {
			throw new APIException(String.format("Technical exception occurred while processing file: ", filePath), e);
		}
	}
}
