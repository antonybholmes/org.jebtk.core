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

import org.jebtk.core.Function;

/**
 * The Class IntStream.
 */
public class IntStream extends NumberStream<Integer> {

  /**
   * The Class MinFunction.
   */
  private static class MinFunction implements ReduceFunction<Integer, Integer> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(Stream<Integer> stream) {
      int min = Integer.MAX_VALUE;

      while (stream.hasNext()) {
        int v = stream.next().intValue();

        if (v < min) {
          min = v;
        }
      }

      return min;
    }
  }

  /**
   * The Class MaxFunction.
   */
  private static class MaxFunction implements ReduceFunction<Integer, Integer> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(Stream<Integer> stream) {
      int max = stream.next();

      while (stream.hasNext()) {
        int v = stream.next();

        if (v > max) {
          max = v;
        }
      }

      return max;
    }
  }

  /**
   * The Class SumFunction.
   */
  private static class SumFunction implements ReduceFunction<Integer, Integer> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(Stream<Integer> stream) {
      int sum = 0;

      while (stream.hasNext()) {
        sum += stream.next().intValue();
      }

      return sum;
    }
  }

  /**
   * The Class MultiplyFunction.
   */
  private static class MultiplyFunction implements Function<Integer, Integer> {

    /** The m places. */
    private int mV;

    /**
     * Instantiates a new round function.
     *
     * @param v the v
     */
    public MultiplyFunction(int v) {
      mV = v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(Integer v) {
      return v * mV;
    }
  }

  /**
   * The Class MultiplyDoubleFunction.
   */
  private static class MultiplyDoubleFunction implements Function<Integer, Double> {

    /** The m V. */
    private double mV;

    /**
     * Instantiates a new round function.
     *
     * @param v the v
     */
    public MultiplyDoubleFunction(double v) {
      mV = v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Integer v) {
      return v * mV;
    }
  }

  /**
   * The Class AddFunction.
   */
  private static class AddFunction implements Function<Integer, Integer> {

    /** The m places. */
    private int mV;

    /**
     * Instantiates a new round function.
     *
     * @param v the v
     */
    public AddFunction(int v) {
      mV = v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(Integer v) {
      return v + mV;
    }
  }

  /**
   * Instantiates a new int stream.
   *
   * @param stream the stream
   */
  public IntStream(Stream<Integer> stream) {
    super(stream);
  }

  /**
   * Returns the sum of the stream.
   *
   * @return the int
   */
  public int sum() {
    return reduce(new SumFunction());
  }

  /**
   * Multiply the values in a number stream by a constant.
   *
   * @param v the v
   * @return the int stream
   */
  public IntStream multiply(int v) {
    return new IntStream(map(new MultiplyFunction(v)));
  }

  /**
   * Multiple the values in a number stream by a constant.
   *
   * @param v the v
   * @return the double stream
   */
  public DoubleStream multiply(double v) {
    return new DoubleStream(map(new MultiplyDoubleFunction(v)));
  }

  /**
   * Adds the.
   *
   * @param v the v
   * @return the int stream
   */
  public IntStream add(int v) {
    return new IntStream(map(new AddFunction(v)));
  }

  /**
   * Returns the min value in the stream using {@compareTo()} for ordering.
   * 
   * @return The min element.
   */
  public int min() {
    return reduce(new MinFunction());
  }

  /**
   * Returns the max value in the stream using {@compareTo()} for ordering.
   * 
   * @return The max element.
   */
  public int max() {
    return reduce(new MaxFunction());
  }
}
