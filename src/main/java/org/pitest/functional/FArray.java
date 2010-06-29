/*
 * Copyright 2010 Henry Coles
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package org.pitest.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author henry
 * 
 */
public abstract class FArray {

  public static <T> void filter(final T[] xs, final F<T, Boolean> predicate,
      final Collection<T> dest) {
    for (final T x : xs) {
      if (predicate.apply(x)) {
        dest.add(x);
      }
    }
  }

  public static <T> List<T> filter(final T[] xs, final F<T, Boolean> predicate) {
    final List<T> dest = new ArrayList<T>();
    filter(xs, predicate, dest);
    return dest;
  }

}