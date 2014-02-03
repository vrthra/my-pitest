package org.pitest.mutationtest.filter;

import org.pitest.classpath.CodeSource;

public class LimitNumberOfMutationsFilterFactory implements MutationFilterFactory {

  public MutationFilter createFilter(CodeSource source, int maxMutationsPerClass) {
    //if ( maxMutationsPerClass > 0 ) {
    return new LimitNumberOfMutationsFilter(maxMutationsPerClass);
    //} else {
    //  return UnfilteredMutationFilter.INSTANCE;
    //}
  }

  public String description() {
    return "Default limit mutations plugin";
  }
  
}
