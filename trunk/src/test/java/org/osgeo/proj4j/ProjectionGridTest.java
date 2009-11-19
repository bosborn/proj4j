package org.osgeo.proj4j;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * Tests accuracy and correctness of projecting and reprojecting a grid of geographic coordinates.
 * 
 * @author Martin Davis
 *
 */
public class ProjectionGridTest extends TestCase
{
	static final double TOLERANCE = 0.00001;
	
  public static void main(String args[]) {
    TestRunner.run(ProjectionGridTest.class);
  }

  public ProjectionGridTest(String name) { super(name); }

  public void testAlbers()
  {
  	runEPSG(3005);
  }
  
  public void testStatePlane()
  {
  	// State-planes
  	runEPSG(2759, 2930);
  }
  
  void runEPSG(int codeStart, int codeEnd)
  {
  	for (int i = codeStart; i <= codeEnd; i++) {
  		runEPSG(i);
  	}
  }
  
  void runEPSG(int code)
  {
  	run("epsg:" + code);
  }
  	
 void run(String code)
 {
   CoordinateSystemFactory csFactory = new CoordinateSystemFactory();
  	CoordinateSystem cs = csFactory.createFromName(code);
  	if (cs == null)
  		return;
  	ProjectionGridRoundTripper tripper = new ProjectionGridRoundTripper(cs);
  	//tripper.setLevelDebug(true);
  	boolean isOK = tripper.runGrid(TOLERANCE);
  	double[] extent = tripper.getExtent();
  	
    System.out.println(code + " - " + cs.getParameterString()); 
    System.out.println( 
  			"  - extent: [ " + extent[0] + ", " + extent[1] + " : " + extent[2] + ", " + extent[3] + " ]" 
        + " - tol: " + TOLERANCE
  			+ " - # pts run = " + tripper.getTransformCount());
  	
  	assertTrue(isOK);
  }
}
