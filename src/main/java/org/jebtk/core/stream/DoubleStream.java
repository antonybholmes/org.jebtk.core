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
import org.jebtk.core.Mathematics;

/**
 * The Class DoubleStream is a wrapper for a generic Stream<Double> providing
 * extra numerical functions.
 */
public class DoubleStream extends NumberStream<Double> {

  /**
   * The Class MinFunction.
   */
  private static class MinFunction implements ReduceFunction<Double, Double> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Stream<Double> stream) {
      double min = Double.MAX_VALUE;

      while (stream.hasNext()) {
        double v = stream.next().doubleValue();

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
  private static class MaxFunction implements ReduceFunction<Double, Double> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Stream<Double> stream) {
      double max = stream.next();

      while (stream.hasNext()) {
        double v = stream.next();

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
  private static class SumFunction implements DoubleReduceFunction<Double> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Stream<Double> stream) {
      double sum = 0;

      while (stream.hasNext()) {
        sum += stream.next();
      }

      return sum;
    }
  }

  /**
   * The Class RoundFunction.
   */
  private static class RoundFunction implements Function<Double, Double> {

    /** The m places. */
    private int mPlaces;

    /**
     * Instantiates a new round function.
     *
     * @param places the places
     */
    public RoundFunction(int places) {
      mPlaces = places;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Double v) {
      return Mathematics.round(v, mPlaces);
    }
  }

  /**
   * The Class MultiplyFunction.
   */
  private static class MultiplyFunction implements Function<Double, Double> {

    /** The m places. */
    private double mV;

    /**
     * Instantiates a new round function.
     *
     * @param v the v
     */
    public MultiplyFunction(double v) {
      mV = v;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(Double v) {
      return v * mV;
    }
  }

  /**
   * Instantiates a new double stream.
   *
   * @param stream the stream
   */
  public DoubleStream(Stream<Double> stream) {
    super(stream);
  }

  /**
   * Sum.
   *
   * @return the double
   */
  public double sum() {
    return reduce(new SumFunction());
  }

  /**
   * Min.
   *
   * @param places the places
   * @return the double
   */
  // public double min() {
  // return reduce(new MinFunction<Double>());
  // }

  /**
   * Rounds the numbers in the stream to a given number of decimal places.
   *
   * @param places the places
   * @return the stream
   */
  public DoubleStream round(int places) {
    return new DoubleStream(map(new RoundFunction(places)));
  }

  /**
   * Multiply the values in the stream by a constant.
   *
   * @param v the v
   * @return the double stream
   */
  public DoubleStream multiply(double v) {
    return new DoubleStream(map(new MultiplyFunction(v)));
  }

  /**
   * Min.
   *
   * @return the double
   */
  public double min() {
    return reduce(new MinFunction());
  }

  /**
   * Max.
   *
   * @return the double
   */
  public double max() {
    return reduce(new MaxFunction());
  }
}
