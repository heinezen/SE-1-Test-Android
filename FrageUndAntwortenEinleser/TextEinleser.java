import java.io.*;
/**
 * Liest Text aus einer Text-Datei in einen String ein. \n wird vernachlässigt, sodass
 * eine Formatierung in gewünschter Form mit ßn möglich ist.
 * 
 * @Heine
 * @03/03/14
 */
public class TextEinleser
{
    String _text;
    
    public TextEinleser(String datei)
    {
        _text = "";
        
        try
        {
            InputStream ips = new FileInputStream(datei); 
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

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
    
    public String toString()
    {
        return _text;
    }
}
