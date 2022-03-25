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

/**
 * Represents a stream containing numbers.
 *
 * @param <T> the generic type
 */
public abstract class NumberStream<T extends Number> extends ContainerStream<T> {

  /**
   * The Class MeanFunction.
   *
   * @param <T> the generic type
   */
  private static class MeanFunction<T extends Number> implements ReduceFunction<T, Double> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Stream<T> stream) {
      double sum = 0;
      int c = 0;

      while (stream.hasNext()) {
        sum += stream.next().doubleValue();

        ++c;
      }

      return sum / c;
    }
  }

  /**
   * Instantiates a new number stream.
   *
   * @param stream the stream
   */
  public NumberStream(Stream<T> stream) {
    super(stream);
  }

  /**
   * Returns the mean of a collection of numbers in a stream.
   *
   * @return the double
   */
  public double mean() {
    return reduce(new MeanFunction<T>());
  }
}
