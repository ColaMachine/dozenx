package com.dozenx.web.core.codegen;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * FreeMarker 自定义指令，用于转换指令内容体中的字母为大写字母。
 * 
 */
public class JdbcTypeDirective implements TemplateDirectiveModel {

	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// 检查是否传递参数，此指令禁止传参！
		if (!params.isEmpty()) {
			throw new TemplateModelException(
					"This directive doesn't allow parameters.");
		}
		// 禁用循环变量
		/*
		 * 循环变量
		         用户定义指令可以有循环变量，通常用于重复嵌套内容，基本用法是：作为nested指令的参数传递循环变量的实际值，而在调用用户定义指令时，在${"<@…>"}开始标记的参数后面指定循环变量的名字
		         例子：
			<#macro repeat count>
			  <#list 1..count as x>
			    <#nested x, x/2, x==count>
			  </#list>
			</#macro>
			<@repeat count=4 ; c, halfc, last>
			  ${c}. ${halfc}<#if last> Last!</#if>
			</@repeat> 
		*/
		if (loopVars.length != 0) {
			throw new TemplateModelException(
					"This directive doesn't allow loop variables.");
		}

		// 指令内容体不为空
		if (body != null) {
			// Executes the nested body. Same as <#nested> in FTL, except
			// that we use our own writer instead of the current output writer.
			body.render(new UpperCaseFilterWriter(env.getOut()));
		} else {
			throw new RuntimeException("missing body");
		}
	}

	/**
	 * 输出流的包装器(转换大写字母)
	 */
	private static class UpperCaseFilterWriter extends Writer {

		private final Writer out;

		UpperCaseFilterWriter(Writer out) {
			this.out = out;
		}

		public void write(char[] cbuf, int off, int len) throws IOException {
			char[] transformedCbuf = new char[len];
			for (int i = 0; i < len; i++) {
				transformedCbuf[i] = Character.toLowerCase(cbuf[i + off]);
			}
			String type =String.valueOf(transformedCbuf).trim();
			String typeName=null;
			 if(type.startsWith("varchar")){
		            typeName= "VARCHAR";
		        }else if(type.startsWith("int")){
		            typeName= "INTEGER";
		        }else if(type.startsWith("bigint")){
		            typeName= "BIGINT";
		        }else if(type.startsWith("float")){
		            typeName= "FLOAT";
		        } else if(type.startsWith("double")){
		            typeName= "DOUBLE";
		        } else if(type.startsWith("date")){
		            typeName= "DATE";
		        } else if(type.startsWith("timestamp")){
		            typeName= "TIMESTAMP";
		        }else if(type.startsWith("text")){
                    typeName= "LONGVARCHAR";
                }else if(type.startsWith("char")){
                    typeName= "CHAR";
                }else if(type.startsWith("tinyint")){
                    typeName= "TINYINT";
                }else if (type.startsWith("decimal")) {
				 	typeName = "DECIMAL";
			 	}else{
		            throw new IOException(
		                    "This directive content doesn't  allow this value"+type+".");
		        }
			out.write(typeName);
		}

		public void flush() throws IOException {
			out.flush();
		}

		public void close() throws IOException {
			out.close();
		}
	}

}