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
package org.jebtk.test;

import java.text.ParseException;

import org.jebtk.core.io.PathUtils;
import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonArray;
import org.jebtk.core.json.JsonBuilder;
import org.jebtk.core.json.JsonInteger;
import org.jebtk.core.json.JsonObject;
import org.jebtk.core.json.JsonParser;
import org.jebtk.core.json.JsonString;
import org.junit.Test;

public class JsonTest {

  @Test
  public void testJsonParser() {

    Json jsonArray = new JsonArray();

    jsonArray.add(new JsonObject().add("version", new JsonInteger(30)));

    // jsonArray.add("blob");

    System.err.println(jsonArray.toString());

    try {
      System.err.println(new JsonParser()
          .parse("[{\"name\":\"edbw\",\"version\":2.0,\"copyright\":\"Copyright (C) 2013-2015 Antony Holmes\"}]"));
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
  }

  @Test
  public void testJsonEscapeParser() {

    try {
      Json json = new JsonParser().parse(
          "[{\"test\":\"c:\\\\test\",\"version	\":2.0,\"copy\\\"right\":\"Copyright (C) 2013-2015 Antony Holmes\"}]");

      System.err.println("escape parser " + json.get(0).get("test"));

      System.err.println(json);
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
  }

  @Test
  public void testJsonEscape() {

    System.err.println("escape " + JsonString.escape("x:r/	"));
  }

  @Test
  public void testJsonUnicodeParser() {

    try {
      System.err.println(new JsonParser()
          .parse("[{\"name\":\"\\u0063\",\"version\":2.0,\"copyright\":\"Copyright (C) 2013-2015 Antony Holmes\"}]"));
    } catch (ParseException e1) {
      e1.printStackTrace();
    }
  }

  @Test
  public void testJsonFile() {

    JsonObject o = new JsonObject();
    o.add("aha:", PathUtils.getPath("R:\\cake\\cheese.txt"));

    System.err.println("json file " + o);
  }

  @Test
  public void testBuilder() {
    System.err.println("Json test builder "
        + new JsonBuilder().startArray().add("cake").add(1).add(false).startObject().add("p", "test/sdf\\sdf")
            .add("q", 4).add("r", true).startArray("hmm").add("2").add("dfdf").endArray().endObject().endArray());
  }
}
