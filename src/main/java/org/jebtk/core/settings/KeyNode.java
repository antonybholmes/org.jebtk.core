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

import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonObject;
import org.jebtk.core.tree.TreeNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class KeyNode.
 *
 * @author Antony Holmes
 */
public class KeyNode extends TreeNode<String> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The description.
   */
  private String mDescription = "";

  /**
   * Instantiates a new key node.
   *
   * @param name the name
   */
  public KeyNode(String name) {
    super(name);
  }

  /**
   * Instantiates a new key node.
   *
   * @param name  the name
   * @param value the value
   */
  public KeyNode(String name, String value) {
    super(name, value);
  }

  /**
   * Create a clone of a node by inheriting its properties.
   *
   * @param node the node
   */
  public KeyNode(KeyNode node) {
    super(node);
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    if (description == null) {
      return;
    }

    mDescription = description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.tree.TreeNode#toXml()
   */
  @Override
  public Element toXml(Document doc) {
    Element propertyElement = doc.createElement("key");

    propertyElement.setAttribute("name", mName);

    if (mValue != null && mValue.length() > 0) {
      propertyElement.setAttribute("value", mValue);
    }

    for (TreeNode<String> property : this) {
      propertyElement.appendChild(property.toXml(doc));
    }

    return propertyElement;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.tree.TreeNode#toJson()
   */
  @Override
  public Json toJson() {
    Json json = new JsonObject(mName);

    if (mValue != null && mValue.length() > 0) {
      json.add("value", mValue);
    }

    Json childrenJson = json.createArray("chidren");

    for (TreeNode<String> property : this) {
      childrenJson.add(property.toJson());
    }

    return json;
  }

  /**
   * Standardize.
   *
   * @param name the name
   * @return the string
   */
  public static String standardize(String name) {
    return name.toLowerCase(); // .replaceAll("\\s+", "_");
  }
}
