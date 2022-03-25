/**
toString * Copyright 2016 Antony Holmes
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.stream.IntStream;
import org.jebtk.core.stream.Stream;
import org.junit.Test;

public class StreamTest {

  @Test
  public void streamToListTest() {

    List<Integer> values = new ArrayList<Integer>();
    values.add(1);
    values.add(2);
    values.add(3);
    values.add(4);

    Stream<Integer> stream = Stream.of(values);

    assertTrue("Stream list: " + stream.toList().toString(), stream.toList().toString().equals("[1, 2, 3, 4]"));
  }

  @Test
  public void streamMeanTest() {

    List<Integer> values = new ArrayList<Integer>();
    values.add(1);
    values.add(2);
    values.add(3);
    values.add(4);
    values.add(4);

    double s = Stream.of(values).asDouble().mean();

    assertEquals("Stream mean: " + s, 2.8, s, 0.001);
  }

  @Test
  public void emptyFilterTest() {

    List<String> values = new ArrayList<String>();
    values.add("1");
    values.add("2");
    values.add("");
    values.add("4");

    Stream<Integer> stream = Stream.of(values).asString().emptyFilter().asInt();

    List<Integer> s = stream.toList();

    assertTrue("Stream list: " + s.toString(), s.toString().equals("[1, 2, 4]"));
  }

  @Test
  public void minTest() {

    List<String> values = new ArrayList<String>();
    values.add("8");
    values.add("2");
    values.add("1");
    values.add("4");

    IntStream stream = Stream.of(values).asInt().asInt();

    int s = stream.min();

    assertEquals("Stream min: " + s, 1, s);
  }

  @Test
  public void maxTest() {

    List<String> values = new ArrayList<String>();
    values.add("8");
    values.add("2");
    values.add("1");
    values.add("4");

    IntStream stream = Stream.of(values).asInt();

    int s = stream.max();

    assertEquals("Stream max: " + s, 8, s);
  }

  @Test
  public void catTest() {

    List<String> l1 = new ArrayList<String>();
    l1.add("1");
    l1.add("2");
    l1.add("3");
    l1.add("4");

    List<String> l2 = new ArrayList<String>();
    l2.add("5");
    l2.add("6");
    l2.add("7");
    l2.add("8");

    Stream<Integer> stream = Stream.of(l1).cat(l2).asInt();

    List<Integer> s = stream.toList();

    assertTrue("Stream list: " + s.toString(), s.toString().equals("[1, 2, 3, 4, 5, 6, 7, 8]"));
  }
}
