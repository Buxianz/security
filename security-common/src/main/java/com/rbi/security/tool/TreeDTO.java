package com.rbi.security.tool;

import java.util.ArrayList;
import java.util.List;

//使用这个必须使用AbTreeInfo 接口作为父类
public class TreeDTO {
    public static <T extends AbTreeInfo> List<T> listToTree(List<T> list) {
        //用递归找子。
        List<T> treeList = new ArrayList<T>();
        for (T tree : list) {
            if (tree.getParentId() == 0) {
                treeList.add(findChildren(tree, list));
            }
        }
        return treeList;
    }
    private static  <T extends AbTreeInfo>  T findChildren(T tree, List<T> list) {
        for (T node : list) {
            if (node.getParentId() == tree.getId()) {
                if (tree.getChiled() == null) {
                    tree.setChiled(new ArrayList<T>());
                }
                tree.getChiled().add(findChildren(node, list));
            }
        }
        return tree;
    }
}
