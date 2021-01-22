package com.dozenx.web.core.tree;

import com.dozenx.common.util.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:04 2019/12/16
 * @Modified By:
 */
public class TreeNode {

    int pid;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    int used;
    String name;
    List<TreeNode> children;
    public void addChild(TreeNode  treeNode){
        if(children==null){
            children=new ArrayList<>();
        }
        children.add(treeNode);
    }
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeNode(int id, String name){
        this.pid=id;
        this.name =name;
    }

}
