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
import java.util.Collection;
import java.util.List;

import org.jebtk.core.Function;

/**
 * The Class MapStream allows values in a stream to be mapped to something else
 * on a one to one basis.
 *
 * @param <T> the generic type
 * @param <V> the value type
 */
public class MapStream<T, V> extends Stream<V> {

  /** The m stream. */
  private Stream<T> mStream;

  /** The m F. */
  private Function<T, V> mF;

  /**
   * Instantiates a new map stream.
   *
   * @param stream the stream
   * @param f      the f
   */
  public MapStream(Stream<T> stream, Function<T, V> f) {
    mStream = stream;
    mF = f;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return mStream.hasNext();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public V next() {
    return mF.apply(mStream.next());
  }

  //
  // Static methods
  //

  /**
   * Apply a function to a collection and return a list of the function outputs of
   * the same size as the input collection. This is a functional way of running a
   * for loop over a list.
   *
   * @param <VV>   the generic type
   * @param <TT>   the generic type
   * @param values the values
   * @param f      the f
   * @return the list
   */
  public static <VV, TT> List<TT> lapply(Collection<VV> values, Function<VV, TT> f) {
    List<TT> ret = new ArrayList<TT>(values.size());

    for (VV v : values) {
      ret.add(f.apply(v));
    }

    return ret;
  }
}
