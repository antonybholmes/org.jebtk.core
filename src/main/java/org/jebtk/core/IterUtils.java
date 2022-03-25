package org.jebtk.core;

public class IterUtils {
  private IterUtils() {
    // Do nothing
  }

  public static void forEach(int m, ForEach l) {
    for (int i = 0; i < m; ++i) {
      l.loop(i);
    }
  }

  /**
   * Perform a loop over two variables varying i and then j.
   * 
   * @param m
   * @param n
   * @param l
   */
  public static void forEach(int m, int n, ForEach2D l) {
    for (int i = 0; i < m; ++i) {
      for (int j = 0; j < n; ++j) {
        l.loop(i, j);
      }
    }
  }
}
