/**
 * Copyright 2016 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.core.tree;

import java.awt.Color;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.IdProperty;
import org.jebtk.core.collections.ReverseIterator;
import org.jebtk.core.collections.Reversible;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonObject;
import org.jebtk.core.json.JsonRepresentation;
import org.jebtk.core.path.Path;
import org.jebtk.core.text.TextUtils;
import org.jebtk.core.xml.XmlRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents a node in a tree hierarchy. A node may contain a data object as
 * well as child nodes.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class TreeNode<T> implements IdProperty, Iterable<TreeNode<T>>, Reversible<TreeNode<T>>, Comparable<TreeNode<T>>,
    Cloneable, TreeNodeEventProducer, XmlRepresentation, JsonRepresentation, Serializable, ITreeNodeEventListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant NEXT_ID.
   */
  private static final AtomicInteger NEXT_ID = new AtomicInteger(1);

  /**
   * The member id.
   */
  // Unique id for the node
  private final int mId = NEXT_ID.getAndIncrement();

  // public static final String NODE_CHANGED = "node_changed";
  // public static final String NODE_ADDED = "node_added";
  // public static final String NODE_NAME_CHANGED = "node_name_changed";

  /**
   * The constant PATH_DELIMITER.
   */
  public static final String PATH_DELIMITER = "/";

  /**
   * The member listeners.
   */
  private final TreeNodeEventListeners mListeners = new TreeNodeEventListeners();

  /**
   * The member children.
   */
  protected List<TreeNode<T>> mChildren = new ArrayList<>();

  // private Map<Integer, TreeNode<T>> childMap =
  // new HashMap<Integer, TreeNode<T>>();

  /**
   * The member value.
   */
  protected T mValue = null;

  /**
   * The member is expanded.
   */
  private boolean mIsExpanded = false;

  /**
   * The member is expandable.
   */
  private boolean mIsExpandable = true;

  /**
   * The member name.
   */
  protected String mName = null;

  /**
   * The member parent.
   */
  private TreeNode<T> mParent = null;

  /**
   * The member is parent.
   */
  private boolean mIsParent = false;

  /**
   * The member is selectable.
   */
  private boolean mIsSelectable = true;

  /**
   * The member visible.
   */
  private boolean mVisible = true;

  /**
   * The member cumulative child count.
   */
  private int mCumulativeChildCount = 0;

  /**
   * The class ChildEvents.
   *
   * @param name the name
   */
  /*
   * private class ChildEvents implements TreeNodeEventListener {
   * 
   * @Override public void nodeChanged(ChangeEvent e) { countCumulativeChildren();
   * }
   * 
   * @Override public void nodeUpdated(ChangeEvent e) { // Do nothing } }
   */

  /**
   * Create node with a name, but no data. Typically used for creating folder
   * nodes.
   *
   * @param name the name
   */
  public TreeNode(String name) {
    this(name, null);
  }

  /**
   * Creates a node with a given name and associated data.
   *
   * @param name  the name
   * @param value the value
   */
  public TreeNode(String name, T value) {
    if (name == null) {
      name = "node";
    }
    
    mName = name;
    mValue = value;
  }

  /**
   * Creates a clone of the node including all of its children. If the value is
   * not immutable, this method should be overridden to ensure that value is
   * properly cloned as well.
   *
   * @param node the node
   */
  public TreeNode(TreeNode<T> node) {
    this(node.mName, node.mValue);

    clone(node);
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    mName = name;

    fireTreeNodeChanged(new ChangeEvent(this));
  }

  /**
   * Return the name of the node.
   *
   * @return the name
   */
  public String getName() {
    return mName;
  }

  /**
   * Return the unique id of the node.
   *
   * @return the uid
   */
  @Override
  public int getId() {
    return mId;
  }

  /**
   * Returns the data object associated with the node.
   *
   * @return the value
   */
  public T getValue() {
    return mValue;
  }

  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(T value) {
    if (value == null) {
      return;
    }

    mValue = value;

    // System.err.println("tree changed");

    fireTreeNodeUpdated();
  }

  /**
   * Adds a child.
   *
   * @param node the node
   */
  public void addChild(TreeNode<T> node) {
    node.setParent(this);

    // We should listen to the children to decide if we ourselves need
    // to take any action.
    node.addTreeNodeListener(this);

    mChildren.add(node);
    // childMap.put(node.getUid(), node);

    // automatically promote the node to being
    // a parent if a child is added.

    mIsParent = true;

    // setExpanded(true);

    countCumulativeChildren();
  }

  /**
   * Adds the child before.
   *
   * @param ref  the ref
   * @param node the node
   */
  public void addChildBefore(TreeNode<T> ref, TreeNode<T> node) {
    addChildBefore(indexOf(ref), node);
  }

  /**
   * Adds the child before.
   *
   * @param i    the i
   * @param node the node
   */
  public void addChildBefore(int i, TreeNode<T> node) {
    node.setParent(this);

    node.addTreeNodeListener(this);

    mChildren.add(i, node);
    // childMap.put(node.getUid(), node);

    // automatically promote the node to being
    // a parent if a child is added.

    mIsParent = true;

    // setExpanded(true);

    countCumulativeChildren();
  }

  /**
   * Adds the children.
   *
   * @param children the children
   */
  public void addChildren(List<TreeNode<T>> children) {
    for (TreeNode<T> node : children) {
      node.setParent(this);

      node.addTreeNodeListener(this);

      mChildren.add(node);
    }

    mIsParent = true;

    // setExpanded(true);

    countCumulativeChildren();
  }

  /**
   * Removes a child.
   *
   * @param node the node
   */
  public void removeChild(TreeNode<T> node) {
    // We must remove ourselves as as listener, otherwise if this node
    // happens to be added to the node as a child, a feedback loop will
    // occur when doing the cumulative child count.
    node.removeTreeNodeListener(this);

    mChildren.remove(node);

    if (mChildren.isEmpty()) {
      setIsParent(false);
    }

    countCumulativeChildren();
  }

  /**
   * Remove all children with the given name.
   *
   * @param name the name
   */
  public void removeChildren(String name) {
    Stack<Integer> indexes = new Stack<>();

    for (int i = 0; i < mChildren.size(); ++i) {
      if (mChildren.get(i).getName().equals(name)) {
        indexes.push(i);
      }
    }

    // Remove the children in reverse order
    // Collections.reverse(indexes);

    while (!indexes.isEmpty()) {
      int i = indexes.pop();

      mChildren.get(i).removeTreeNodeListener(this);

      mChildren.remove(i);
    }

    // If there are no more children, we can't be a parent anymore.
    if (mChildren.isEmpty()) {
      setIsParent(false);
    }

    countCumulativeChildren();
  }

  /**
   * Returns the number of children directly under this parent.
   *
   * @return the child count
   */
  public int getChildCount() {
    return mChildren.size();
  }

  /**
   * Returns the total number of children under this parent (i.e all children and
   * children of children etc).
   *
   * @return the cumulative child count
   */
  public int getCumulativeChildCount() {
    return mCumulativeChildCount;
  }

  /**
   * Sets the parent.
   *
   * @param node the new parent
   */
  public void setParent(TreeNode<T> node) {
    mParent = node;
  }

  /**
   * Gets the parent.
   *
   * @return the parent
   */
  public TreeNode<T> getParent() {
    return mParent;
  }

  /**
   * Can be used to forcibly indicate that a node is a parent, for example a
   * folder node that is currently empty.
   *
   * @param isParent the new checks if is parent
   */
  public void setIsParent(boolean isParent) {
    mIsParent = isParent;
  }

  /**
   * Returns whether the node is a parent.
   *
   * @return true, if is parent
   */
  public boolean isParent() {
    return mIsParent;
  }

  /**
   * Returns whether the node is a leaf.
   *
   * @return true, if is leaf
   */
  public boolean isLeaf() {
    return !isParent();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<TreeNode<T>> iterator() {
    return mChildren.iterator();
  }

  /**
   * Returns true if the node can be expanded.
   *
   * @return true, if is expandable
   */
  public boolean isExpandable() {
    return mIsExpandable;
  }

  /**
   * Sets whether the node should be displayed expandable or not.
   *
   * @param isExpandable the new checks if is expandable
   */
  public void setIsExpandable(boolean isExpandable) {
    mIsExpandable = isExpandable;
  }

  /**
   * Returns whether the node should be displayed in a collapsed state or not.
   *
   * @return true if the node should be displayed collapsed.
   */
  public boolean isExpanded() {
    return mIsExpanded;
  }

  /**
   * Sets the visible.
   *
   * @param visible the new visible
   */
  public void setVisible(boolean visible) {
    mVisible = visible;
  }

  /**
   * Checks if is visible.
   *
   * @return true, if is visible
   */
  public boolean isVisible() {
    return mVisible;
  }

  /**
   * Sets whether the node should be displayed collapsed or not.
   *
   * @param isExpanded the new expanded
   */
  public void setExpanded(boolean isExpanded) {
    setExpanded(isExpanded, false);
  }

  /**
   * Sets whether the node should be displayed collapsed or not and controls
   * whether a node changed event should be fire. This is used by batch methods to
   * do bulk expand operations on multiple nodes without fireing node events for
   * each change.
   *
   * @param isExpanded the is expanded
   * @param recursive  the recursive
   */
  public void setExpanded(boolean isExpanded, boolean recursive) {
    updateExpanded(isExpanded, recursive);

    fireTreeNodeChanged();
  }

  /**
   * Update expanded.
   *
   * @param isExpanded the is expanded
   */
  public void updateExpanded(boolean isExpanded) {
    updateExpanded(this, isExpanded, false);
  }

  /**
   * Update expanded.
   *
   * @param isExpanded the is expanded
   * @param recursive  the recursive
   */
  public void updateExpanded(boolean isExpanded, boolean recursive) {
    updateExpanded(this, isExpanded, recursive);
  }

  /**
   * Update expanded.
   *
   * @param <X>        the generic type
   * @param root       the root
   * @param isExpanded the is expanded
   * @param recursive  the recursive
   */
  public static <X> void updateExpanded(TreeNode<X> root, boolean isExpanded, boolean recursive) {
    Deque<TreeNode<X>> stack = new ArrayDeque<>();

    stack.push(root);

    while (!stack.isEmpty()) {
      TreeNode<X> node = stack.pop();

      if (node.mIsExpandable && !node.mChildren.isEmpty()) {
        node.mIsExpanded = isExpanded;
      }

      if (recursive) {
        for (TreeNode<X> child : node) {
          stack.push(child);
        }
      }
    }
  }

  /**
   * Collapase the children but not the node itself.
   *
   * @param isExpanded the new children are expanded
   */
  public void setChildrenAreExpanded(boolean isExpanded) {
    setChildrenAreExpanded(isExpanded, false);
  }

  /**
   * Sets the children are expanded.
   *
   * @param isExpanded the is expanded
   * @param recursive  the recursive
   */
  public void setChildrenAreExpanded(boolean isExpanded, boolean recursive) {
    updateChildrenAreExpanded(isExpanded, recursive);

    // Notify the listeners the tree has changed
    fireTreeNodeChanged();
  }

  /**
   * Update children are expanded.
   *
   * @param isExpanded the is expanded
   */
  public void updateChildrenAreExpanded(boolean isExpanded) {
    updateChildrenAreExpanded(isExpanded, false);
  }

  /**
   * Update children are expanded.
   *
   * @param isExpanded the is expanded
   * @param recursive  the recursive
   */
  public void updateChildrenAreExpanded(boolean isExpanded, boolean recursive) {
    for (TreeNode<T> child : mChildren) {
      updateExpanded(child, isExpanded, recursive);
    }
  }

  /**
   * Remove all of this nodes children.
   */
  public void clear() {
    mChildren.clear();

    mIsParent = false;
  }

  /**
   * Checks if is selectable.
   *
   * @return true, if is selectable
   */
  public boolean isSelectable() {
    return mIsSelectable;
  }

  /**
   * Sets the checks if is selectable.
   *
   * @param isSelectable the new checks if is selectable
   */
  public void setIsSelectable(boolean isSelectable) {
    mIsSelectable = isSelectable;

    fireTreeNodeUpdated();
  }

  /**
   * Allow a node to inherit the children of another node. This will result in
   * nodes sharing child nodes. Use close to create copies of children so that the
   * node can be updated independently.
   *
   * @param node the node
   */
  public void inherit(final TreeNode<T> node) {
    for (TreeNode<T> child : node) {
      addChild(child);
    }
  }

  /**
   * Allow a node to inherit a copy of the children of another node.
   *
   * @param node the node
   */
  public void clone(final TreeNode<T> node) {
    for (TreeNode<T> child : node) {
      addChild(new TreeNode<>(child));
    }
  }

  /**
   * Gets the child.
   *
   * @param name the name
   * @return the child
   */
  public TreeNode<T> getChild(String name) {
    for (TreeNode<T> child : this) {
      if (child.getName().equals(name)) {
        return child;
      }

    }

    return null;
  }

  /**
   * Search a tree using a path of ids. Since all nodes have a unique id, this
   * method guarantees the correct node will be returned along a valid path.
   *
   * @param path the path
   * @return the child
   */
  public TreeNode<T> getChild(TreePath path) {

    TreeNode<T> node = this;

    boolean found;

    for (int level : path) {

      found = false;

      for (TreeNode<T> child : node) {
        if (child.getId() == level) {
          node = child;
          found = true;
          break;
        }
      }

      // If the level was not found it means
      // the tree hierarchy does not match
      // what we are looking for so return
      // null to indicate failure
      if (!found) {
        return null;
      }
    }

    // The last update to the node reflects the
    // node we found at the end of the path
    return node;
  }

  /**
   * Gets the child by path.
   *
   * @param path the path
   * @return the child by path
   */
  public TreeNode<T> getChildByPath(String path) {
    return getChildByPath(new Path(path));
  }

  /**
   * Search a tree using a path of ids. Since node names are not guaranteed to be
   * unique, this method may not find the correct branch if you allow multiple
   * nodes with the same name.
   * 
   *
   * @param path the path
   * @return the child
   */
  public TreeNode<T> getChildByPath(Path path) {

    TreeNode<T> node = this;

    boolean found;

    for (String level : path) {

      found = false;

      for (TreeNode<T> child : node) {
        if (child.getName().equals(level)) {
          node = child;
          found = true;
          break;
        }
      }

      // If the level was not found it means
      // the tree hierarchy does not match
      // what we are looking for so return
      // null to indicate failure
      if (!found) {
        return null;
      }
    }

    // The last update to the node reflects the
    // node we found at the end of the path
    return node;
  }

  /**
   * Return the depth of the node.
   *
   * @return the depth
   */
  public int getDepth() {
    int depth = 0;

    TreeNode<T> node = this;

    while ((node = node.getParent()) != null) {
      ++depth;
    }

    return depth;
  }

  /**
   * Search the tree for a node containing the search text. Returns null if node
   * not found.
   *
   * @param search the search
   * @return the tree node
   */
  public TreeNode<T> findFirst(String search) {
    String ls = search.toLowerCase();

    Deque<TreeNode<T>> stack = new ArrayDeque<>();

    stack.push(this);

    TreeNode<T> node;

    while (!stack.isEmpty()) {
      node = stack.pop();

      if (node.getName().toLowerCase().contains(ls)) {
        return node;
      }

      for (TreeNode<T> child : node) {
        stack.push(child);
      }
    }

    return null;
  }

  /**
   * Index of.
   *
   * @param node the node
   * @return the int
   */
  public int indexOf(TreeNode<T> node) {
    for (int i = 0; i < mChildren.size(); ++i) {
      if (mChildren.get(i).equals(node)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Search the tree for a node whose name equals the search text. Returns null if
   * node not found.
   *
   * @param search the search
   * @return the tree node
   */
  public TreeNode<T> matchFirst(String search) {
    String ls = search.toLowerCase();

    Deque<TreeNode<T>> stack = new ArrayDeque<>();

    stack.push(this);

    TreeNode<T> node;

    while (!stack.isEmpty()) {
      node = stack.pop();
      
      System.err.println("sdfsdf"+node);
      
      String n = node.getName().toLowerCase();

      if (n.contains(ls) || ls.contains(n)) {
        return node;
      }

      for (TreeNode<T> child : node) {
        stack.push(child);
      }
    }

    return null;
  }

  /**
   * Return the list of nodes corresponding to the path.
   *
   * @param path the path
   * @return the chain
   */
  public TreeNodeChain<T> getChain(TreePath path) {

    TreeNodeChain<T> chain = new TreeNodeChain<>();

    TreeNode<T> node = this;

    boolean found;

    for (int level : path) {

      found = false;

      for (TreeNode<T> child : node) {
        if (child.getId() == level) {
          node = child;

          chain.add(child);

          found = true;
          break;
        }
      }

      // If the level was not found it means
      // the tree hierarchy does not match
      // what we are looking for so return
      // null to indicate failure
      if (!found) {
        return null;
      }
    }

    // The last update to the node reflects the
    // node we found at the end of the path
    return chain;
  }

  /**
   * Translates a tree path into a path. This can make it easier to see a path by
   * name rather than ids to make it more human readable. Since trees may contain
   * multiple nodes with the same name, paths using node names are not guaranteed
   * to be unique, therefore it is advisable to use TreePath to search a tree.
   * 
   * @param treePath a TreePath
   * @return a path representation of a TreePath
   */
  public Path convertToPath(TreePath treePath) {

    List<String> levels = new ArrayList<>();

    TreeNode<T> node = this;

    for (int level : treePath) {
      // node = node.childMap.get(level);

      TreeNode<T> nextNode = null;

      for (TreeNode<T> child : node) {
        if (child.getId() == level) {
          nextNode = child;
          break;
        }
      }

      // If the node is null, there has been
      // a mistake so return null
      if (nextNode == null) {
        return null;
      }

      levels.add(nextNode.getName());

      node = nextNode;
    }

    // The last update to the node reflects the
    // node we found at the end of the path
    return new Path(levels);
  }

  /**
   * Returns a path so that a path can be followed from this node's root to the
   * node.
   *
   * @return the path
   */
  public TreePath getPath() {
    Deque<TreeNode<T>> stack = new ArrayDeque<>();

    TreeNode<T> node = this;

    // We stop once we hit the first child of the root, but not the
    // root itself
    while (node != null && !(node instanceof TreeRootNode)) {
      stack.push(node);

      node = node.getParent();
    }

    TreePath path = new TreePath();

    while (!stack.isEmpty()) {
      path.add(stack.pop());
    }

    return path;
  }

  /**
   * Gets the child.
   *
   * @param index the index
   * @return the child
   */
  public TreeNode<T> getChild(int index) {
    return mChildren.get(index);
  }

  /**
   * Sort the children.
   */
  public void sortChildren() {
    Collections.sort(mChildren);
  }

  /**
   * Return the node value as a string. Returns Integer.MIN_VALUE if there is an
   * error.
   *
   * @return the int
   */
  public int getInt() {
    return TextUtils.scanInt(mValue.toString());
  }

  /**
   * As bool.
   *
   * @return true, if successful
   */
  public boolean getBool() {
    return mValue.toString().equalsIgnoreCase(TextUtils.TRUE);
  }

  /**
   * As double.
   *
   * @return the double
   * @throws ParseException the parse exception
   */
  public double getDouble() throws ParseException {
    return TextUtils.parseDouble(mValue.toString());
  }

  /**
   * Gets the as color.
   *
   * @return the as color
   */
  public Color getColor() {
    return ColorUtils.decodeHtmlColor(mValue.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.XmlRepresentation#toXml()
   */
  @Override
  public Element toXml(Document doc) {
    Element e = doc.createElement(getName());

    if (mValue != null) {
      e.setAttribute("value", mValue.toString());
    }

    return e;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.JsonRepresentation#toJson()
   */
  @Override
  public Json toJson() {
    Json json = new JsonObject(mName);

    if (mValue != null) {
      json.add("value", mValue);
    }

    Json childrenJson = json.createArray("chidren");

    for (TreeNode<T> property : this) {
      childrenJson.add(property.toJson());
    }

    return json;
  }

  /**
   * Count the total number of children below this node.
   */
  private void countCumulativeChildren() {
    updateCumulativeChildren();

    // forward the event
    fireTreeNodeChanged();
  }

  /**
   * Count the total number of children below this node.
   */
  private void updateCumulativeChildren() {
    mCumulativeChildCount = 0;

    Deque<TreeNode<T>> stack = new ArrayDeque<>();

    stack.push(this);

    while (!stack.isEmpty()) {
      TreeNode<T> node = stack.pop();

      for (TreeNode<T> child : node) {
        // The count can only change if the child has children
        // as well
        if (child.isParent()) {
          stack.push(child);
        } else {
          ++mCumulativeChildCount;
        }
      }
    }
  }

  /**
   * Convert a tree into a list.
   *
   * @return the children as list
   */
  public List<TreeNode<T>> getChildrenAsList() {
    List<TreeNode<T>> children = new ArrayList<>();

    Deque<TreeNode<T>> stack = new ArrayDeque<>();

    stack.push(this);

    while (!stack.isEmpty()) {
      TreeNode<T> node = stack.pop();

      for (TreeNode<T> child : node) {
        children.add(child);

        if (child.isParent()) {
          stack.push(child);
        }
      }
    }

    return children;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(TreeNode<T> n) {
    return mName.compareTo(n.mName);
  }

  /*
   * @Override public boolean equals(Object o) { if (!(o instanceof TreeNode)) {
   * return false; }
   * 
   * return mId == ((TreeNode<?>)o).getUid(); }
   */

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.tree.TreeNodeEventProducer#addTreeNodeListener(org.abh.lib.
   * tree. TreeNodeEventListener)
   */
  @Override
  public void addTreeNodeListener(ITreeNodeEventListener l) {
    mListeners.addTreeNodeListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.tree.TreeNodeEventProducer#removeTreeNodeListener(org.abh.lib.
   * tree.TreeNodeEventListener)
   */
  @Override
  public void removeTreeNodeListener(ITreeNodeEventListener l) {
    mListeners.removeTreeNodeListener(l);
  }

  /**
   * Fire tree node changed.
   */
  public void fireTreeNodeChanged() {
    fireTreeNodeChanged(new ChangeEvent(this));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.tree.TreeNodeEventProducer#fireTreeNodeChanged(org.abh.lib.
   * event. ChangeEvent)
   */
  @Override
  public void fireTreeNodeChanged(ChangeEvent e) {
    mListeners.fireTreeNodeChanged(e);
  }

  /**
   * Fire tree node updated.
   */
  public void fireTreeNodeUpdated() {
    fireTreeNodeUpdated(new ChangeEvent(this));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.tree.TreeNodeEventProducer#fireTreeNodeUpdated(org.abh.lib.
   * event. ChangeEvent)
   */
  @Override
  public void fireTreeNodeUpdated(ChangeEvent e) {
    mListeners.fireTreeNodeUpdated(e);
  }

  /**
   * Remove the node from its registered parent.
   */
  public void removeSelf() {
    getParent().removeChild(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.tree.TreeNodeEventListener#nodeChanged(org.abh.common.event.
   * ChangeEvent)
   */
  @Override
  public void nodeChanged(ChangeEvent e) {
    countCumulativeChildren();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.tree.TreeNodeEventListener#nodeUpdated(org.abh.common.event.
   * ChangeEvent)
   */
  @Override
  public void nodeUpdated(ChangeEvent e) {
    // Forward events
    fireTreeNodeUpdated();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.Reversible#reversed()
   */
  @Override
  public Iterable<TreeNode<T>> reversed() {
    return new ReverseIterator<>(mChildren);
  }
}
