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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jebtk.core.Function;
import org.jebtk.core.collections.UniqueArrayList;

/**
 * A stream represents a series of functional operations on a collection to
 * perform data processing.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public abstract class Stream<T> implements StreamIterator<T>, Iterable<T> {
  /**
   * The Class CountFunction.
   *
   * @param <T> the generic type
   */
  private static class CountFunction<T> implements IntReduceFunction<T> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(Stream<T> stream) {
      int c = 0;

      while (stream.hasNext()) {
        ++c;
      }

      return c;
    }
  }

  /**
   * The Class IntMapFunction.
   *
   * @param <T> the generic type
   */
  private static class IntMapFunction<T> implements Function<T, Integer> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Integer apply(T item) {
      if (item instanceof Integer) {
        return (Integer) item;
      } else if (item instanceof Long) {
        return ((Long) item).intValue();
      } else if (item instanceof Double) {
        return ((Double) item).intValue();
      } else {
        return (int) Integer.parseInt(item.toString());
      }
    }
  }

  /**
   * The Class DoubleMapFunction.
   *
   * @param <T> the generic type
   */
  private static class DoubleMapFunction<T> implements Function<T, Double> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Double apply(T item) {
      if (item instanceof Double) {
        return (Double) item;
      } else if (item instanceof Long) {
        return ((Long) item).doubleValue();
      } else if (item instanceof Integer) {
        return ((Integer) item).doubleValue();
      } else {
        return Double.parseDouble(item.toString());
      }
    }
  }

  /**
   * The Class ToListFunction.
   *
   * @param <T> the generic type
   */
  private static class ToListFunction<T> extends ListReduceFunction<T, T> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public void apply(T item, List<T> ret) {
      ret.add(item);
    }
  }

  /**
   * The Class ToSetFunction.
   *
   * @param <T> the generic type
   */
  private static class ToSetFunction<T> implements ReduceFunction<T, Set<T>> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public Set<T> apply(Stream<T> stream) {
      Set<T> ret = new HashSet<T>();

      while (stream.hasNext()) {
        ret.add(stream.next());
      }

      return ret;
    }
  }

  /**
   * Converts an object to a string representation.
   *
   * @param <T> the generic type
   */
  private static class AsStringFunction<T> implements StringMapFunction<T> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public String apply(T item) {
      if (item instanceof String) {
        return (String) item;
      } else {
        return item.toString();
      }
    }
  }

  private static class CastFunction<T, V> implements Function<T, V> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public V apply(T item) {
      return (V) item;
    }
  }

  /**
   * Filter the stream to remove values. Streams cannot contain nulls.
   *
   * @param filter the filter
   * @return the stream
   */
  public Stream<T> filter(Filter<T> filter) {
    return new FilterStream<T>(this, filter);
  }

  /**
   * Replicate.
   *
   * @param n the n
   * @return the stream
   */
  public Stream<T> replicate(int n) {
    return new ReplicateStream<T>(this, n);
  }

  /**
   * Jump every n elements in a stream, thus can be used to skip elements when
   * iterating.
   *
   * @param n the n
   * @return the stream
   */
  public Stream<T> jump(int n) {
    return new JumpStream<T>(this, n);
  }

  public Stream<T> skipNulls() {
    return new SkipNullStream<T>(this);
  }

  /**
   * Map the values in a stream to a different type.
   *
   * @param <V> the value type
   * @param f   the f
   * @return the stream
   */
  public <V> Stream<V> map(Function<T, V> f) {
    return new MapStream<T, V>(this, f);
  }

  /**
   * Convenience methods to encapsulate a function that maps objects to integers
   * as an IntStream. This is equivalent to calling {@code map(f).mapToInt()}.
   *
   * @param f the f
   * @return the int stream
   */
  public IntStream asInt(Function<T, Integer> f) {
    return map(f).asInt();
  }

  /**
   * Convert a stream to an integer stream.
   *
   * @return the int stream
   */
  public IntStream asInt() {
    return new IntStream(map(new IntMapFunction<T>()));
  }

  /**
   * Map stream to a double stream.
   *
   * @return the double stream
   */
  public DoubleStream asDouble() {
    return new DoubleStream(map(new DoubleMapFunction<T>()));
  }

  /**
   * Equivalent to calling {@code map(...).mapToString()}. This is a shorthand
   * method for returning a proper @{code StringStream} if the mapping function
   * maps to a String.
   *
   * @param f the f
   * @return the string stream
   */
  public StringStream asString(Function<T, String> f) {
    return map(f).asString();
  }

  /**
   * Convert a stream to a string stream.
   *
   * @return the string stream
   */
  public StringStream asString() {
    return new StringStream(map(new AsStringFunction<T>()));
  }

  /**
   * Cast items from one type to another. This has minimal type checking and
   * expects all items to be of the same type. It will throw errors if
   * imcompatible types are mixed.
   * 
   * @return
   */
  public <V> Stream<V> cast() {
    return map(new CastFunction<T, V>());
  }

  /**
   * Maps the items in a stream to a single value, for example the sum of a number
   * stream. Reduce consumes all data in the stream pipeline so another stream
   * will need to be created to continue streaming data.
   *
   * @param <V> the value type
   * @param f   a Reduce function.
   * @return the v
   */
  public <V> V reduce(ReduceFunction<T, V> f) {
    return f.apply(this);
  }

  /**
   * Skip the first n elements of a stream.
   *
   * @param n the n
   * @return the stream
   */
  public Stream<T> skip(int n) {
    return new SkipStream<T>(this, n);
  }

  /**
   * Concatenate a collection to this stream as a new stream. Equivalent to
   * {@code cat(stream(values)}.
   *
   * @param values the values
   * @return the stream
   */
  public Stream<T> cat(Iterable<T> values) {
    return cat(of(values));
  }

  /**
   * Concatenate a stream to the this stream. Do not modify the streams being
   * concatenated separately once the new stream has been created as this may
   * cause undesired behavior.
   *
   * @param stream The stream to concatenate at the end of this stream.
   * @return the stream
   */
  public Stream<T> cat(Stream<T> stream) {
    return new CatStream<T>(this, stream);
  }

  /**
   * Returns the union of two streams. This is a terminal operation for both
   * streams.
   *
   * @param stream the stream
   * @return the list
   */
  public List<T> union(Stream<T> stream) {
    List<T> ret = new UniqueArrayList<T>();

    while (hasNext()) {
      ret.add(next());
    }

    while (stream.hasNext()) {
      ret.add(stream.next());
    }

    return ret;
  }

  /**
   * Returns the union of two streams. This is a terminal operation for both
   * streams.
   *
   * @param stream the stream
   * @return the list
   */
  public List<T> intersect(Stream<T> stream) {

    Set<T> checkSet = stream.toSet();

    List<T> ret = new UniqueArrayList<T>(checkSet.size());

    while (hasNext()) {
      T item = next();

      if (checkSet.contains(item)) {
        ret.add(item);
      }
    }

    return ret;
  }

  /**
   * Join the values in a stream into a string using a given delimiter. This is a
   * convenience method for {@code mapToString().join()}.
   * 
   * @param delimiter The string delimiter.
   * @return The stream values in a single string separated by {@code delimiter}.
   */
  public String join(String delimiter) {
    return asString().join(delimiter);
  }

  /**
   * Counts the number of items in the stream. This is a reduce function that will
   * render the stream consumed and unusable.
   *
   * @return the int
   */
  public int count() {
    return reduce(new CountFunction<T>());
  }

  /**
   * Converts the stream to a list. The returned list will be a copy of the stream
   * values, thus it is modifiable, but will will not affect the internal
   * structure of the stream. This method uses the reduce function and will
   * renderer the stream consumed and unusable.
   *
   * @return the list
   */
  public List<T> toList() {
    return reduce(new ToListFunction<T>());
  }

  /**
   * To set.
   *
   * @return the sets the
   */
  public Set<T> toSet() {
    return reduce(new ToSetFunction<T>());
  }

  /**
   * Returns the stream values reversed in a list. This is a terminal operation.
   * 
   * @return The stream values reversed.
   */
  public List<T> reverse() {
    List<T> list = toList();

    Collections.reverse(list);

    return list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#remove()
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException("Items cannot be removed from streams.");
  }

  /**
   * Should return the size of the stream if known, otherwise -1 to indicate
   * unknown size.
   *
   * @return the int
   */
  public int size() {
    return -1;
  }

  /**
   * Returns an iterator of the stream. This is a terminal operation.
   *
   * @return the iterator
   */
  @Override
  public Iterator<T> iterator() {
    return this; // toList().iterator();
  }

  //
  // Static methods
  //

  /**
   * Stream.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the stream
   */
  public static <T> Stream<T> of(Iterable<T> iter) {
    return new IterableStream<T>(iter);
  }

  public static <T> Stream<T> of(Collection<T> iter) {
    return new CollectionStream<T>(iter);
  }

  /**
   * Creates a stream from a single item.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the stream
   */
  public static <T> Stream<T> of(T item) {
    return new SingletonStream<T>(item);
  }

  public static Stream<Integer> of(int... values) {
    return new IntArrayStream(values);
  }

  public static Stream<Double> of(double... values) {
    return new DoubleArrayStream(values);
  }

  /**
   * Int stream.
   *
   * @param values the values
   * @return the int stream
   */
  public static IntStream asInt(Iterable<Integer> values) {
    return of(values).asInt();
  }

  public static IntStream asInt(Collection<Integer> values) {
    return of(values).asInt();
  }

  public static IntStream asInt(int... values) {
    return of(values).asInt();
  }

  /**
   * Double stream.
   *
   * @param values the values
   * @return the double stream
   */
  public static DoubleStream asDouble(Iterable<Double> values) {
    return of(values).asDouble();
  }

  public static DoubleStream asDouble(Collection<Double> values) {
    return of(values).asDouble();
  }

  public static DoubleStream asDouble(double... values) {
    return of(values).asDouble();
  }

  /**
   * String stream.
   *
   * @param values the values
   * @return the string stream
   */
  public static StringStream asString(Iterable<String> values) {
    return of(values).asString();
  }

  public static StringStream asString(Collection<String> values) {
    return of(values).asString();
  }

  public static StringStream asString(String... values) {
    return of(values).asString();
  }

  public static <TT, VV> Stream<VV> cast(Iterable<TT> values) {
    return of(values).cast();
  }
}
