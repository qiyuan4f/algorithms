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
 * @date 2012-06
 */
public class ISO7064Mod97_10 implements CheckCoder {
	private final static int TABLE[] = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6,};

	@Override
	public int generate(String digits) {
		return make(digits);
	}

	@Override
	public String encode(String digits) {
		return digits + String.format("%02d", make(digits));
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
		return Integer.parseInt(digits.substring(digits.length() - 2));
	}

	@Override
	public String getData(String digits) {
		return digits.substring(0, digits.length() - 2);
	}

	@Override
	public boolean check(String digits) {
		return make(getData(digits)) == getCode(digits);
	}

	/**
	 * 实现<tt>ISO7064Mod97_10 (ISO 7064/ANSI 4.13)</tt>算法生成验证码 公式：(98 - digits * 100 mod 97) mod 97
	 *
	 * @param digits 未加验证码的字符串
	 *
	 * @return 验证码:<tt>0-10</tt>
	 *
	 * @throws NullPointerException 如果<tt>digits</tt>为<code>null</code>
	 * @throws NumberFormatException 如果<tt>digits</tt>中包含非数值字符
	 */
	public int make(String digits) throws NullPointerException, NumberFormatException {
		return ((int) (98 - (Long.parseLong(digits) * 100) % 97L)) % 97;
	}

	public static void main(String[] args) {
		System.out.println(new ISO7064Mod97_10().check("123456100004"));
	}
}
