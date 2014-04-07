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
import java.util.logging.Logger;
import org.pitest.util.Log;



public class LimitNumberOfMutationsFilter implements MutationFilter {

  private static final Logger      LOG = Log.getLogger();


  private ScriptingContainer rubyContainer = new ScriptingContainer();
  Object receiver;

  public LimitNumberOfMutationsFilter(final int max) {
    rubyContainer = new ScriptingContainer(LocalVariableBehavior.PERSISTENT);
    try {
      receiver = rubyContainer.runScriptlet(PathType.RELATIVE, "filter.rb");
    } catch (Exception ex) {
      LOG.info("NO FILTERS FOUND check filter.rb");
    }
  }


@SuppressWarnings("unchecked")
public Collection<MutationDetails> filter(
      final Collection<MutationDetails> mutations) {
    if (receiver == null) return mutations;
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


