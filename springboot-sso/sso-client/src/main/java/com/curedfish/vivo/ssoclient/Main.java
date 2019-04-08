package com.curedfish.vivo.ssoclient;

import java.util.Stack;

public class Main {
    public static void main(String[]args){
         TreeNode node = new TreeNode(1);
         TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        TreeNode node5 = new TreeNode(6);
        node.left = node1;
        node.right = node4;
        node1.left = node2;
        node1.right = node3;
        node4.right = node5;
        flatten(node);

    }

    public static  void flatten(TreeNode root) {
        Stack<TreeNode> stack = new Stack();
        TreeNode node = root;
       while (!stack.empty()  ||   node!= null){
           while (node != null){
               stack.push(node);
               node = node.left;
           }
           node = stack.pop();
           System.out.println(node.val);
           node = node.right;
       }
    }
}
