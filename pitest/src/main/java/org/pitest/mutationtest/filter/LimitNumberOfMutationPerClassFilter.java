/*
 * Copyright 2011 Henry Coles
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
package org.pitest.mutationtest.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pitest.mutationtest.engine.MutationDetails;

public class LimitNumberOfMutationPerClassFilter implements MutationFilter {

  private final double mutationsPerLineProb;

  public LimitNumberOfMutationPerClassFilter(final int prob) {
    this.mutationsPerLineProb = prob * 1.0 / 10000.0;
  }


  public Collection<MutationDetails> filter(
      final Collection<MutationDetails> mutations) {
	  Map<Integer, ArrayList<MutationDetails>> lines = new HashMap<Integer, ArrayList<MutationDetails>>();	  
	  for(MutationDetails m : mutations) {
		  int ln = m.getLineNumber();
		  if (lines.containsKey(ln)) {
			  lines.get(ln).add(m);
		  } else {
			  ArrayList<MutationDetails> md = new ArrayList<MutationDetails>();
			  md.add(m);
			  lines.put(ln, md);
		  }
	  }
	  Collection<MutationDetails> mymutations = new ArrayList<MutationDetails>();
	  for(ArrayList<MutationDetails> m : lines.values()) {
		  mymutations.addAll(choose(m));
	  }
      return mymutations;
  }

  private Collection<MutationDetails> choose(
      final ArrayList<MutationDetails> mutations) {
	  int max = Math.min(mutations.size(), 1);
	  Collections.shuffle(mutations);
	  Collection<MutationDetails> mymutations = new ArrayList<MutationDetails>();
	  for (int i=0; i<max; i++) {
      double d = Math.random();
      if (d <= mutationsPerLineProb) {
        mymutations.add(mutations.get(i));
      }
	  }
	  return mymutations;
  }
}
