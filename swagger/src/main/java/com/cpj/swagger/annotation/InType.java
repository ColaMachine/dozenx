/*
 * Copyright 2011-2017 CPJIT Group.
 * 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.cpj.swagger.annotation;

/**
 * 请求参数的数据类型。
 * @author zhang zw
 * @since 1.2.2
 */
public class InType {
	/**在url参数里**/
	public static final String query ="query";
	/**在url路径里**/
	public static final String path ="path";
	/**在提交的body 流里**/
	public static final String body ="body";
	/**在提交的form 表单里**/
	public static final String form ="form";

	public static final String params ="params";
}
