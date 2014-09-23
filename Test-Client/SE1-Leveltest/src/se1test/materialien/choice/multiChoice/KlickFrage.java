package se1test.materialien.choice.multiChoice;

import se1test.materialien.Frage;
import se1test.materialien.choice.AbstractChoiceFrage;
import se1test.materialien.choice.ChoiceAntwort;

/**
 * Eine Multiple-Choice-Frage, die mehrere Multiple-Choice-Antworten in
 * einem Array hält. KlickFragen werden verglichen, indem die Antworten
 * des Klienten mit den tatsächlichen über Wahrheitswerte verglichen 
 * werden.
 * 
 * @author Christophad
 *
 */
public class KlickFrage extends AbstractChoiceFrage implements Frage
{
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte für eine richtige Antwort.
	 * @param antworten Die möglichen Antworten der Frage.
	 */
	public KlickFrage(String fragetext, int fragepunkte, ChoiceAntwort[] antworten) 
	{
		super(fragetext, fragepunkte, antworten);
	}
	
	public int vergleicheAntworten()
	{
		boolean[] vergleich = new boolean[_antwortArray.length];
		
		int wert = 0;
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
			if(antwort.istRichtig() == _spielerAntworten[i])
			{
				vergleich[i] = true;
			}
			
			if(!vergleich[i])
			{
				wert -= getPunkteFuerAntwort();
			}
			else if(vergleich[i] && antwort.istRichtig())
			{
				wert += getPunkteFuerAntwort();
			}
		}
		
		if(wert < 0)
		{
			wert = 0;
		}
		
		return wert;
	}
	
	public int getMaxPunktzahl()
	{
		int wert = 0;
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
			if(antwort.istRichtig())
			{
				wert += _punkteFuerRichtigeAntwort;
			}
		}
		
		return wert;
	}

	@Override
	public void aktualisiereSpielerAntworten(Object antwortWerte) 
	{
		_spielerAntworten = (boolean[]) antwortWerte;
	}

	@Override
    public String getFragetyp()
    {
	    return "Klick";
    }
}
