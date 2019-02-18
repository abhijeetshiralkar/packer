package com.mobiquityinc.packer;

import org.junit.Test;

import com.mobiquityinc.exception.APIException;

public class PackerTest {

	@Test(expected = APIException.class)
	public void testPackEmptyFilePath() throws APIException {
		Packer.pack(null);
	}

}
