package se1test.werkzeuge.einleser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Kommentare
 * 
 * @author Christophad
 *
 */
public class DateienEinleser
{
	private final File ORDNER;

	private final String KENNZEICHNUNG = "##TESTDATEI-OOPM##";
	
	private List<String> _testDateien;
	
	public DateienEinleser(File ordner)
	{
		ORDNER = ordner;
		
		_testDateien = new ArrayList<String>();
		
		getAlleDateienInOrdner(ORDNER);
		
		for(String s : _testDateien)
		{
			System.out.println(s);
		}
	}
	
	private void getAlleDateienInOrdner(File ordner) 
	{
	    for (File datei : ordner.listFiles()) 
	    {
	        if (datei.isDirectory()) 
	        {
	            getAlleDateienInOrdner(datei);
	        } 
	        else if(testeObZulaessig(datei.getPath()))
	        {
	            _testDateien.add(datei.getPath());
	        }
	    }
	}
	
	private boolean testeObZulaessig(String name)
    {
		// Pr�fen, ob die Datei eine Textdatei ist.
		String endung = name.substring(name.length() - 4, name.length());
		
	    if(!endung.equals(".txt"))
	    {
	    	return false;
	    }
	    else if(ersteZeileZulaessig(name))
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
    }

	private boolean ersteZeileZulaessig(String name)
    {
        
        try
        {
            InputStream ips = new FileInputStream(name); 
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            String ausdruck = br.readLine();

            br.close();
            
            if(ausdruck.equals(KENNZEICHNUNG))
            {
            	return true;
            }
            else
            {
            	return false;
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
		return false;
        
    }

	public List<String> getFrageDateien()
	{
		return _testDateien;
	}
}
