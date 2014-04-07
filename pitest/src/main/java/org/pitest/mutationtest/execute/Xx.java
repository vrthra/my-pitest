package org.pitest.mutationtest.execute;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;

public class Xx {
  HashMap<String, PrintWriter> hout;
  static Xx x = null;
  public static Xx i() {
	  if (x == null) x = new Xx();
	  return x;
  }
  private Xx() {
    hout = new HashMap<String,PrintWriter>();
  }
  public void finalize() {
    for(PrintWriter pw : hout.values()) {
      pw.close();
    }
  }
  private PrintWriter get(String i) throws FileNotFoundException, UnsupportedEncodingException {
    if (!hout.containsKey(i)) {
      hout.put(i, new PrintWriter("pid." +i + ".log", "UTF-8"));
    }
    return hout.get(i);
  }
  private static String getPid() {
    return ManagementFactory.getRuntimeMXBean().getName();
  }
  
  public static void log(String s) {
    try {
      PrintWriter p = i().get(getPid());
      p.println("Xx: " + s);
      p.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
