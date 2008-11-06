/*
 *    Mapfaces - 
 *    http://www.mapfaces.org
 *
 *    (C) 2007 - 2008, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.mapfaces.models.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Tools for create and manipulate TreeTableModel and TreeNodeModel
 * @author Kevin Delfour (Geomatys)
 */
public class TreeModelsUtils {

    private int count = 0;

    /**
     * Get a count of treenode in a treetable
     * @param tree a TreeTableModel to explore
     * @return a count as an Int
     */
    public int getTreeNodeCount(final TreeTableModel tree) {
        return SsTreeNodeCount(tree.getRoot());
    }

    private int SsTreeNodeCount(final TreeNodeModel node) {
        int result = 1;
        for (int i = 0; i < node.getChildCount(); i++) {
            result += SsTreeNodeCount((TreeNodeModel) node.getChildAt(i));
        }
        return result;
    }

    /**
     * Method to transform a DefaultMutableTreeNode to a TreeNodeModel
     * @param node the DefaultMutableTreeNode  to change
     * @param id the id give to identify the node as a String
     * @param depth 
     * @param row 
     * @return a TreeNodeModel node with an id and a content
     */
    public TreeNodeModel transformNode(final DefaultMutableTreeNode node, final int id, final int depth, final int row) {
        if (node.getUserObject() == null) {
            node.setUserObject("NoName");
        }
        final TreeNodeModel treenode = new TreeNodeModel(node.getUserObject(), id, depth, row);
//        System.out.println("NODE "+id +" with value :"+node.getUserObject());
//        if (depth > TreeTableConfig.getDEFAULT_DEPTH_VIEW()) {
//            treenode.setChecked(false);
//        }
        return treenode;
    }

    /**
     * Method to transform a DefaultTreeModel to a TreeTableModel
     * @param tree Initial tree to transform
     * @return a TreeTableModel
     */
    public TreeTableModel transformTree(final DefaultTreeModel tree) {
        count = 0;
        DefaultMutableTreeNode initial_root = (DefaultMutableTreeNode) tree.getRoot();
        final TreeNodeModel root;
        if (initial_root == null || initial_root.getUserObject() == null) {
            initial_root = new DefaultMutableTreeNode("NoValue");
            root = new TreeNodeModel(initial_root.getUserObject(), count, 0, count);
        } else if (!initial_root.toString().equals("root")) {
            root = new TreeNodeModel(new DefaultMutableTreeNode("root"), count, 0, count);
            final TreeNodeModel child = new TreeNodeModel(initial_root.getUserObject(), count, 0, count);
            root.add(child);
        } else {
            root = new TreeNodeModel(initial_root.getUserObject(), count, 0, count);
        }

        count++;
        
        final int depthnode = root.getDepth() + 1;
        for (int i=0, n=initial_root.getChildCount(); i<n; i++) {
            final DefaultMutableTreeNode child = (DefaultMutableTreeNode) initial_root.getChildAt(i);
            if (initial_root.getChildAt(i).isLeaf()) {
                final TreeNodeModel leaf = transformNode(child, count, depthnode, count);
                root.add(leaf);
            } else {
                root.add(sstransformTree(root, child));
            }
            count++;
        }

        return new TreeTableModel(root);
    }

    private TreeNodeModel sstransformTree(TreeNodeModel parent, DefaultMutableTreeNode node) {

        final TreeNodeModel leaf = transformNode(node, count, parent.getDepth() + 1, count);

        if (!node.isLeaf()) {
            for (int i = 0,n=node.getChildCount(); i<n; i++) {
                count++;
                leaf.add(sstransformTree(leaf, (DefaultMutableTreeNode) node.getChildAt(i)));
            }
        }

        return leaf;
    }

    /**
     * Print a TreeTableModel Structure
     * @param tree the tree to print
     */
    public void printTree(final TreeTableModel tree) {
        final TreeNodeModel root = tree.getRoot();
        
        if(root.isLeaf()){
            System.out.println("Tree is empty");
            return;
        }
        
        System.out.println("Tree content is :");
        for (int i=0, n=root.getChildCount(); i<n ; i++) {
            ssprintTree( (TreeNodeModel) root.getChildAt(i) );
        }
        
    }

    private void ssprintTree(final TreeNodeModel node) {
        if (node.isLeaf()) {
            System.out.println("(leaf)" + node + "->" + node.getParent());
        } else {
            System.out.println("(node)" + node + "->" + node.getParent());
            for (int i=0, n = node.getChildCount(); i<n; i++) {
                ssprintTree( (TreeNodeModel) node.getChildAt(i) );
            }
        }
    }

    public TreeTableModel moveTo(final TreeTableModel tree, final int movedNode, final int targetNode) {

        if (tree.getById(movedNode).getParent().equals(tree.getById(targetNode))) {
            //Change the row of the movedNode
        } else {
            final TreeNodeModel tmpMmovedNode = tree.getById(movedNode);
            tree.getById(movedNode).removeFromParent();
            tree.getById(targetNode).insert(tmpMmovedNode, 0);
        }
        return tree;
    }

    public TreeTableModel moveTo(final TreeTableModel tree, final int movedNode, final int targetNode, final int position) {
        if (tree.getById(movedNode).getParent().equals(tree.getById(targetNode))) {
            //Change the row of the movedNode
        } else {
            final TreeNodeModel tmpMmovedNode = tree.getById(movedNode);
            tree.getById(movedNode).removeFromParent();
            if (targetNode == 0) {
                tree.getRoot().insert(tmpMmovedNode, position);
            } else {
                if (position > tree.getById(targetNode).getChildCount()) {
                    tree.getById(targetNode).add(tmpMmovedNode);
                } else {
                    tree.getById(targetNode).insert(tmpMmovedNode, position);
                }
            }
        }
        return tree;
    }

    public TreeTableModel insertBefore(final TreeTableModel tree, final int movedNode, final int nodeBefore) {
        final TreeNodeModel MovedNode   = tree.getById(movedNode);
        final TreeNodeModel NodeBefore  = tree.getById(nodeBefore);
        final TreeNodeModel ParentNode  = (TreeNodeModel) NodeBefore.getParent();
        final int position              = ParentNode.getIndex(NodeBefore);

        tree.getById(movedNode).removeFromParent();
        final int targetNode = ((TreeNodeModel) tree.getById(nodeBefore).getParent()).getId();

        if (targetNode == tree.getRoot().getId()) {
            tree.getRoot().insert(MovedNode, position);
        } else {
            tree.getById(targetNode).insert(MovedNode, position);
        }
        return tree;
    }

    public TreeTableModel insertAfter(final TreeTableModel tree, final int movedNode, final int nodeAfter) {
        final TreeNodeModel MovedNode   = tree.getById(movedNode);
        final TreeNodeModel NodeBefore  = tree.getById(nodeAfter);
        final TreeNodeModel ParentNode  = (TreeNodeModel) NodeBefore.getParent();
        final int position              = ParentNode.getIndex(NodeBefore) + 1;

        tree.getById(movedNode).removeFromParent();
        final int targetNode = ((TreeNodeModel) tree.getById(nodeAfter).getParent()).getId();

        if (targetNode == tree.getRoot().getId()) {
            tree.getRoot().insert(MovedNode, position);
        } else {
            tree.getById(targetNode).insert(MovedNode, position);
        }
        return tree;
    }
}

