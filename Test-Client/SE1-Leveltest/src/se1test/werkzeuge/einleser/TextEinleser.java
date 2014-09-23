package se1test.werkzeuge.einleser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Liest Text aus einer Text-Datei in einen String ein. \n wird vernachl�ssigt, sodass
 * eine Formatierung in gew�nschter Form mit \n m�glich ist.
 * 
 * @Heine
 * @03/03/14
 */
public class TextEinleser
{
    private String _text;
    private String _titel;
    
    public TextEinleser(String datei)
    {
        _text = "";
        _titel = "";
        
        try
        {
            InputStream ips = new FileInputStream(datei); 
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            // Erste Zeile �berspringen.
            br.readLine();
            // Zweite Zeile ist der Titel des Tests
            _titel = br.readLine();
            // Inhalt einlesen
            String line = br.readLine();
            
            while(line != null)
            {
                _text = _text + line;
                line = br.readLine();
            }

            br.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public String gibTitel()
    {
    	return _titel;
    }
    
    public String toString()
    {
        return _text;
    }
}
