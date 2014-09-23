package se1test.startup;

import java.io.File;

import se1test.werkzeuge.TestAuswahlWerkzeug;
import se1test.werkzeuge.einleser.DateienEinleser;


/**
 * Startet die Hauptanwendung mit grafischer Oberfläche.
 * 
 * @author SE2-Team
 * @version SoSe 2014
 */
public class Startup
{
	private static final File TEST_DATEIEN_ORDNER = new File("./Tests");
	
	private static TestAuswahlWerkzeug _testAuswahl;
	
	private static DateienEinleser _dateienEinleser;
	
    public static void main(String[] args)
    {
    	_dateienEinleser = new DateienEinleser(TEST_DATEIEN_ORDNER);
    	
    	_testAuswahl = new TestAuswahlWerkzeug(_dateienEinleser.getFrageDateien());
    }
}
