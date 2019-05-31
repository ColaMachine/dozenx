package com.dozenx.web.module.checkin.faceCheckinOut.bean;

import com.dozenx.util.JsonUtil;
import com.dozenx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 19:27 2019/4/17
 * @Modified By:
 */
public class FastYml {
    private Logger logger = LoggerFactory.getLogger(FastYml.class);
    public static void main(String args[]){

        Map map =new FastYml().readAsMap(new File("G:\\workspace\\dozenx\\ui\\src\\main\\resources\\alpha\\config.yml"));
        System.out.println(JsonUtil.toJsonString(map));
    }
    class TreeNode {
        public TreeNode parentNode;
        public List<TreeNode> childNodes;
        public int indent;
        public String key;
        public boolean isLeaf;
        public String txtVal;
        public Object value;

        public TreeNode(int indent, String key) {
            this.indent = indent;
            this.key = key;
        }

        public TreeNode(int indent, String key, String value) {
            this.indent = indent;
            this.key = key;
            this.txtVal = value;
        }

        public void addChild(TreeNode child) {

            if (childNodes == null) {
                childNodes = new ArrayList<>();
            }
            this.childNodes.add(child);
            child.parentNode = this;
        }
    }

    public Map readAsMap(File file) {
        TreeNode node = readAsTreeNode(file);
        Map<String, Object> map = new HashMap<>();

        for (TreeNode childNode : node.childNodes) {
            map.put(childNode.key, getNodeVal(childNode));
        }
        return map;
    }

    public Object getNodeVal(TreeNode treeNode) {

        if (treeNode.isLeaf) {
            return treeNode.txtVal;
        } else {
            HashMap<String, Object> map = new HashMap<>();
            for (TreeNode childNode : treeNode.childNodes) {

                map.put(childNode.key, getNodeVal(childNode));
            }
            return map;
        }

    }

    public TreeNode readAsTreeNode(File file) {

        TreeNode rootNode = new TreeNode(-2, "");
        try {
            //依次读取每一行的代码
            //System.out.println(obj);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String lineStr = "";
            boolean isLeaf = true;//是否是子节点

            TreeNode lastNode = rootNode;
            //String lastPropertiesKey ="";
            //String lastPropertiesValue ="";
            try {
                while ((lineStr = bufferedReader.readLine()) != null) {

                    if (StringUtil.isBlank(lineStr.trim())) {
                        continue;
                    }
                    if (lineStr.indexOf("#") > -1) {
                        lineStr = lineStr.substring(0, lineStr.indexOf("#"));
                        if (StringUtil.isBlank(lineStr.trim())) {
                            continue;
                        }
                    }
                    //logger.info("read==>"+lineStr);
                    //获取前面有几个空格
                    int whiteSpaceNum = getWitheSpaceNumPrefix(lineStr);
                    int semicolonIndex = lineStr.indexOf(":");
                    String key = lineStr.substring(0, semicolonIndex).trim();
                    if(key.equals("mybatis")){
                        logger.info(key);
                    }
                    String value = lineStr.substring(semicolonIndex + 1).trim();
                    TreeNode nowNode = new TreeNode(whiteSpaceNum, key, value);

                    int indentChanged = 0;

                    indentChanged = whiteSpaceNum - lastNode.indent;//如果缩进大于2 就认为有变化
                    //lastIndent= whiteSpaceNum;
                    if (StringUtil.isNotBlank(value)) {
                        //不允许有子节点了
                        isLeaf = true;
                        // logger.info(getStackStr(stack) +" "+key+":"+value);
                    } else {
                        isLeaf = false;
                    }
                    nowNode.isLeaf = isLeaf;
                    if (Math.abs(indentChanged) >= 2) {
                        //认为是发生了变化的
                        //否者是同级
                        if (indentChanged > 0) {//说明是子节点  addLeaf
                            lastNode.addChild(nowNode);
                        } else {// 上级 上上级 ....
                            TreeNode parentNode = lastNode.parentNode;
                            while (parentNode.indent >= whiteSpaceNum) {
                                parentNode = parentNode.parentNode;
                                if(parentNode==null){
                                    logger.error("取到最高级了");
                                }
                            }
                            parentNode.addChild(nowNode);
                            //回退到之前同级的
                        }
                    } else {// 同级目录
                        lastNode.parentNode.addChild(nowNode);
                    }
                    if (isLeaf) {
                        //logger.info(getStackStr(stack) + ":" + value);
                    }
                    lastNode = nowNode;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rootNode;
    }

    public Properties readAsProperties(File file) {
        Properties properties = new Properties();
        try {
            //依次读取每一行的代码
            //System.out.println(obj);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String lineStr = "";
            boolean isLeaf = true;//是否是子节点
            LinkedList<TreeNode> stack = new LinkedList<>();
            //String lastPropertiesKey ="";
            //String lastPropertiesValue ="";
            int lastIndent = 0;
            try {
                while ((lineStr = bufferedReader.readLine()) != null) {
                    if (StringUtil.isBlank(lineStr.trim())) {
                        continue;
                    }
                    if (lineStr.indexOf("#") > -1) {
                        lineStr = lineStr.substring(0, lineStr.indexOf("#"));
                        if (StringUtil.isBlank(lineStr.trim())) {
                            continue;
                        }
                    }
                    //logger.info("read==>"+lineStr);
                    //获取前面有几个空格
                    int whiteSpaceNum = getWitheSpaceNumPrefix(lineStr);
                    int fenhaoIndex = lineStr.indexOf(":");
                    String key = lineStr.substring(0, fenhaoIndex).trim();
                    String value = lineStr.substring(fenhaoIndex + 1).trim();
                    int indentChanged = 0;
                    if (stack.size() != 0) {
                        indentChanged = whiteSpaceNum - stack.peek().indent;//如果缩进大于2 就认为有变化
                    } else {
                        indentChanged = 0;//如果是第一行读取的时候 statck是空的
                    }
                    //lastIndent= whiteSpaceNum;
                    if (StringUtil.isNotBlank(value)) {
                        //不允许有子节点了
                        isLeaf = true;
                        // logger.info(getStackStr(stack) +" "+key+":"+value);
                    } else {
                        isLeaf = false;
                    }
                    if (Math.abs(indentChanged) >= 2) {
                        //认为是发生了变化的
                        //否者是同级
                        if (indentChanged > 0) {
                            stack.push(new TreeNode(whiteSpaceNum, key));
                        } else {
                            TreeNode indentKey = stack.poll();
                            while (indentKey.indent > whiteSpaceNum) {
                                indentKey = stack.poll();
                            }
                            //回退到之前同级的
                            stack.push(new TreeNode(whiteSpaceNum, key));
                        }
                    } else {//同级的
                        stack.poll();
                        stack.push(new TreeNode(whiteSpaceNum, key));
                    }
                    if (isLeaf) {
                        properties.put(getStackStr(stack), value);
                        logger.info(getStackStr(stack) + ":" + value);
                    }
//                    if(indentChanged==1){
//                       // logger.info("indentChanged:"+indentChanged);
//                        stack.push(key);
//                    }else if(indentChanged<=0){
//                        for(int i=0;i>=indentChanged;i--){
//                            stack.poll();
//                        }
//
//                        stack.push(key);
//                        //logger.info(lastPropertiesKey+":"+lastPropertiesValue);
//                       //lastPropertiesKey =  getStackStr(stack);
//                        //lastPropertiesValue=value;
//
//
//                    }else{
//                        logger.info("错误的缩进"+lineStr);
//                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getStackStr(LinkedList<TreeNode> stack) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = stack.size() - 1; i >= 0; i--) {
            stringBuffer.append(stack.get(i).key);
            if (i == 0) {
                stringBuffer.append(".");
            }
        }
        return stringBuffer.toString();
        // logger.info(stringBuffer.toString());
    }

    public int getWitheSpaceNumPrefix(String lineStr) {
        int i = 0;
        int count = 0;
        while (i <= lineStr.length() - 1 && lineStr.charAt(i) == ' ') {
            count++;
            i++;
        }
        return count;
    }
}
