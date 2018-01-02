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
package thredds.server.catalog;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thredds.TestOnLocalServer;
import thredds.client.catalog.Catalog;
import thredds.client.catalog.builder.CatalogBuilder;
import ucar.unidata.util.test.category.NeedsCdmUnitTest;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;

/**
 * Test catalog utilities
 */
@Category(NeedsCdmUnitTest.class)
public class TdsLocalCatalog {
  private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  
  public static boolean showValidationMessages = false;

  public static Catalog openFromURI(URI uri) throws IOException {
    String catPath = uri.toString();
    CatalogBuilder builder = new CatalogBuilder();
    Catalog cat = builder.buildFromLocation(catPath, null);
    if (builder.hasFatalError()) {
      System.out.println("Validate failed "+ catPath+" = \n<"+ builder.getErrorMessage()+">");
      assert false : builder.getErrorMessage();
    } else if (showValidationMessages)
      System.out.println("Validate ok "+ catPath+" = \n<"+ builder.getErrorMessage()+">");

    return cat;
  }


  public static Catalog open(String catalogName) throws IOException {
    if (catalogName == null) catalogName = "/catalog.xml";
    String catalogPath = TestOnLocalServer.withHttpPath(catalogName);
    System.out.println("\n open= "+catalogPath);

    CatalogBuilder builder = new CatalogBuilder();
    Catalog cat = builder.buildFromLocation(catalogPath, null);
    if (builder.hasFatalError()) {
      System.out.println("Validate failed "+ catalogName+" = \n<"+ builder.getErrorMessage()+">");
      assert false : builder.getErrorMessage();
    } else if (showValidationMessages)
      System.out.println("Validate ok "+ catalogName+" = \n<"+ builder.getErrorMessage()+">");

    return cat;
  }

  @Test
  public void readCatalog() {
    Catalog mainCat;
    try {
      mainCat = open(null);
      assert mainCat != null;
    } catch (IOException e) {
      assert false;
    }
  }

}
