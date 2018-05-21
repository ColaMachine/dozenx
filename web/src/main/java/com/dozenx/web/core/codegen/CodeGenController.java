package com.dozenx.web.core.codegen;

import com.cpj.swagger.annotation.API;
import com.cpj.swagger.annotation.APIs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dozen.zhang on 2017/3/21.
 */

//@APIs(description = "代码生成")
@Controller
@RequestMapping("/codegen")
public class CodeGenController {
    Generator generator =new Generator();

    @RequestMapping(value = "/config/{name}", method= RequestMethod.GET)
    @ResponseBody
    //@API(/*value="/config/{id}",*/ summary="获取配置文件"/*method = "GET"*/ )
    public Object configGet(@PathVariable("name")String name,HttpServletRequest request ) {
        Generator generator=  Generator.generate(new String[]{"SysRole","SysUser","SysUserRole"});

       return  generator.allTable;
    }

}
