package com.dozenx.web.core.tree;

import com.dozenx.common.util.MapUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: dozen.zhang
 * @Description:
 * @Date: Created in 17:04 2019/12/16
 * @Modified By:
 */
public class TreeData {
    public TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void loadData(List<TreeNode> list){

        addChildNode(root,list);

    }

/*
    public List<TreeData> getPathChild(int ... id){

        for(int i=0;i<id.length;i++){

        }

    }
    public List getChildren(int id ){

    }*/
/*

    public void  changeListToTreeNode(List<HashMap> list){
        int rootId =0;
        TreeNode treeNode = new TreeNode(0,"root");
        addChildNodeMap(treeNode,list);
    }*/

    public void addChildNodeMap(TreeNode parentNode,List<HashMap> list ){
        for(HashMap map : list){
            int used = MapUtils.getIntValue(map,"used");
            if(used==1)
                continue;
            int pid = MapUtils.getInteger(map,"pid");
            int id= MapUtils.getInteger(map,"id");
            int type=MapUtils.getInteger(map,"type");
            String name = MapUtils.getString(map,"name");
            if(parentNode.getPid()== pid){
                TreeNode childNode = new TreeNode(id,name,pid,type);

                addChildNodeMap(childNode,list);
                parentNode.addChild(childNode);
            }
            map.put("used",1);
        }
    }

    public void addChildNode(TreeNode parentNode,List<TreeNode> list ){
        for(TreeNode listItemNode : list){
            int used = listItemNode.used;
            if(used==1)
                continue;
            if(parentNode.getId()== listItemNode.getPid()){
                listItemNode.used=1;
                int id= listItemNode.getId();
                String name = listItemNode.getName();
                TreeNode childNode = new TreeNode(id,name,listItemNode.getPid(),listItemNode.type); //创建子程序
                parentNode.addChild(childNode);
                addChildNode(childNode,list);//递归调用
            }

        }
    }

    //基本写完 然后考虑缓存的作用  为什么前端要用三级联动 直接给一棵树不就得了

    //所以缓存要分两种 数据量不大的时候可以直接给整棵树

    /***
     *
     *
     * 数据量不大的时候可以给整棵树
     * 数据量大的时候给当前的叶子
     *
     * 数据的权限
     *
     *
     */
}
