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
package ucar.nc2;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;

public class TestRedefine3 {
  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Rule public final TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testRedefine3() throws IOException, InvalidRangeException {
    String filename = tempFolder.newFile().getAbsolutePath();
    NetcdfFileWriter ncFile = NetcdfFileWriter.createNew (filename, false);
    ncFile.setExtraHeaderBytes (64*1000);
    Dimension dim = ncFile.addDimension ("time", 100);

    double[] jackData = new double[100];
    for (int i = 0; i < 100; i++) jackData[i] = i;
    double[] jillData = new double[100];
    for (int i = 0; i < 100; i++) jillData[i] = 2*i;

    ncFile.addVariable ("jack", DataType.DOUBLE, "time");
    ncFile.addVariableAttribute ("jack", "where", "up the hill");
    ncFile.create();

    int[] start = new int[] {0};
    int[] count = new int[] {100};
    ncFile.write ("jack", start, Array.factory (DataType.DOUBLE, count, jackData));

    ncFile.setRedefineMode (true);
    ncFile.addVariable ("jill", DataType.DOUBLE, "time");
    ncFile.addVariableAttribute ("jill", "where", "up the hill");
    ncFile.setRedefineMode (false);

    Array jillArray = Array.factory (DataType.DOUBLE, count, jillData);
    ncFile.write ("jill", start, jillArray);

    ncFile.flush();
    ncFile.close();

    NetcdfFile nc = NetcdfFile.open(filename, null);
    Variable v = nc.findVariable("jill");
    Array jillRead = v.read();
    ucar.unidata.util.test.CompareNetcdf.compareData(jillArray, jillRead);

    nc.close();
  }
}
