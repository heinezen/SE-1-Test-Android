package fragenersteller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class DateiSchreiber
{
//	private final File ORDNER;

	private final String KENNZEICHNUNG = "##TESTDATEI-OOPM##";
	
	public DateiSchreiber(File ordner)
	{
//		ORDNER = ordner;
	}
	
	public void schreibeInDatei(String input, String dateiname, boolean zusaetzlich)
	{
		try
		{
			FileWriter fw = new FileWriter(dateiname, zusaetzlich);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			
			if(!zusaetzlich)
			{
				out.println(KENNZEICHNUNG);
			}
			
			String[] text = input.split("<T>");
			
			for(int i = 0; i < text.length; ++i)
			{
				out.println(text[i]);
			}
			out.println();
			
			out.close();
		}
		catch(Exception e)
		{
			
		}
	}
}
