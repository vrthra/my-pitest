/*
 * http://www.apache.org/licenses/LICENSE-2.0 
 */
package org.pitest.mutationtest.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.pitest.mutationtest.engine.MutationDetails;
import org.jruby.embed.PathType;


public class LimitNumberOfMutationsFilter implements MutationFilter {

  private ScriptingContainer rubyContainer = new ScriptingContainer();
  Object receiver;

  public LimitNumberOfMutationsFilter(final int max) {
    rubyContainer = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
    receiver = rubyContainer.runScriptlet(PathType.RELATIVE, "filter.rb");
  }


@SuppressWarnings("unchecked")
public Collection<MutationDetails> filter(
      final Collection<MutationDetails> mutations) {
	  try {
		  Object[] args = new Object[1];
		  args[0] = mutations;
		  Object result = rubyContainer.callMethod(receiver, "filter", args, args[0].getClass());
		  return (Collection<MutationDetails>)result;
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return null;
  }
}


