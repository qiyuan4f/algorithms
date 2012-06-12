package org.atlantis.algorithm.checkcode.imp;

import org.atlantis.algorithm.checkcode.CheckCoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-06
 */
public class ISO7064Mod11_10 implements CheckCoder {
	private final static int TABLE[] = {9, 2, 4, 6, 8, 10, 1, 3, 5, 7};

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
		return (digits.charAt(digits.length() - 1) - '0');
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
	 * 实现<tt>ISO7064Mod11_10 (ISO 7064/ANSI 4.13)</tt>算法生成验证码
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
	private  int make(String digits) {
		int sum = 10;
		
		for (int i = 0; i < digits.length(); ++i) {
			char temp = digits.charAt(i);
			if (Character.isDigit(temp)) {
				sum = TABLE[(sum + temp - '0') % 10];
			} else {
				throw new NumberFormatException(
												   "ISO7064Mod11_10::make: 包含非数值字符，输入字符：" + digits);
			}
		}

		return (11 - sum) % 10;
	}
}
