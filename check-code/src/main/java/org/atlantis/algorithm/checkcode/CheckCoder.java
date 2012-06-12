/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.atlantis.algorithm.checkcode;

import java.util.Map;

/**
 * @author L <qiyuan4f@gmail.com>
 * 
 * @version
 * @date 2012-6-4
 */
public interface CheckCoder {
	public final static String PARAM_DATA = "data";
	public final static String PARAM_CODE = "code";
	
	/**
	 * 根据给定的字符串生成校验码
	 * 
	 * @param digits
	 *            待生成校验码的字符串
	 * @return 校验码
	 */
	public abstract int generate(String digits);

	/**
	 * 生成包含给定字符串校验码的完整字符串
	 * 
	 * @param digits
	 *            待生成校验码的字符串
	 * @return 包含校验码的完整字符串
	 */
	public abstract String encode(String digits);

	/**
	 * 解码给定字符串，以Map形式存放，内容包括校验码、数据区等其他信息 信息中的内容标准如下：
	 * <p>
	 * <tt>code</tt> : 校验码 <br>
	 * <tt>data</tt> : 数据区
	 * </p>
	 * 
	 * @param digits
	 *            包含校验码的完整字符串
	 * @return 解码后的各部分信息
	 */
	public abstract Map<String, Object> decode(String digits);

	/**
	 * 获取包含校验码的给定字符串解码后对应的校验码
	 * 
	 * @param digits
	 *            包含校验码的完整字符串
	 * @return 校验码
	 */
	public abstract int getCode(String digits);

	/**
	 * 获取包含校验码的给定字符串解码后对应的数据
	 * 
	 * @param digits
	 *            包含校验码的完整字符串
	 * @return 数据
	 */
	public abstract String getData(String digits);

	/**
	 * 验证<code>digits</code>是否合法
	 * 
	 * @param digits
	 *            包含校验码的完整字符串
	 * @return <tt>true</tt>，如果验证合法;否则，返回<tt>false</tt>
	 */
	public boolean check(String digits);

}
