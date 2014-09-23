package se1test.materialien.choice.singleChoice;

import se1test.materialien.Frage;
import se1test.materialien.choice.AbstractChoiceFrage;
import se1test.materialien.choice.ChoiceAntwort;

/**
 * Eine Single-Choice-Frage, die mehrere Single-Choice-Antworten in
 * einem Array h�lt. AuswahlFragen werden verglichen, indem die Antworten
 * des Klienten mit den tats�chlichen �ber Wahrheitswerte verglichen 
 * werden.
 * 
 * @author Christophad
 *
 */
public class AuswahlFrage extends AbstractChoiceFrage implements Frage
{
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Single-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte f�r eine richtige Antwort.
	 * @param antworten Die m�glichen Antworten der Frage.
	 */
	public AuswahlFrage(String fragetext, int fragepunkte,
            ChoiceAntwort[] antworten)
    {
	    super(fragetext, fragepunkte, antworten);
    }

	@Override
    public int vergleicheAntworten()
    {
		int wert = 0;
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i];
			if(antwort.istRichtig() && _spielerAntworten[i])
			{
				wert += getPunkteFuerAntwort();
			}
		}
		
		return wert;
    }
	
	public int getMaxPunktzahl()
	{
		return _punkteFuerRichtigeAntwort;
	}

	@Override
    public void aktualisiereSpielerAntworten(Object neueAntworten)
    {
	    _spielerAntworten = (boolean[]) neueAntworten;
    }

	@Override
    public String getFragetyp()
    {
	    return "Auswahl";
    }
}
