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
package org.jebtk.core.stream;

import java.util.ArrayList;
import java.util.List;

/**
 * A reduce function takes a stream and returns an object representing a
 * property of the stream, such as the sum of the numbers in it.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 * @param <V> the value type
 */
public abstract class ListReduceFunction<T, V> implements ReduceFunction<T, List<V>> {

  @Override
  public List<V> apply(Stream<T> stream) {
    List<V> ret = new ArrayList<V>(1000);

    apply(stream, ret);

    return ret;
  }

  public void apply(Stream<T> stream, List<V> values) {
    while (stream.hasNext()) {
      apply(stream.next(), values);
    }
  }

  public abstract void apply(T item, List<V> values);
}
