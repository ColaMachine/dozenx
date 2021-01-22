package com.dozenx.web.core.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:04 2019/12/16
 * @Modified By:
 */
public class TreeNode {
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

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

    public TreeNode(int id, String name,int pid){
        this.pid=pid;
        this.id=id;
        this.name =name;
    }
    public TreeNode(int id, String name,int pid,int type){
        this.pid=pid;
        this.id=id;
        this.name =name;
        this.type = type;
    }

}
