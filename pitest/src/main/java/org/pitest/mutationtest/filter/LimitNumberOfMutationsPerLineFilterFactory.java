package org.pitest.mutationtest.filter;

import org.pitest.classpath.CodeSource;

public class LimitNumberOfMutationsPerLineFilterFactory implements MutationFilterFactory {

  public MutationFilter createFilter(CodeSource source, int maxMutationsPerLine) {
    if ( maxMutationsPerLine > 0 ) {
    return new LimitNumberOfMutationPerLineFilter(maxMutationsPerLine);
    } else {
      return UnfilteredMutationFilter.INSTANCE;
    }
  }

  public String description() {
    return "Default limit mutations plugin";
  }
  
}
