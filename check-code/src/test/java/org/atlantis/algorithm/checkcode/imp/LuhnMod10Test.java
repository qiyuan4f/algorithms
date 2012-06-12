/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.atlantis.algorithm.checkcode.imp;

import org.atlantis.algorithm.checkcode.CheckCoder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-06
 */
public class LuhnMod10Test {
	private CheckCoder checkCoder;
	private String testData = "33028319870101171";
	private int testCodeCorrect = 9;
	private int testCodeError = 1;

	@Before
	public void setUp() throws Exception {
		checkCoder = new LuhnMod10();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGenerate() throws Exception {
		Assert.assertEquals(testCodeCorrect, checkCoder.generate(testData));
	}

	@Test
	public void testCheck() throws Exception {
		// 含有正确的校验码的字符串
		Assert.assertTrue(checkCoder.check(testData + testCodeCorrect));

		// 含有错误的校验码的字符串
		Assert.assertFalse(checkCoder.check(testData + testCodeError));
	}

	@Test
	public void testEncode() throws Exception {
		Assert.assertEquals(testData + testCodeCorrect, checkCoder.encode(testData));
	}

	@Test
	public void testDecode() throws Exception {
		Map<String, Object> ret = checkCoder.decode(testData + testCodeCorrect);

		Assert.assertEquals(testData, ret.get(CheckCoder.PARAM_DATA));
		Assert.assertEquals(testCodeCorrect, ret.get(CheckCoder.PARAM_CODE));
	}

	@Test
	public void testGetCode() throws Exception {
		Assert.assertEquals(testCodeCorrect, checkCoder.getCode(testData + testCodeCorrect));
		Assert.assertNotSame(testCodeCorrect, checkCoder.getCode(testData + testCodeError));
	}

	@Test
	public void testGetData() throws Exception {
		Assert.assertEquals(testData, checkCoder.getData(testData + testCodeCorrect));
		Assert.assertNotSame(testData, checkCoder.getCode(testData + testCodeError));
	}
}
