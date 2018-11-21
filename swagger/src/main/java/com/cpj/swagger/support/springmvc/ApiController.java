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
package com.cpj.swagger.support.springmvc;

import com.cpj.swagger.support.Constants;
import com.cpj.swagger.support.internal.ApiViewWriter;
import com.cpj.swagger.support.internal.DefaultApiViewWriter;
import com.cpj.swagger.util.ResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dozenx.util.FileUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author yonghuan
 * @since 1.0.0
 */
@Controller
@RequestMapping("/api")
//@APIs("/api")
public class ApiController implements InitializingBean, Constants {
	
	private ApiViewWriter apiViewWriter = new DefaultApiViewWriter();
	private Properties props = new Properties();
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	//@API(value = "", summary = "获取API文档", method = "get", parameters = @Param(name = "lang", description = "语言（默认为中文）", type = "string", format = "string"))
	public void index(HttpServletRequest request,HttpServletResponse response,  @RequestParam(defaultValue = DEFAULT_LANG) String lang) throws Exception {
		if(StringUtils.isBlank(lang)) {
			lang = DEFAULT_LANG;
		}
		String suffix = props.getProperty("suffix");
		if(StringUtils.isBlank(suffix)) {
			suffix = "";
		}
		props.put("suffix", suffix);
		apiViewWriter.writeIndex(request, response, lang, props);
	}





	@RequestMapping(value="", method=RequestMethod.GET)
	public void queryApi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Properties props = new Properties();
		InputStream is = ResourceUtil.getResourceAsStream("swagger.properties");
		try {
			props.load(is);

			String path = request.getContextPath();
			String host = request.getServerName() + ":" + request.getServerPort() + path;
			props.setProperty("apiHost", host);
			String apiFile = props.getProperty("apiFile");
			if (StringUtils.isBlank(apiFile)) {
				apiFile = DEFAULT_API_FILE;
			}
			String apiFilePath = request.getRealPath(apiFile);
			props.setProperty("apiFile", apiFilePath);
			apiViewWriter.writeApis(request, response, props);
		}catch (Exception e){
			e.printStackTrace();

		}finally {
			try
			{//关掉流
				is.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value="test/data/get", method=RequestMethod.GET)
	@ResponseBody
	public String getApiTestData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Properties props = new Properties();
		InputStream is = ResourceUtil.getResourceAsStream("swagger.properties");
		props.load(is);

		is.close();

		//吧json 数据 传入到对应的名称的数据文档里
		String  url = request.getParameter("url");
		url =url.replaceAll("\\\\","").replaceAll("/","");
		String path =props.getProperty("savepath");
		File file =new File(path);
		if( file.exists()&&file.isDirectory() ){

		}else{
			file.mkdirs();
		}

		Path testDataPath = Paths.get(file.getPath());
		testDataPath=testDataPath.resolve(url+".tsd");
		File testDataFile =testDataPath.toFile();
		String content = "";


		String data = FileUtil.readFile2Str(testDataFile);
		return data;

	}

	@RequestMapping(value="test/data/save", method=RequestMethod.POST)
	public void saveApiTestData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Properties props = new Properties();
		InputStream is = ResourceUtil.getResourceAsStream("swagger.properties");
		props.load(is);

		is.close();

		//吧json 数据 传入到对应的名称的数据文档里

		String  url = request.getParameter("url");
		String testData = request.getParameter("testData");
		url =url.replaceAll("\\\\","").replaceAll("/","");
		String path =props.getProperty("savepath");

		File file =new File(path);
		if( file.exists()&&file.isDirectory() ){


		}else{
			file.mkdirs();
		}
		Path testDataPath = Paths.get(file.getPath());
		testDataPath=testDataPath.resolve(url+".tsd");
		File testDataFile =testDataPath.toFile();
		String content = "";
		FileUtil.writeFile(testDataFile,testData);


	}



	/**
	 * @since 1.2.0
	 */
	@RequestMapping(value="statics/**", method=RequestMethod.GET)
	public void queryStatic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		apiViewWriter.writeStatic(request, response, props);
	}

	//@Override
	public void afterPropertiesSet() throws Exception {
		InputStream is = ResourceUtil.getResourceAsStream("swagger.properties");
		props.load(is);
		is.close();
	}
}
