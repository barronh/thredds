// $Id: TestStandardPrefs.java,v 1.1.1.1 2002/12/20 16:40:27 john Exp $
/*
 * Copyright 1998-2009 University Corporation for Atmospheric Research/Unidata
 *
 * Portions of this software were developed by the Unidata Program at the
 * University Corporation for Atmospheric Research.
 *
 * Access and use of this software shall impose the following obligations
 * and understandings on the user. The user is granted the right, without
 * any fee or cost, to use, copy, modify, alter, enhance and distribute
 * this software, and any derivative works thereof, and its supporting
 * documentation for any purpose whatsoever, provided that this entire
 * notice appears in all copies of the software, derivative works and
 * supporting documentation.  Further, UCAR requests that the user credit
 * UCAR/Unidata in any publications that result from the use of this
 * software or in any product that includes this software. The names UCAR
 * and/or Unidata, however, may not be used in any advertising or publicity
 * to endorse or promote any products or commercial entity unless specific
 * written permission is obtained from UCAR/Unidata. The user also
 * understands that UCAR/Unidata is not obligated to provide the user with
 * any support, consulting, training or assistance of any kind with regard
 * to the use, operation and performance of this software nor to provide
 * the user with any updates, revisions, new versions or "bug fixes."
 *
 * THIS SOFTWARE IS PROVIDED BY UCAR/UNIDATA "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UCAR/UNIDATA BE LIABLE FOR ANY SPECIAL,
 * INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE ACCESS, USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package ucar.util.prefs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.prefs.*;
import java.io.*;

public class TestStandardPrefs  {
  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public static void main(String args[]) {
    //System.getProperty("ucar.util.prefs.PreferencesExtFactory");
    TestStandardPrefs pf = new TestStandardPrefs();
    pf.foo();
  }

  // Preference keys for this package
  private static final String NUM_ROWS = "num_rows";
  private static final String NUM_COLS = "num_cols";

  void foo() {
    Preferences userRoot = Preferences.userRoot();
    Preferences prefs = userRoot.node("myApp");

    System.out.println("rows = "+prefs.getInt(NUM_ROWS, 40));
    System.out.println("cols = "+prefs.getInt(NUM_COLS, 80));

    prefs.putInt(NUM_ROWS, 140);
    prefs.putInt(NUM_COLS, 180);

    prefs.putDouble("TestDoubleInt", 111);
    prefs.putDouble("TestDouble", 3.14156);
    prefs.putBoolean("TestBoolean", false);

    System.out.println("*rows = "+prefs.getInt(NUM_ROWS, 40));
    System.out.println("cols = "+prefs.getInt(NUM_COLS, 80));

    Preferences subnode = prefs.node("subnode");
    subnode.put("an entry", "value entry");
    Preferences subsubnode = subnode.node("subsubnode");

    try {
      //OutputStream os = new FileOutputStream("standardPrefs.xml");
      userRoot.exportSubtree( System.out);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
/* Change History:
   $Log: TestStandardPrefs.java,v $
   Revision 1.1.1.1  2002/12/20 16:40:27  john
   start new cvs root: prefs

*/
