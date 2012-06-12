/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.atlantis.algorithm.checkcode.imp;

import org.atlantis.algorithm.checkcode.CheckCoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-6-4
 */
public class ISO7064Mod11_2 implements CheckCoder {
	private final static int TABLE[] = { 1, 2, 4, 8, 5, 10, 9, 7, 3, 6, };

	@Override
	public int generate(String digits) {
		return make(digits);
	}

	@Override
	public String encode(String digits) {
		return digits + make(digits);
	}

	@Override
	public Map<String, Object> decode(String digits) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PARAM_CODE, getCode(digits));
		map.put(PARAM_DATA, getData(digits));

		return map;
	}

	@Override
	public int getCode(String digits) {
		char code = digits.charAt(digits.length() - 1);
		return (code == 'X' || code == 'x' ? 10 : code - '0');
	}

	@Override
	public String getData(String digits) {
		return digits.substring(0, digits.length() - 1);
	}

	@Override
	public boolean check(String digits) {
		return make(getData(digits)) == getCode(digits);
	}

	/**
	 * 实现<tt>ISO7064Mod11_2 (ISO 7064/ANSI 4.13)</tt>算法生成验证码 公式：∑(ai×Wi) mod 11，ai=各位数字, wi对应位数的加权因子=ai*(2^i mod 11)
	 * 
	 * @param digits
	 *            未加验证码的字符串
	 * 
	 * @return 验证码:<tt>0-10</tt>
	 * 
	 * @throws NullPointerException
	 *             如果<tt>digits</tt>为<code>null</code>
	 * @throws NumberFormatException
	 *             如果<tt>digits</tt>中包含非数值字符
	 */
	public int make(String digits) throws NullPointerException,
			NumberFormatException {
		int sum = 0;

		for (int i = digits.length() - 1, j = 1; i >= 0; --i, ++j) {
			char temp = digits.charAt(i);
			if (Character.isDigit(temp)) {
				sum += (temp - '0') * TABLE[j % 10];
			} else {
				throw new NumberFormatException(
						"ISO7064Mod11_2::make: 包含非数值字符，输入字符：" + digits);
			}
		}
		sum %= 11;

		return (12 - sum) % 11;
	}
}
