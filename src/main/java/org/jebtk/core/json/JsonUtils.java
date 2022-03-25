package org.jebtk.core.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for dealing with JSON strings.
 * 
 * @author antony
 *
 */
public class JsonUtils {
  private JsonUtils() {
    // Do nothing
  }

  /**
   * Add JSON array characters around a JSON string. Useful to convert a list of
   * JSON values into a JSON array.
   * 
   * @param json
   * @return
   */
  public static String asArray(String json) {
    return "[" + json + "]";
  }

  /**
   * Convert a json array to a list of strings.
   * 
   * @param json
   * @return
   */
  public static List<String> toStringList(Json json) {
    List<String> ret = new ArrayList<String>();

    for (int i = 0; i < json.size(); ++i) {
      ret.add(json.getString(i));
    }

    return ret;
  }
}
