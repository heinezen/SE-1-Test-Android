package se1test.materialien.lueckentext;

import se1test.materialien.Frage;

/**
 * Eine Textfrage, die mehrere Antwortmöglichkeiten in
 * einem Array hält. TextFragen werden verglichen, indem die Antworten
 * des Klienten mit den Möglichkeiten verglichen werden.
 * 
 * @author Christophad
 *
 */
public class TextFrage extends AbstractTextFrage implements Frage
{

	public TextFrage(String fragetext, int fragepunkte,
            String[] antwortMoeglichkeiten)
    {
	    super(fragetext, fragepunkte, antwortMoeglichkeiten);
    }

	@Override
    public int vergleicheAntworten()
    {
		int wert = 0;
		
	    for(String moeglichkeit : _antwortMoeglichkeiten)
	    {
	    	if(moeglichkeit.equals(_spielerAntwort))
	    	{
	    		wert += getPunkteFuerAntwort();
	    	}
	    }
	    
	    return wert;
    }

	@Override
    public void aktualisiereSpielerAntworten(Object neueAntwort)
    {
	    _spielerAntwort = (String) neueAntwort;
    }

	@Override
    public String getFragetyp()
    {
	    return "Text";
    }
}
