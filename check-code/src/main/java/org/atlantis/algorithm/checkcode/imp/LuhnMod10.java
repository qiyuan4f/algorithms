/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.atlantis.algorithm.checkcode.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.atlantis.algorithm.checkcode.CheckCoder;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-6-4
 */
public class LuhnMod10 implements CheckCoder {
	private final static int TABLE[][] = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
			{ 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 } };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gfg.util.checkcode.CheckCoder#generate(java.lang.String)
	 */
	@Override
	public int generate(String digits) {

		return make(digits);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gfg.util.CheckCoder#check(java.lang.String)
	 */
	@Override
	public boolean check(String digits) {
		return ((make(digits) % 10) == 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gfg.util.checkcode.CheckCoder#encode(java.lang.String)
	 */
	@Override
	public String encode(String digits) {
		return digits + make(digits);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gfg.util.checkcode.CheckCoder#decode(java.lang.String)
	 */
	@Override
	public Map<String, Object> decode(String digits) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(PARAM_CODE, getCode(digits));
		map.put(PARAM_DATA, getData(digits));

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gfg.util.checkcode.CheckCoder#getCode(java.lang.String)
	 */
	@Override
	public int getCode(String digits) {
		return (digits.charAt(digits.length() - 1) - '0');
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gfg.util.checkcode.CheckCoder#getData(java.lang.String)
	 */
	@Override
	public String getData(String digits) {
		return digits.substring(0, digits.length() - 1);
	}

	/**
	 * 默认根据当前时间<tt>yyyyMMddHHmmss</tt>计算校验码
	 */
	@SuppressWarnings("unused")
	private String make() {
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String digits = fmt.format(date);

		return String.valueOf(make(digits));
	}

	/**
	 * 实现<tt>Luhn Mod-10 (ISO 2894/ANSI 4.13)</tt>算法生成验证码 公式：∑(ai×Wi) mod 10，ai=各位数字, wi对应位数的加权因子，当i为奇数wi=2*ai
	 * (如果值大于10则减去5)，i为偶数ai
	 * 
	 * @param digits
	 *            未加验证码的字符串
	 * 
	 * @return 验证码:<tt>0-9</tt>
	 * 
	 * @throws NullPointerException
	 *             如果<tt>digits</tt>为<code>null</code>
	 * @throws NumberFormatException
	 *             如果<tt>digits</tt>中包含非数值字符
	 */
	private int make(String digits) throws NullPointerException,
			NumberFormatException {
		int sum = 0;
		int odd = 1;

		for (int i = 0; i < digits.length(); ++i) {
			char temp = digits.charAt(i);
			if (Character.isDigit(temp)) {
				sum += TABLE[(odd = 1 - odd)][(temp - '0')];
			} else {
				throw new NumberFormatException(
						"LuhnMod10::make: 包含非数值字符，输入字符：" + digits);
			}
		}

		sum %= 10;

		return (10 - sum) % 10;
	}
}
