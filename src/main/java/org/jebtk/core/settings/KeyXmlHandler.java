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
package org.jebtk.core.settings;

import java.util.ArrayDeque;
import java.util.Deque;

import org.jebtk.core.text.TextUtils;
import org.jebtk.core.tree.TreeNode;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The class KeyXmlHandler.
 */
public class KeyXmlHandler extends DefaultHandler {

  /**
   * The root node.
   */
  public TreeNode<String> mRoot;

  /**
   * The property stack.
   */
  private final Deque<TreeNode<String>> mStack = new ArrayDeque<TreeNode<String>>();

  /**
   * Whether to ensure that each node only contains children with unique names.
   */
  private boolean mUnique = true;

  /**
   * Instantiates a new key xml handler.
   *
   * @param service the service
   */
  public KeyXmlHandler(KeyService service) {
    mRoot = service;

    mStack.push(service);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    switch (qName) {
      case "keys":
        mUnique = attributes.getValue("unique") == null && attributes.getValue("unique").equals(TextUtils.TRUE);
        break;
      case "key":
        TreeNode<String> key = mStack.peek().getChild(attributes.getValue("name"));
        if (mUnique && key != null) {
          key.setValue(attributes.getValue("value"));
        } else {
          // Only create a new node if the unique mode is false or
          // we cannot find an existing node
          key = new KeyNode(attributes.getValue("name"), attributes.getValue("value"));
          
          mStack.peek().addChild(key);
        } mStack.push(key);
        break;
      case "clone":
        {
          String path = attributes.getValue("path");
          mStack.peek().clone(mRoot.getChildByPath(path));
          break;
        }
      case "inherit":
        {
          String path = attributes.getValue("path");
          mStack.peek().inherit(mRoot.getChildByPath(path));
          break;
        }
      case "update":
        {
          String path = attributes.getValue("path");
          mStack.peek().getChildByPath(path).setValue(attributes.getValue("value"));
          break;
        }
      default:
        break;
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {

    if (qName.equals("key")) {
      mStack.pop();
    }
  }
}
