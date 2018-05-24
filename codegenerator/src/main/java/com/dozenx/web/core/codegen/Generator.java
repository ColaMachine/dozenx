/**
\ * 版权所有：公众信息
 * 项目名称:calendar
 * 创建者: dozen.zhang
 * 创建日期: 2015年12月26日
 * 文件说明: 
 */
package com.dozenx.web.core.codegen;

import com.dozenx.core.Path.PathManager;
import com.dozenx.util.StringUtil;
import com.google.gson.*;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Generator {


    ZTable table;
    static HashMap<String ,ZTable> allTable =new HashMap<String ,ZTable>();
    String fatherPackage = "";
    private String ctrl = "\r\n";
    private String tab = "    ";
    private String tab2 = tab+tab;
    private String tab3 = tab2+tab;
    private String tab4 = tab2+tab2;
    private Path homePath;

    private Configuration cfg;

    private static Logger logger = LoggerFactory.getLogger(Generator.class);

    private Map root = new HashMap();

    public static Gson createGson() {
        return new GsonBuilder().

        /* registerTypeAdapter(ZTable.class, new ZTable.Handler()).*/ registerTypeAdapter(ZColum.class, new ZColum.Handler()).create();

    }

    /**
     * 两个jsonobject 合并
     *
     * @param target
     *            参数
     * @param from
     *            参数
     */
    public static void merge(JsonObject target, JsonObject from) {
        for (Map.Entry<String, JsonElement> entry : from.entrySet()) {
            if (entry.getValue().isJsonObject()) {
                if (target.has(entry.getKey()) && target.get(entry.getKey()).isJsonObject()) {
                    merge(target.get(entry.getKey()).getAsJsonObject(), entry.getValue().getAsJsonObject());
                } else {
                    target.remove(entry.getKey());
                    target.add(entry.getKey(), entry.getValue());
                }
            } else {
                target.remove(entry.getKey());
                target.add(entry.getKey(), entry.getValue());
            }
        }

    }
    public ZTable load(Path fromFile) throws IOException {

        logger.info("read config file{}", fromFile);
        if (!fromFile.toFile().exists()) {
            logger.info("code "+fromFile+".cfg not exists");
            return null;
        }
        try (Reader reader = Files.newBufferedReader(fromFile, Charset.forName("UTF-8"))) {
            Gson gson = createGson();
            JsonElement baseConfig = gson.toJsonTree(new ZTable());
            JsonParser parser = new JsonParser();
            JsonElement config = parser.parse(reader);
            if (!config.isJsonObject()) {
                return new ZTable();
            } else {
                merge(baseConfig.getAsJsonObject(), config.getAsJsonObject());
                return gson.fromJson(baseConfig, ZTable.class);
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new IOException("Failed to load config", e);
        }



    }



    public void init(String code ) throws IOException {
        root.put("apiType", new ApiTypeDirective());
        root.put("javaType", new JavaTypeDirective());
        root.put("jdbcType", new JdbcTypeDirective());
        root.put("getabc", new abcDirective());
        root.put("getAbc", new AbDirective());
        // 创建目录
        Path homePath = PathManager.getInstance().getHomePath();
        Files.createDirectories(homePath.resolve("temp"));
        this.homePath = homePath;
        StringBuffer sb = new StringBuffer();
        String type = "";
        String typeName = "";
        table = load(homePath.resolve("src/main/resources/cfg/"+code+".cfg"));
        allTable.put(table.getName(), table);
        table.init();
        // FreeMarkerUtil.processTemplate(templatePath, templateName,
        // templateEncoding, root, out);
    }
    public  void intTpl(String code) throws Exception {
        table=(ZTable)allTable.get(code);
        root.put("allTables", allTable);
        root.put("table", table);
        if(table==null){
            throw new Exception( "code table name is null " +code);
           // System.out.println("code table name is null");
        }
        if(null!=table.getMapper()){
            root.put("child", allTable.get(table.getMapper().getChild()));
            root.put("mapper", allTable.get(table.getMapper().getMapper()));
            root.put("parent", table);
        }
       root.put("abc", StringUtil.getabc(table.getName()));
        root.put("Abc", StringUtil.getAbc(table.getName()));
        /*
        root.put("javaType", new JavaTypeDirective());*/
        /*
         * Configuration config=new Configuration(); //设置要解析的模板所在的目录，并加载模板文件
         * config.setDirectoryForTemplateLoading(file); //设置包装器，并将对象包装为数据模型
         * config.setObjectWrapper(new DefaultObjectWrapper());
         */

        cfg = new Configuration();
        StringTemplateLoader temp = new StringTemplateLoader();

        String serviceTpl = this.readFile2Str("src/main/resources/code/Service.tpl");
        String beanTpl = this.readFile2Str("src/main/resources/code/Bean.tpl");
        String controllerTpl = this.readFile2Str("src/main/resources/code/Controller.tpl");
        String mapperTpl = this.readFile2Str("src/main/resources/code/Mapper.tpl");
        String mapperXmlTpl = this.readFile2Str("src/main/resources/code/MapperXml.tpl");
        String list = this.readFile2Str("src/main/resources/code/list.tpl");
        String listMapper = this.readFile2Str("src/main/resources/code/listMapper.tpl");
        String edit = this.readFile2Str("src/main/resources/code/edit.tpl");
        String sqlTpl = this.readFile2Str("src/main/resources/code/sql.tpl");
        String viewTpl = this.readFile2Str("src/main/resources/code/view.tpl");
        // String listHtmlTpl =
        // this.readFile2Str("src/main/resources/code/ListHtml.tpl");
        // String editHtmlTpl =
        // this.readFile2Str("src/main/resources/code/EditHtml.tpl");
        // String viewHtmlTpl =
        // this.readFile2Str("src/main/resources/code/ViewHtml.tpl");
        temp.putTemplate("service", serviceTpl);
        temp.putTemplate("bean", beanTpl);
        temp.putTemplate("controller", controllerTpl);
        temp.putTemplate("mapper", mapperTpl);
        temp.putTemplate("sql", sqlTpl);
        temp.putTemplate("mapperXml", mapperXmlTpl);
        temp.putTemplate("list", list);
        temp.putTemplate("edit", edit);
        temp.putTemplate("view", viewTpl);
        temp.putTemplate("listMapper", listMapper);
        // temp.putTemplate("listHtml", listHtmlTpl);
        // temp.putTemplate("editHtml", editHtmlTpl);
        // temp.putTemplate("viewHtml", viewHtmlTpl);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateLoader(temp);
    }

    public void writeFile(String path,String name, String templateName) throws IOException, TemplateException {
        Template template;
        template = cfg.getTemplate(templateName,"UTF-8");
        homePath.resolve("temp").resolve(path).toFile().mkdirs();
       // homePath.resolve(path).toFile().mkdirs();
        File file = homePath.resolve("temp").resolve(path).resolve(StringUtil.getAbc(name)).toFile();
       // file = homePath.resolve(path).resolve(StringUtil.getAbc(name)).toFile();
        // Files.createFile(homePath.resolve(StringUtil.getAbc(table.getName())));
        FileWriter writer = new FileWriter(file);
        template.process(root, writer);
        writer.flush();
        writer.close();
        System.out.println(writer.toString());
    }
    public String readFile2Str(String path) throws IOException {
        File file = PathManager.getInstance().getHomePath().resolve(path).toFile();

        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        StringBuffer templateStr = new StringBuffer();
        while ((s = br.readLine()) != null) {
            templateStr.append(s + "\r\n");
        }
        return templateStr.toString();
    }
    public String getValidStr(){
       StringBuffer sb=new StringBuffer(tab2+"ValidateUtil vu = new ValidateUtil();").append(ctrl);;
      StringBuffer jssb=new StringBuffer();
      StringBuffer jsmsg=new StringBuffer();

       sb.append(tab2+"String validStr=\"\";").append(ctrl);;
       for(int i=0;i<table.getCols().size();i++){
           ZColum zcol =table.getCols().get(i);
           String type = zcol.getType().toLowerCase();

           List rules =new ArrayList();
           List jsrules =new ArrayList();
           List message=new ArrayList();
           if(type.startsWith("varchar")||type.startsWith("char")){
        	   int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
        	   rules.add(String.format("new Length(%d)", length));
        	   jsrules.add(String.format("maxlength:%d", length));
        	   message.add(String.format("maxlength:\"%s不能多于%d个字符\"", zcol.getName(),length));
           }
           if(type.startsWith("int")||type.startsWith("bigint")){
               Integer integer=this.getIntFromKuoHao(type);
               if(integer!=null){
                   rules.add(String.format("new Digits("+integer+",0)"));
               }else{
                   rules.add(String.format("new Digits(10,0)"));
               }
               if(zcol.getShowValue()!=null){
                   Map<Integer, String> map = zcol.getShowValue();
                   List strAry= new ArrayList();
                   for (Map.Entry<Integer, String> entry : map.entrySet()) {
                       strAry.add("\""+entry.getKey() +"\"");
                   }

                   rules.add(String.format("new CheckBox(new String[]{"+StringUtil.join(",", strAry.toArray())+"})"));
                   jsrules.add("CheckBox:["+StringUtil.join(",", strAry.toArray())+"]");
                   message.add("CheckBox:\"必须输入"+StringUtil.join(",", strAry.toArray()).replaceAll("\"", "'")+"中的值\"");
               }
        	   jsrules.add(String.format("digits:true"));
        	   message.add(String.format("digits:\"必须输入整数\""));
           }
           if(type.startsWith("float")){
        	   int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
        	   int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
        	   rules.add(String.format("new Digits(%d,%d)",integer,fraction));
        	   jsrules.add(String.format("number:true"));
        	   message.add(String.format("number:\"必须输入合法的数字（负数，小数）\""));
           }
           if(type.startsWith("double")){
        	   int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
        	   int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
        	   rules.add(String.format("new Digits(%d,%d)",integer,fraction));
        	   jsrules.add(String.format("number:true"));
        	   message.add(String.format("number:\"必须输入合法的数字（负数，小数）\""));
           }
           if(type.equals("date")){
        	   rules.add(String.format("new DateValue(\"yyyy-MM-dd\")"));
        	   jsrules.add(String.format("dateISO:true"));
        	   message.add(String.format("dateISO:\"必须输入合法日期\""));
           }
           if(type.equals("datetime")){
        	   rules.add(String.format("new DateValue(\"yyyy-MM-dd HH:mm:ss\")"));
        	   jsrules.add(String.format("ymd:\"yyyy-MM-dd HH:mm:ss\""));
               message.add(String.format("ymd:\"必须输入合法日期\""));
           }
           if(type.equals("timestamp")){
        	   rules.add(String.format("new DateValue(\"yyyy-MM-dd HH:mm:ss\")"));
        	   jsrules.add(String.format("ymd:\"yyyy-MM-dd HH:mm:ss\""));
               message.add(String.format("ymd:\"必须输入合法日期\""));
           }

           if(zcol.isNn()){
        	   rules.add(String.format("new NotEmpty()"));
        	   jsrules.add(String.format("required:true"));
           }
           if(StringUtil.isNotBlank(zcol.getValid())){
        	   String[] validAry= zcol.getValid().split(",,");
        	   for(int j=0;j<validAry.length;j++){
        		   if(validAry[j].toLowerCase().startsWith("regex")){
        			   String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   content=content.replace("\\", "\\\\");
        			   rules.add(String.format("new Regex(%s)",content));
        			   jsrules.add(String.format("regex:"+content));
                       message.add(String.format("regex:\"必须输入指定格式字符串\""));
        		   }
        		   if(validAry[j].toLowerCase().startsWith("email")){
        			  // String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new EmailRule()"));
        			   jsrules.add(String.format("email:true"));
                       //message.add(String.format("email:\"请输入正确邮箱地址\""));
        		   }
        		   if(validAry[j].toLowerCase().startsWith("phone")){
        			   //String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new PhoneRule()"));
        			   jsrules.add(String.format("phone:true"));
        		   }
        		   if(validAry[j].toLowerCase().startsWith("money")){
        			  // String content=StringUtil.getContentBetween(validAry[j], "(", ")");
        			   rules.add(String.format("new MoneyRule()"));
        			   jsrules.add(String.format("money:true"));
        		   }
                   if(validAry[j].toLowerCase().startsWith("alpha_numeric")){
                       rules.add(String.format("new AlphaNumbericUnderlineRule()"));
                       jsrules.add(String.format("alphanumeric:true"));
                   }
                   if(validAry[j].toLowerCase().startsWith("alphanumeric")){
                       rules.add(String.format("new AlphaNumericRule()"));
                       jsrules.add(String.format("alphanumeric:true"));
                       message.add(String.format("regex:\"只能输入字母和数字\""));
                   }
                   if(validAry[j].toLowerCase().startsWith("alpha")){
                       rules.add(String.format("new AlphaRule()"));
                       jsrules.add(String.format("alpha:true"));
                       message.add(String.format("alpha:\"只能输入字母\""));
                   }
        	   }
           }
           String ruleStr=StringUtil.join(",",rules.toArray());
           ruleStr=" new Rule[]{"+ruleStr+"}";
           sb.append(tab2+"vu.add(\""+zcol.getName()+"\", "+zcol.getName()+", \""+zcol.getRemark()+"\", "+ruleStr+");").append(ctrl);
           jssb.append(tab2+zcol.getName()+":{").append(ctrl)
           .append(tab3+StringUtil.join(",",jsrules.toArray())).append(ctrl)
           .append(tab2+"},").append(ctrl);
           jsmsg.append(tab2+zcol.getName()+":{").append(ctrl)
           .append(tab3+StringUtil.join(",",message.toArray())).append(ctrl)
           .append(tab2+"},").append(ctrl);

       }
       sb.append(tab2+"validStr = vu.validateString();").append(ctrl);
       sb.append(tab2+"if(StringUtil.isNotEmpty(validStr)) {").append(ctrl);
       sb.append(tab3+String.format("return ResultUtil.getResult(%d,%s);",302,"validStr")).append(ctrl);
       sb.append(tab2+"}").append(ctrl);
       root.put("jsrules", jssb.toString());
       root.put("jsmsg", jsmsg.toString());
      return sb.toString();

    }
    public String getSetParam(){
        StringBuffer sb =new StringBuffer();
        for(int i=0;i<table.getCols().size();i++){
            ZColum zcol =table.getCols().get(i);
            String type = zcol.getType().toLowerCase();
            sb.append(tab2+"String "+zcol.getName()+" = request.getParameter(\""+zcol.getName()+"\");").append(ctrl)
            .append(tab2+"if(!StringUtil.isBlank("+zcol.getName()+")){").append(ctrl);
            if(type.startsWith("date")||type.startsWith("timestamp")||type.startsWith("datetime")){
                sb.append(tab3+"if(StringUtil.checkNumeric("+zcol.getName()+")){").append(ctrl);
                if(type.startsWith("date")){
                    sb.append(tab4+StringUtil.getabc(table.getName())+".set"+StringUtil.getAbc(zcol.getName())+"(new Date("+zcol.getName()+"));").append(ctrl);
                }else{
                sb.append(tab4+StringUtil.getabc(table.getName())+".set"+StringUtil.getAbc(zcol.getName())+"("+ GenCodeHelper.changeMySqlType2JavaType(type)+".valueOf("+zcol.getName()+"));").append(ctrl);
                }
                sb.append(tab3+"}else if(StringUtil.checkDateStr("+zcol.getName()+", \""+getYMDStr(type)+"\")){").append(ctrl)
                .append(tab4+StringUtil.getabc(table.getName())+".set"+StringUtil.getAbc(zcol.getName())+"(");
                if(type.startsWith("timestamp")){
                    sb.append("new Timestamp( DateUtil.parseToDate("+zcol.getName()+", \""+getYMDStr(type)+"\").getTime()");
                }
                if(type.startsWith("date")){
                    sb.append("DateUtil.parseToDate("+zcol.getName()+", \""+getYMDStr(type)+"\"");
                }
                sb.append("));").append(ctrl).append(tab3+"}").append(ctrl);
            }else{
                sb.append(tab4+StringUtil.getabc(table.getName())+".set"+StringUtil.getAbc(zcol.getName())+"("+ GenCodeHelper.changeMySqlType2JavaType(type)+".valueOf("+zcol.getName()+"));").append(ctrl);
            }
            sb.append(tab2+"}").append(ctrl);
        }
           return sb.toString();
    }

    /**
     * 在controller 中提取参数
     * @return
     * @author dozen.zhang
     */
    public String getSearchParam(){
        StringBuffer sb =new StringBuffer();
        sb.append(tab2+"HashMap<String,Object> params= new HashMap<>();").append(ctrl);
        for(int i=0;i<table.getCols().size();i++){
            ZColum zcol =table.getCols().get(i);
            String type = zcol.getType().toLowerCase();
            sb.append(tab2+"String "+zcol.getName()+" = request.getParameter(\""+zcol.getName()+"\");").append(ctrl)
            .append(tab2+"if(!StringUtil.isBlank("+zcol.getName()+")){").append(ctrl);
            if(type.startsWith("date")||type.startsWith("timestamp")||type.startsWith("datetime")){
                sb.append(tab3+"if(StringUtil.checkNumeric("+zcol.getName()+")){").append(ctrl)
                .append(tab4+String.format("params.put(\"%s\",%s);",zcol.getName(),zcol.getName())).append(ctrl)
                .append(tab3+"}else if(StringUtil.checkDateStr("+zcol.getName()+", \""+getYMDStr(type)+"\")){").append(ctrl)
                .append(tab4+String.format("params.put(\"%s\",",zcol.getName()));
                if(type.startsWith("timestamp")){
                    sb.append("new Timestamp( DateUtil.parseToDate("+zcol.getName()+", \""+getYMDStr(type)+"\").getTime()");
                }
                if(type.startsWith("date")){
                    sb.append("DateUtil.parseToDate("+zcol.getName()+", \""+getYMDStr(type)+"\"");
                }
                sb.append("));").append(ctrl).append(tab3+"}").append(ctrl);

            }else{
                sb.append(tab4+"params.put(\""+zcol.getName()+"\","+zcol.getName()+");").append(ctrl);
            }
            sb.append(tab2+"}").append(ctrl);
            if(type.startsWith("date")||type.startsWith("timestamp")||type.startsWith("datetime")){
                sb.append(tab2+"String "+zcol.getName()+"Begin = request.getParameter(\""+zcol.getName()+"Begin\");").append(ctrl)
                .append(tab2+"if(!StringUtil.isBlank("+zcol.getName()+"Begin)){").append(ctrl);
                sb.append(tab3+"if(StringUtil.checkNumeric("+zcol.getName()+"Begin)){").append(ctrl)
                .append(tab4+String.format("params.put(\"%sBegin\",%sBegin);",zcol.getName(),zcol.getName())).append(ctrl)
                .append(tab3+"}else if(StringUtil.checkDateStr("+zcol.getName()+"Begin, \""+getYMDStr(type)+"\")){").append(ctrl)
                .append(tab4+String.format("params.put(\"%sBegin\",",zcol.getName()));
                if(type.startsWith("timestamp")){
                    sb.append("new Timestamp( DateUtil.parseToDate("+zcol.getName()+"Begin, \""+getYMDStr(type)+"\").getTime()");
                }
                if(type.startsWith("date")){
                    sb.append("DateUtil.parseToDate("+zcol.getName()+"Begin, \""+getYMDStr(type)+"\"");
                }
                sb.append("));").append(ctrl).append(tab3+"}").append(ctrl);
                sb.append(tab2+"}").append(ctrl);
                sb.append(tab2+"String "+zcol.getName()+"End = request.getParameter(\""+zcol.getName()+"End\");").append(ctrl)
                .append(tab2+"if(!StringUtil.isBlank("+zcol.getName()+"End)){").append(ctrl);
                sb.append(tab3+"if(StringUtil.checkNumeric("+zcol.getName()+"End)){").append(ctrl)
                .append(tab4+String.format("params.put(\"%sEnd\",%sEnd);",zcol.getName(),zcol.getName())).append(ctrl)
                .append(tab3+"}else if(StringUtil.checkDateStr("+zcol.getName()+"End, \""+getYMDStr(type)+"\")){").append(ctrl)
                .append(tab4+String.format("params.put(\"%sEnd\",",zcol.getName()));
                if(type.startsWith("timestamp")){
                    sb.append("new Timestamp( DateUtil.parseToDate("+zcol.getName()+"End, \""+getYMDStr(type)+"\").getTime()");
                }
                if(type.startsWith("date")){
                    sb.append("DateUtil.parseToDate("+zcol.getName()+"End, \""+getYMDStr(type)+"\"");
                }
                sb.append("));").append(ctrl).append(tab3+"}").append(ctrl);
                sb.append(tab2+"}").append(ctrl);
            }
        }
           return sb.toString();
    }
    public String getYMDStr(String type){
        String ymd ="";
        if(type.startsWith("date")){
            ymd="yyyy-MM-dd";
        }else{
            ymd="yyyy-MM-dd HH:mm:ss";
        }
return ymd;
    }
    public Integer getIntFromKuoHao(String str){
        int index = str.indexOf("(");
        if(index==-1){
            return null;
        }
        else{
            int end =str.indexOf(")");
            if(end>index){
                String val = str.substring(index+1,end);
                return Integer.valueOf(val);
            }else{
                return null;
            }

        }
    }
   /* public String changeMySqlType2JavaType(String type) {
        String typeName = null;
        type = type.toLowerCase();
        if (type.startsWith("varchar")) {
            typeName = "String";
        } else if (type.startsWith("int")) {
            typeName = "Integer";
        } else if (type.startsWith("bigint")) {
            typeName = "Long";
        } else if (type.startsWith("float")) {
            typeName = "Float";
        } else if (type.startsWith("double")) {
            typeName = "Double";
        } else if (type.startsWith("date")) {
            typeName = "Date";
        } else if (type.startsWith("timestamp")) {
            typeName = "Timestamp";
        } else if (type.startsWith("text")) {
            typeName = "String";
        }
        return typeName;
    }*/
   public void preGenController() throws IOException, TemplateException {
       logger.info("genController");
/*        getIdValid();
        root.put("getSearchParam", getSearchParam());
        root.put("setParam",  getSetParam());
        root.put("validCode", getValidStr());
        root.put("controllerViewMethod", getCtrlViewM());*/
       ControllerFactory factory= new ControllerFactory(allTable, root);
       factory.genCode(table.getName());

   }
    public void genController() throws IOException, TemplateException {
        String pkg= table.getPkg();
        pkg=pkg.replaceAll("\\.","/");
        writeFile("src/main/java/"+pkg+"/"+StringUtil.getabc(table.getName())+"/action",table.getName() + "Controller.java", "controller");
    }

    private String getCtrlViewM() {
        StringBuffer sb =new StringBuffer();
        sb.append("String id = request.getParameter(\"id\");").append(ctrl);
        sb.append("HashMap<String,Object> result =new HashMap<String,Object>();").append(ctrl);
        sb.append("if(!StringUtil.isBlank(id)){").append(ctrl);
            sb.append(tab+""+StringUtil.getAbc(table.getName())+" bean = "+StringUtil.getabc(table.getName())+"Service.selectByPrimaryKey("+ GenCodeHelper.changeMySqlType2JavaType(table.getPk().getType())+".valueOf(id));").append(ctrl);
            sb.append(tab+"result.put(\"bean\", bean);").append(ctrl);
            if(table.getMapper()!=null && table.getName().equals(table.getMapper().getParent())){
            sb.append(tab+"HashMap<String,String> params =new HashMap<String,String>();").append(ctrl);
            sb.append(tab+"params.put(\""+table.getMapper().getChild()+"\",id);").append(ctrl);
        sb.append(tab+"List<"+StringUtil.getAbc(table.getMapper().getMapper())+"> childMaps ="+StringUtil.getabc(table.getMapper().getMapper())+"Service.listByParams(new HashMap<String,String>());").append(ctrl);
        sb.append(tab+"result.put(\"childMaps\", childMaps);").append(ctrl);
            }
        sb.append("}").append(ctrl);
        if(table.getMapper()!=null&& table.getName().equals(table.getMapper().getParent())){
            sb.append("List<"+StringUtil.getAbc(table.getMapper().getChild())+"> childs ="+StringUtil.getabc(table.getMapper().getChild())+"Service.listByParams(new HashMap<String,String>());").append(ctrl);
            sb.append("result.put(\"childs\", childs);").append(ctrl);
        }
        sb.append("return this.getResult(result);").append(ctrl);
        return sb.toString();
    }
    public void preGenService() throws IOException, TemplateException {
        ServiceFactory factory= new ServiceFactory(allTable, root);

        factory.getService(table.getName());
        factory.getConfilictJudge(table.getName());
        logger.info("genService");
        if(table.getMapper()!=null&& table.getName().equals(table.getMapper().getParent())){
            ZTable childTable = allTable.get(table.getMapper().getMapper());
            String s= GenCodeHelper.changeMySqlType2JavaType(childTable.getPk().getType())+".valueOf(stNow)";
            root.put("serviceSaveWithChilds", s);
        }

    }
    public void genService() throws IOException, TemplateException {
        logger.info("genservice:"+table.getName());
        String pkg= table.getPkg();
        pkg=pkg.replaceAll("\\.","/");

        writeFile("src/main/java/"+pkg+"/"+StringUtil.getabc(table.getName())+"/service",table.getName() + "Service.java", "service");



       // writeFile("src/main/java/cola/machine/service/",table.getName() + "Service.java", "service");
    }
    public void preGenMapper() throws IOException, TemplateException {

    }
    public void genMapper() throws IOException, TemplateException {
        String pkg= table.getPkg();
        pkg=pkg.replaceAll("\\.","/");
        logger.info("genMapper");
        writeFile("src/main/java/"+pkg+"/"+StringUtil.getabc(table.getName())+"/dao",table.getName() + "Mapper.java", "mapper");


     //   writeFile("src/main/java/cola/machine/dao/",table.getName() + "Mapper.java", "mapper");
    }
    public void preGenMapperXml() throws IOException, TemplateException {

    }
    public void genMapperXml() throws IOException, TemplateException {
        String pkg= table.getPkg();
        pkg=pkg.replaceAll("\\.","/");
        logger.info("genMapperXml");
        writeFile("src/main/java/"+pkg+"/"+StringUtil.getabc(table.getName())+"/mapper",table.getName() + "Mapper.xml", "mapperXml");



        //writeFile("src/main/resources/config/mapper/",table.getName() + "Mapper.xml", "mapperXml");
    }
    public void preGenBean() throws IOException, TemplateException {
        logger.info("genBean");
        StringBuffer sb = new StringBuffer();
        String type = "";
        String typeName = "";
        for (ZColum col : table.getCols()) {
            type = col.getType().toLowerCase();
            sb.append("\n/**" + col.getRemark() + "**/").append(ctrl);
            typeName = GenCodeHelper.changeMySqlType2JavaType(type);
            sb.append("    private " + typeName + " " + col.getName() + ";").append(ctrl);
            sb.append("    public " + typeName + " get" + StringUtil.getAbc(col.getName()) + "(){").append(ctrl)
                    .append("        return " + col.getName() + ";").append(ctrl).append("    }")
                    .append("    public void set" + StringUtil.getAbc(col.getName()) + "(" + typeName + " "
                            + col.getName() + "){")
                    .append(ctrl).append("        this." + col.getName() + "=" + col.getName() + ";").append(ctrl)
                    .append("    }");
            if(col.getReferences()!=null){
                String[] ary = col.getReferences().split("\\.");
                typeName="String";
                ary[2]=ary[0]+"_"+ary[2];
                sb.append("    private String " + ary[2] + ";").append(ctrl);
                sb.append("    public " + typeName + " get" + StringUtil.getAbc( ary[2]) + "(){").append(ctrl)
                        .append("        return " +  ary[2] + ";").append(ctrl).append("    }")
                        .append("    public void set" + StringUtil.getAbc( ary[2]) + "(" + typeName + " "
                                +  ary[2] + "){")
                        .append(ctrl).append("        this." +  ary[2] + "=" +  ary[2] + ";").append(ctrl)
                        .append("    }");
            }
        }

        root.put("content", sb);


        /*
         * StringWriter writer = new StringWriter(); template.process(root,
         * writer); System.out.println(writer.toString());
         */
    }
    public void genBean() throws IOException, TemplateException {
        String pkg= table.getPkg();
        pkg=pkg.replaceAll("\\.","/");

        writeFile("src/main/java/"+pkg+"/"+StringUtil.getabc(table.getName())+"/bean",table.getName() + ".java", "bean");

        /*
         * StringWriter writer = new StringWriter(); template.process(root,
         * writer); System.out.println(writer.toString());
         */
    }


    public void getIdValid(){
        ZColum zcol =table.getPk();
        String type =zcol.getType().toLowerCase();
        List rules =new ArrayList();
        if(type.startsWith("varchar")){
            int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
            rules.add(String.format("new Length(%d)", length));
        }
        if(type.startsWith("int")){
            Integer integer=this.getIntFromKuoHao(type);
            if(integer!=null){
                rules.add(String.format("new Digits("+integer+",0)"));
            }else{
                rules.add(String.format("new Digits(10,0)"));
            }
        }
        if(StringUtil.isNotBlank(zcol.getValid())){
            String[] validAry= zcol.getValid().split(",,");
            for(int j=0;j<validAry.length;j++){
                if(validAry[j].toLowerCase().startsWith("regex")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    content=content.replace("\\", "\\\\");
                    rules.add(String.format("new Regex(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("email")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new EmailRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("phone")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new PhoneRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("alpha_numeric")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new AlphaNumbericUnderlineRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("alphanumeric")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new AlphaNumericRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("alpha")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new AlphaRule(%s)",content));
                }
                if(validAry[j].toLowerCase().startsWith("money")){
                    String content=StringUtil.getContentBetween(validAry[j], "(", ")");
                    rules.add(String.format("new MoneyRule(%s)",content));
                }
            }
        }
        StringBuffer sb =new StringBuffer();
        String ruleStr=StringUtil.join(",",rules.toArray());
        ruleStr=" new Rule[]{"+ruleStr+"}";
        sb.append(tab2+"vu.add(\""+zcol.getName()+"\", "+zcol.getName()+", \""+zcol.getRemark()+"\", "+ruleStr+");").append(ctrl);
        root.put("idvalid", sb);
    }
    public void preGenSql() throws IOException, TemplateException {
        logger.info("genSql");
        StringBuffer sql =new StringBuffer();
        sql.append("CREATE TABLE `").append(table.getTableName()).append("` (").append(ctrl);
        for(int i=0;i<table.getCols().size();i++){
            ZColum column = table.getCols().get(i);
            sql.append(tab+"`"+column.getName()+"` "+column.getType());
            if(column.isNn()){
                sql.append(" NOT NULL");
            }else{
                sql.append(" NULL");
                if(column.getType().toLowerCase().startsWith("timestamp")&&StringUtil.isBlank(column.getDef())){
                    sql.append(" DEFAULT NULL");
                }
            }
            if(column.isAi()){
                sql.append(" AUTO_INCREMENT");
            }
            if(StringUtil.isNotBlank(column.getDef())){
                if(column.getDef().toLowerCase().equals("now")){
                    sql.append(" CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
                }else{
                    sql.append(" DEFAULT "+column.getDef());
                }
            }
            sql.append(" COMMENT '"+column.getRemark()+"',").append(ctrl);
        }
        sql.append("PRIMARY KEY (`"+table.getPk().getName()+"`)").append(ctrl);
        sql.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='"+table.getRemark()+"';");
        root.put("sql", sql);


    }
    public void genSql() throws IOException, TemplateException {

        writeFile("",table.getName() + ".sql", "sql");


    }
    public void preGenListHtml() throws IOException, TemplateException {

        StringBuffer sb =new StringBuffer();
        List<ZColum> cols =table.getCols();
        for(int i=0;i<cols.size();i++){

            ZColum zcol =cols.get(i);
            String type =zcol.getType().toLowerCase();
            String commonStr= "id=\""+zcol.getName()+"\" name=\""+zcol.getName()+"\"  class=\"form-control input-sm\" ";
            if(!zcol.isList()){
                continue;
            }
            if(zcol!=null){
                sb.append(tab2+"<label for=\""+zcol.getName()+"\">"+zcol.getRemark()+"</label>").append(ctrl);

                if(zcol.getReferences()!=null) {
                    sb.append(tab3+String.format("<select "+commonStr+" >"
                            ,zcol.getName(),zcol.getName())).append(ctrl);

                    sb.append(tab4+"<option value=''>-请选择-</option>").append(ctrl);

                    sb.append(tab3+"</select>").append(ctrl);


                }else
                if(type.startsWith("varchar")){
                    int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
                    String tagName="input";
                 /*   if(length>50){
                        tagName="textarea";
                    }*/
             /*       sb.append(tab3+String.format("<input type=\"text\" "+commonStr+"  maxlength=\"%d\" placeholder=\""+zcol.getRemark()+"\" ></input>",
                            length)).append(ctrl);*/
                    sb.append(tab3+String.format("<input type=\"text\" "+ "id=\""+zcol.getName()+"Like\" name=\""+zcol.getName()+"Like\"  class=\"form-control input-sm\" "+"  maxlength=\"%d\" placeholder=\""+zcol.getRemark()+"\" ></input>",
                            length)).append(ctrl);


                }else
                if(type.startsWith("int")||type.startsWith("bigint")||type.startsWith("tinyint")){
                    //有一种checkbox 的选项
                    if(zcol.getShowValue()!=null){//genEditHtml();
                        sb.append(tab3+String.format("<select "+commonStr+" >"
                                ,zcol.getName(),zcol.getName())).append(ctrl);
                        Map<Integer, String> map =zcol.getShowValue();
                        sb.append(tab4+"<option value=''>-请选择-</option>").append(ctrl);
                        for (Map.Entry<Integer, String> entry : map.entrySet()) {
                            sb.append(tab4+"<option value=" + entry.getKey() + ">" + entry.getValue()+"</option>").append(ctrl);
                        }
                        sb.append(tab3+"</select>").append(ctrl);
                    }else{
                        Integer value =getIntFromKuoHao(type);
                        int maxlength=10;
                        if(value==null){
                            maxlength=10;
                        }else{
                            maxlength=value;
                        }
                        sb.append(tab3+String.format("<input type=\"number\" "+commonStr+" maxlength=\"%d\" onkeyup=\"chkInt(this,8)\" onafterpaste=\"chkInt(this,8)\" placeholder=\""+zcol.getRemark()+"\"></input>"
                                ,maxlength)).append(ctrl);
                    }
                }else
                if(type.startsWith("float") ||type.startsWith("double") ){
                    int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
                    int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
                    sb.append(tab3+String.format("<input type=\"number\" "+commonStr+" onkeyup=\"chkFloat(this,%d,%d)\" onafterpaste=\"chkFloat(this,%d,%d)\" placeholder=\""+zcol.getRemark()+"\" ></input>",
                            integer,fraction,integer,fraction)).append(ctrl);
                }else
                if(type.equals("date")||type.equals("datetime")||type.equals("timestamp")){
                    sb.append(getSearchFormItem(zcol));
                   /* sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\"  datatype=\"date\" format=\""+getYMDStr(type)+"\"  placeholder=\""+zcol.getRemark()+"\" ></input>")).append(ctrl);
                    sb.append(tab+"<label for=\""+zcol.getName()+"Begin\">"+zcol.getRemark()+"开始"+"</label>").append(ctrl);
                    sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\" datatype=\"date\" format=\""+getYMDStr(type)+"\"  placeholder=\""+zcol.getRemark()+"开始"+"\" ></input>")).append(ctrl);
                    sb.append(tab+"<label for=\""+zcol.getName()+"End\">"+zcol.getRemark()+"结束"+"</label>").append(ctrl);
                    sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\" datatype=\"date\" format=\""+getYMDStr(type)+"\" placeholder=\""+zcol.getRemark()+"结束"+"\" ></input>")).append(ctrl);*/
                }

            }
        }

        root.put("searchhtml", sb.toString());
        root.put(table.getName()+"searchhtml", sb.toString());

    }
    public void genListHtml() throws IOException, TemplateException {


        logger.info("genListHtml");
        writeFile("src/main/webapp/static/html",table.getName() + "List.html", "list");
        if(table.getMapper()!=null && table.getName().equals(table.getMapper().getMapper())){
            root.put("parentsearchhtml",root.get(table.getMapper().getParent()+"searchhtml"));
            root.put("parentTable",allTable.get(table.getMapper().getParent()));
            writeFile("src/main/webapp/static/html",table.getName() + "ListMapper.html", "listMapper");
        }
    }

    public String getSearchFormItem(ZColum zcol){
        String type =zcol.getType().toLowerCase();
        String commonStr= "id=\""+zcol.getName()+"\" name=\""+zcol.getName()+"\"  class=\"form-control input-sm\" ";
        StringBuffer sb =new StringBuffer();
        if(type.equals("date")||type.equals("datetime")||type.equals("timestamp")){
//            sb.append(tab2+"<label for=\""+zcol.getName()+"\">"+zcol.getRemark()+""+"</label>").append(ctrl);
            sb.append(tab2+"<div class=\"input-group\">").append(ctrl);
            sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\"  datatype=\"date\" format=\""+getYMDStr(type)+"\"  placeholder=\""+zcol.getRemark()+"\" ></input>")).append(ctrl);
            sb.append(tab3+"<label class=\"input-group-addon\" for=\""+zcol.getName()+"\" ><i class=\"fa fa-calendar\"></i></label>");
            sb.append(tab2+"</div>").append(ctrl);

            commonStr= "id=\""+zcol.getName()+"Begin\" name=\""+zcol.getName()+"Begin\"  class=\"form-control input-sm\" ";
            sb.append(tab2+"<label for=\""+zcol.getName()+"Begin\">"+zcol.getRemark()+"开始"+"</label>").append(ctrl);
            sb.append(tab2+"<div class=\"input-group\">").append(ctrl);
           sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\"  datatype=\"date\" format=\""+getYMDStr(type)+"\"  placeholder=\""+zcol.getRemark()+"开始\" ></input>")).append(ctrl);
            sb.append(tab3+"<label class=\"input-group-addon\" for=\""+zcol.getName()+"Begin\" ><i class=\"fa fa-calendar\"></i></label>");
            sb.append(tab2+"</div>").append(ctrl);

            commonStr= "id=\""+zcol.getName()+"End\" name=\""+zcol.getName()+"End\"  class=\"form-control input-sm\" ";
            sb.append(tab2+"<label for=\""+zcol.getName()+"End\">"+zcol.getRemark()+"结束"+"</label>").append(ctrl);
            sb.append(tab2+"<div class=\"input-group\">").append(ctrl);
           sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\"  datatype=\"date\" format=\""+getYMDStr(type)+"\"  placeholder=\""+zcol.getRemark()+"结束\" ></input>")).append(ctrl);
            sb.append(tab3+"<label class=\"input-group-addon\" for=\""+zcol.getName()+"End\" ><i class=\"fa fa-calendar\"></i></label>");
            sb.append(tab2+"</div>").append(ctrl);
        }
        return sb.toString();
       /* <div class="input-group ">
        <input type="text" class="form-control input-sm" placeholder="Username" aria-describedby="basic-addon1">
        <span class="input-group-addon input-sm" id="basic-addon1"><i class="fa fa-calendar"></i></span>
      </div>*/

    }
    public void preGenEditHtml() throws IOException, TemplateException {

        logger.info("genEditHtml");
        /*
        <#if col.name!=table.pk.name>
        <div class="form-group">
           <label for="${col.name}" class="col-sm-2 control-label">${col.remark}:</label>
           <div class="col-sm-10">
             <input   <#if col.type!=="int"> type="number" onkeyup="chkFloat(this,8,2)" onafterpaste="chkFloat(this,8,2)" </#if>  <#if col.type=='timestamp'> onClick="WdatePicker()" </#if>
              class="form-control" id="${col.name}" placeholder="">
           </div>
        </div>
        </#if>
        */
        StringBuffer sb =new StringBuffer();

        List<ZColum> cols =table.getCols();
        root.put("reference",null);
        root.put("ueditor",null);
        root.put("editimgjs",null);
        for(int i=0;i<cols.size();i++){
            ZColum zcol =cols.get(i);
            String type =zcol.getType().toLowerCase();
            String commonStr= "id=\""+zcol.getName()+"\" name=\""+zcol.getName()+"\"  class=\"form-control input-sm\" ";
            if(zcol.isPk() && !zcol.isEdit() /*&& !zcol.isEdit()*/){
                sb.append(tab+"<input type=\"hidden\" "+commonStr+">").append(ctrl);
            }else{

                if(!zcol.isEdit()){
                    sb.append(tab+"<div style=\"display:none\" class=\"form-group\">").append(ctrl);
                }else
                    sb.append(tab+"<div class=\"form-group\">").append(ctrl);
                sb.append(tab2+String.format("<label for=\"%s\" class=\"col-sm-2 control-label\">%s:</label>",zcol.getName(),zcol.getRemark())).append(ctrl);
                if(type.equals("date")||type.equals("datetime")||type.equals("timestamp")){
                    sb.append(tab2+String.format("<div class=\"input-group date\">")).append(ctrl);
                }else{
                    sb.append(tab2+String.format("<div class=\"col-sm-10\">")).append(ctrl);
                }

                if(zcol.getReferences()!=null){//如果是其他表的引用
                    //应该选用select
                        /*
                        <select>
                         */
                    sb.append(tab3+String.format("<select  "+commonStr+" >"
                            ,zcol.getName(),zcol.getName())).append(ctrl);
                    Map<Integer, String> map =zcol.getShowValue();
                    sb.append(tab4+"<option value=''>-请选择-</option>").append(ctrl);

                    sb.append(tab3+"</select>").append(ctrl);
                    String refrences= zcol.getReferences();
                    String[] ary = refrences.split("\\.");
                    ZTable refTable = allTable.get(zcol.getReferences().split("\\.")[0]);
                    String js=
                            tab2+"Ajax.getJSON(PATH+\"/"+StringUtil.getabc(refTable.getName())+"/list.json"+"\",{},function(data){"+ctrl
                            +tab3+"fillSelectWithJso(\""+zcol.getName()+"\",data.data,\""+zcol.getReferences().split("\\.")[1]+"\",\""+zcol.getReferences().split("\\.")[2]+"\");"+ctrl
                           ;
                    // +tab2+"});";
                    root.put("reference",js);
                }else
                if(type.startsWith("varchar")||type.startsWith("char")){
                    //可能是图片或者文件上传
                    int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
                    String tagName="input";
                    if(length>10000){
                        root.put("ueditor",zcol.getName());
                        sb.append(" <script id=\"editor\" type=\"text/plain\" style=\"width:1024px;height:500px;\"></script>");
                    }
                    if(length>50){
                        tagName="textarea";
                    }
                    if("img".equals(zcol.getFile())){
                        sb
                        .append("<input  id=\""+zcol.getName()+"\" name=\""+zcol.getName()+"\"  value=\""+zcol.getDef()+"\" style=\"display:none\" class=\"form-control input-sm\"   maxlength=\""+length+"\"></input>");
                        root.put("editimgjs",zcol.getName());
                    }else
                    if(zcol.getShowValue()!=null){
                        sb.append(tab3+String.format("<select  "+commonStr+" >"
                                ,zcol.getName(),zcol.getName())).append(ctrl);
                        Map<Integer, String> map =zcol.getShowValue();
                        sb.append(tab4+"<option value=''>-请选择-</option>").append(ctrl);
                        for (Map.Entry<Integer, String> entry : map.entrySet()) {
                            sb.append(tab4+"<option value=" + entry.getKey() + ">" + entry.getValue()+"</option>").append(ctrl);
                        }
                        sb.append(tab3+"</select>").append(ctrl);
                    }else {
                        sb.append(tab3 + String.format("<%s %s " + commonStr + "  maxlength=\"%d\" %s ></%s>",
                                tagName, tagName.equals("input") ? " type=\"text\" " : "", length,length>10000?"style=\"display:none\"":"", tagName)).append(ctrl);
                    }
                }else
                if(type.startsWith("int")|| type.startsWith("bigint")|| type.startsWith("tinyint")){
                    //有一种checkbox 的选项
                    if(zcol.getShowValue()!=null){
                        sb.append(tab3+String.format("<select  "+commonStr+" >"
                                ,zcol.getName(),zcol.getName())).append(ctrl);
                        Map<Integer, String> map =zcol.getShowValue();
                        sb.append(tab4+"<option value=''>-请选择-</option>").append(ctrl);
                        for (Map.Entry<Integer, String> entry : map.entrySet()) {
                            sb.append(tab4+"<option value=" + entry.getKey() + ">" + entry.getValue()+"</option>").append(ctrl);
                        }
                        sb.append(tab3+"</select>").append(ctrl);
                    }else{
                        Integer value =getIntFromKuoHao(type);
                        int maxlength=10;
                        if(value==null){
                            maxlength=10;
                        }else{
                            maxlength=value;
                        }
                        sb.append(tab3+String.format("<input type=\"number\" "+commonStr+" maxlength=\"%d\" onkeyup=\"chkInt(this,8)\" onafterpaste=\"chkInt(this,8)\"></input>"
                                ,maxlength)).append(ctrl);
                    }
                }else
                if(type.startsWith("float") ||type.startsWith("double") ){
                    int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
                    int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
                    sb.append(tab3+String.format("<input type=\"number\" "+commonStr+" onkeyup=\"chkFloat(this,%d,%d)\" onafterpaste=\"chkFloat(this,%d,%d)\"></input>",
                            zcol.getName(), zcol.getName(),integer,fraction,integer,fraction)).append(ctrl);
                }else
                if(type.equals("date")||type.equals("datetime")||type.equals("timestamp")){
                    sb.append(tab3+"<span class=\"input-group-addon\" for=\""+zcol.getName()+"\" ><i class=\"fa fa-calendar\"></i></span>");

                    sb.append(tab3+String.format("<input type=\"text\" "+commonStr+" onClick=\"WdatePicker({dateFmt:'"+getYMDStr(type)+"'})\"  datatype=\"date\" format=\""+getYMDStr(type)+"\"  ></input>",zcol.getName(),zcol.getName())).append(ctrl);
                  }
                sb.append(tab2+String.format("</div>")).append(ctrl);
                sb.append(tab+String.format("</div>")).append(ctrl);
            }
        }

        root.put("edithtml", sb.toString());

    }
    public void genEditHtml() throws IOException, TemplateException {

        writeFile("src/main/webapp/static/html",table.getName() + "Edit.html", "edit");
    }
    public void preGenViewHtml()  throws IOException, TemplateException{
        logger.info("genViewHtml");
        StringBuffer sb =new StringBuffer();
        List<ZColum> cols =table.getCols();
        for(int i=0;i<cols.size();i++){
            ZColum zcol =cols.get(i);
            String type =zcol.getType().toLowerCase();
            if(zcol.isPk()&& !zcol.isEdit()){
                sb.append(tab+"<input type=\"hidden\" id=\""+zcol.getName()+"\" name=\""+zcol.getName()+"\">").append(ctrl);
            }else{
                sb.append(tab+"<div class=\"form-group\">").append(ctrl);
                sb.append(tab2+String.format("<label for=\"%s\" class=\"col-sm-2 control-label\">%s:</label>",zcol.getName(),zcol.getRemark())).append(ctrl);
                sb.append(tab2+String.format("<div class=\"col-sm-10\">")).append(ctrl);
                 if(zcol.getReferences()!=null){
                    String[] ary = zcol.getReferences().split("\\.");
                ary[2]=StringUtil.getabc(ary[0]+"_"+ary[2]);
                     sb.append(tab3+String.format("<span name=\"%s\" id=\"%s\"  class=\"form-control\"  ></span>",
                             ary[2],ary[2])).append(ctrl);
                }else
                if(type.startsWith("varchar")){
                    int length=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(")")));
                    String tagName="input";
                    if(length>50){
                        tagName="textarea";
                    }
                    if("img".equals(zcol.getFile())){
                        sb
                                .append("<img  id=\""+zcol.getName()+"\" src=\""+zcol.getDef()+"\" name=\""+zcol.getName()+"\" class=\"row-10 img-upload\"   ></img>");
                        root.put("editimgjs",zcol.getName());
                    }else
                    if(zcol.getShowValue()!=null){
                        sb.append(tab3+String.format("<span  name=\"%s\" id=\"%s\" datatype=\"map\" data=\"{"
                                ,zcol.getName(),zcol.getName()));
                        Map<String, String> map =zcol.getShowValue();
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            sb.append("'"+entry.getKey() + "':'" + entry.getValue()+"',");
                        }
                        sb.append("}\" class=\"form-control\" ></span>").append(ctrl);
                    }else
                        sb.append(tab3+String.format("<span name=\"%s\" id=\"%s\"  class=\"form-control\"  ></span>",
                                zcol.getName(),zcol.getName()/*,length,tagName*/)).append(ctrl);
                }else
                if(type.startsWith("int")||type.startsWith("bigint")||type.startsWith("tinyint")){
                    //有一种checkbox 的选项
                    if(zcol.getShowValue()!=null){
                        sb.append(tab3+String.format("<span  name=\"%s\" id=\"%s\" datatype=\"map\" data=\"{"
                                ,zcol.getName(),zcol.getName()));
                        Map<Integer, String> map =zcol.getShowValue();
                        for (Map.Entry<Integer, String> entry : map.entrySet()) {
                            sb.append("'"+entry.getKey() + "':'" + entry.getValue()+"',");
                        }
                        sb.append("}\" class=\"form-control\" ></span>").append(ctrl);
                    }else{
                        Integer value =getIntFromKuoHao(type);
                        int maxlength=10;
                        if(value==null){
                            maxlength=10;
                        }else{
                            maxlength=value;
                        }
                        sb.append(tab3+String.format("<span  name=\"%s\" id=\"%s\" class=\"form-control\"></span>"
                                ,zcol.getName(),zcol.getName()/*,maxlength*/)).append(ctrl);
                    }
                }else
                if(type.startsWith("float") ||type.startsWith("double") ){
                    int integer=Integer.valueOf(type.substring(type.indexOf("(")+1, type.indexOf(",")));
                    int fraction=Integer.valueOf(type.substring(type.indexOf(",")+1, type.indexOf(")")));
                    sb.append(tab3+String.format("<span  name=\"%s\" id=\"%s\" class=\"form-control\"></span>",
                            zcol.getName(), zcol.getName()/*,integer,fraction,integer,fraction*/)).append(ctrl);
                }else
                if(type.equals("date")||type.equals("datetime")||type.equals("timestamp")){
                    sb.append(tab3+String.format("<span class=\"form-control\" datatype=\"date\" format=\""+getYMDStr(type)+"\" name=\"%s\" id=\"%s\" ></span>",zcol.getName(),zcol.getName())).append(ctrl);
                }
                sb.append(tab2+String.format("</div>")).append(ctrl);
                sb.append(tab+String.format("</div>")).append(ctrl);
            }
        }
        root.put("viewhtml", sb.toString());

    }
    public void genViewHtml()  throws IOException, TemplateException{

        writeFile("src/main/webapp/static/html",table.getName() + "View.html", "view");
    }
    public String  getSearchBar(){
        StringBuffer sb =new StringBuffer();
        List<ZColum> cols =table.getCols();
        for(int i=0;i<cols.size();i++){
            ZColum col =cols.get(i);
            sb.append(" input type=\"text\" class=\"form-control\" id=\""+col.getName()+"\" name=\""+col.getName()+"\" placeholder=\""+col.getRemark()+"\">");
        }
        return sb.toString();
        
    }

    public static Generator generate(String[] codes){
        Generator gen = new Generator();
        
        try {
            for (int i = 0; i < codes.length; i++) {
                gen.init(codes[i]);
            }
            for (int i = 0; i < codes.length; i++) {


            }
            for (int i = 0; i < codes.length; i++) {
                gen.intTpl(codes[i]);
                gen.preGenBean();
                gen.preGenService();
                gen.preGenMapper();
                gen.preGenController();
                gen.preGenSql();
                gen.preGenMapperXml();
                gen.preGenListHtml();
                gen.preGenEditHtml();
                gen.preGenViewHtml();


                gen.genBean();
                gen.genService();
                gen.genMapper();
                gen.genController();
                gen.genSql();
                gen.genMapperXml();
                gen.genListHtml();
                gen.genEditHtml();
                gen.genViewHtml();
            }


        }
        // System.out.println(table.getCols().size());
        // gen.genController(table);
        // gen.genBean(table);
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gen;
    }
    public static void main(String[] args) {
        Generator generator =new Generator();
        generator.generate(new String[]{"SysRole","SysUser","SysUserRole","SysMenu","SysRoleMenu"});
        //用户模块
       // Generator.generate(new String[]{"Pwdrst"});
       // Generator.generate(new String[]{/*"SysResource",*/"SysRole","SysUser","SysUserRole"});
        // "SysResource","SysRole","SysUser","SysUserRole",
      //  "SysRoleResource","SysUserResource"
        //"SysUser","VideoNew","VideoHot","Collect"
        //"Expert"/*"Artical",*//*"Partner"*/
     /*Generator.generate(new String[]{*//*"SysMenu","SysUser","SysRole","SysPermission","SysUserRole","SysUserPermission","SysRolePermission","Artical","Expert","ExpertDetail","ExpertArtical","Partner","PartnerDetail",
             "SysLog",*//*"Component","SysConfig","Template","EditorTempComp","Goods"
            });*/

        //Generator.generate(new String[]{"WiiDeviceExtend" });
     // Generator.generate(new String[]{"SysConfig" });
      //  Generator.generate(new String[]{"MerchantExtends","DeviceExtends" });
       // Generator.generate(new String[]{"SysLogTag" });
       //Generator.generate(new String[]{"SmsRecord" });
        //Generator.generate(new String[]{"Activity" });
        //Generator.generate(new String[]{"MerchantPic","MerchantNews","MerchantNotice" });
       // Generator.generate(new String[]{"Consume","Order","OrderDetail","Item","ItemInfo" ,"Merchant"});

        //Generator.generate(new String[]{"OperLog"});
       // Generator.generate(new String[]{"ApiCategory","ApiUrl","ApiParameter"});
       // Generator.generate(new String[]{"MapData"});
        //Generator.generate(new String[]{"Device","Gateway"});
    }

  
}
