/**
 * 
 */
package com.dozenx.web.core.base;

import com.dozenx.util.StringUtil;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class AjaxServlet extends HttpServlet {
	private static final long serialVersionUID = -7490069778904810336L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		Object objService = null;
		try {
			//String username = request.getParameter("USERNAME");
			String url = request.getRequestURI();
			String[] classMethodName = url.substring(1).replace(".ajax","")
					.split("/");
			String s = classMethodName[(classMethodName.length - 1)];
			classMethodName = s.split("\\.");
			String methodName = classMethodName[(classMethodName.length - 1)];
			String className = classMethodName[0];
			/*
			 * for (int i = 1; i < classMethodName.length - 1; i++) { className
			 * = className + "." + classMethodName[i]; }
			 */
			// className = className ;
			// methodName =methodName;
			if (StringUtil.isBlank(methodName)) {
				throw new RuntimeException("ERROR:Ajax methodName is empty");
			}

			StringBuffer strb = new StringBuffer();
			request.setCharacterEncoding("utf-8");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			while (true) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				line = URLDecoder.decode(line, "UTF-8");
				strb.append(line);
			}
			ObjectMapper inputMapper = new ObjectMapper();
			SerializationConfig inputsc = inputMapper.getSerializationConfig();
			inputsc.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
			inputMapper.setSerializationConfig(inputsc);
			String i = strb.toString();
			i = StringUtil.isBlank(i) ? "{}" : i;
			HashMap inputData = (HashMap) inputMapper.readValue(i,
					HashMap.class);
			inputData.put("REQUEST", request);
			// Class serviceClass = Class.forName(className);
			/*
			 * if (serviceClass == null) { throw new
			 * RuntimeException("ERROR:Ajax缂傚倸鍊搁崐椋庢閿熺姴纾诲鑸靛姦閺佸鎲搁弮鍫㈠祦闁告劦鍠栭崡鎶芥煏韫囨洖啸妞ゆ柨娲娲箹閻愭彃濡ч梺鍛婁緱閸犳鎯侀悙鐑樷拻闁稿本鐟ч崝宥夋倵缁楁稑娲﹂崑锟犳煏婵炵偓娅撻柡浣割儔閺屻劑鎮ら崒娑橆伓" +
			 * className); }
			 */

			ServletContext servletContext = this.getServletContext();

			WebApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);

			objService = ctx.getBean(className);
			// objService = serviceClass.newInstance();
			Method method = objService.getClass().getMethod(methodName,
					new Class[] { HashMap.class });
			Object object =  method.invoke(objService,
					new Object[] { inputData });
			response.setContentType("text/json");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();

			ObjectMapper mapper = new ObjectMapper();
			SerializationConfig sc = mapper.getSerializationConfig();
			sc.setDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
			mapper.setSerializationConfig(sc);
			JsonGenerator gen = new JsonFactory().createJsonGenerator(out);
			mapper.writeValue(gen, object);

			gen.close();

			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}