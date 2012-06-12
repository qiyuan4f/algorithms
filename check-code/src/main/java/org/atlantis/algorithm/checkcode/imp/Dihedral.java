/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.atlantis.algorithm.checkcode.imp;

import org.atlantis.algorithm.checkcode.CheckCoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-06
 */
public class Dihedral implements CheckCoder {
	/**
	 * dihedral addition matrix A + B = addtion[A][B]
	 */
	private static final int addtion[][] = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, {1, 2, 3, 4, 0, 6, 7, 8, 9, 5}, {2, 3, 4, 0, 1, 7, 8, 9, 5, 6}, {3, 4, 0, 1, 2, 8, 9, 5, 6, 7}, {4, 0, 1, 2, 3, 9, 5, 6, 7, 8}, {5, 9, 8, 7, 6, 0, 4, 3, 2, 1}, {6, 5, 9, 8, 7, 1, 0, 4, 3, 2}, {7, 6, 5, 9, 8, 2, 1, 0, 4, 3}, {8, 7, 6, 5, 9, 3, 2, 1, 0, 4}, {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}};

	/**
	 * dihedral inverse map, A + inverse[A] = 0
	 */
	private static final int inverse[] = {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};

	/**
	 * permutation weighting matrix weight[position][value]
	 */
	private static final int weight[][] = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, {1, 5, 7, 6, 2, 8, 3, 0, 9, 4}, {5, 8, 0, 3, 7, 9, 6, 1, 4, 2}, {8, 9, 1, 6, 0, 4, 3, 5, 2, 7}, {9, 4, 5, 3, 1, 2, 6, 8, 7, 0}, {4, 2, 8, 6, 5, 7, 3, 9, 0, 1}, {2, 7, 9, 3, 8, 0, 6, 4, 1, 5}, {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}};

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
		return make(getData(digits)) == getCode(digits);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.gfg.util.checkcode.CheckCoder#encode(java.lang.String)
	 */
	@Override
	public String encode(String digits) {
		return make(digits) + digits;
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
		return (digits.charAt(0) - '0');
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.gfg.util.checkcode.CheckCoder#getData(java.lang.String)
	 */
	@Override
	public String getData(String digits) {
		return digits.substring(1);
	}


	/**
	 * 实现<tt>Dihedral</tt>算法生成验证码
	 *
	 * @param digits 未加验证码的字符串
	 *
	 * @return 验证码:<tt>0-9</tt>
	 *
	 * @throws NullPointerException 如果<tt>digits</tt>为<code>null</code>
	 * @throws NumberFormatException 如果<tt>digits</tt>中包含非数值字符
	 */
	private int make(String digits) throws NullPointerException, NumberFormatException {
		int code = 0;

		for (int i = 0; i < digits.length(); ++i) {
			char temp = digits.charAt(i);
			if (Character.isDigit(temp)) {
				code = addtion[code][weight[(i + 1) % 8][temp - '0']];
			} else {
				throw new NumberFormatException("Dihedral::make: 包含非数值字符，输入字符：" + digits);
			}
		}

		return inverse[code];
	}
}
