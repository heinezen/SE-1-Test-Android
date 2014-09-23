package se1test.materialien.choice;

import se1test.materialien.AbstractFrage;
import se1test.materialien.Antwort;

/**
 * Definiert die grundlegenden Methoden einer Frage. Fragen müssen
 * ihren Fragetext und ihre Antworttexte ausgeben können. Außerdem
 * kann die Punktzahl einer Frage errechnet werden und ihre Antworten
 * können verglichen werden.
 * 
 * @author Christophad
 *
 */
public abstract class AbstractChoiceFrage extends AbstractFrage
{	
	/**
	 * Das Array mit den Antworten der Frage.
	 */
	protected final Antwort[] _antwortArray;
	
	/**
	 * Antworten des Spielers.
	 */
	protected boolean[] _spielerAntworten;
	
	/**
	 * Erzeugt eine Frage mit Fragetext und mehreren Multiple-Choice-Antworten.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte 
	 * @param antworten Die möglichen Antworten der Frage.
	 */
	public AbstractChoiceFrage(String fragetext, int fragepunkte, Antwort[] antworten) 
	{
		super(fragetext, fragepunkte);
		
		_antwortArray = mischeAntworten(antworten);
		
		_spielerAntworten = new boolean[_antwortArray.length];
	}
	
	/**
	 * Mischt die Antworten, um eine zufällige Reihenfolge zu erhalten.
	 * 
	 * @param antworten Die zu mischenden Antworten
	 * @return Die eingegebenen Antworten in neuer Reihenfolge
	 */
	private Antwort[] mischeAntworten(Antwort[] antworten)
    {
		Antwort[] tempAntworten = antworten.clone();
	    
	    for(int i = 0; i < tempAntworten.length; ++i)
	    {
	    	int random = (int) (Math.random() * (tempAntworten.length - 1));
	    	Antwort tempAntwort = tempAntworten[i];
	    	tempAntworten[i] = tempAntworten[random];
	    	tempAntworten[random] = tempAntwort;
	    }
	    
	    return tempAntworten;
    }
	
	/**
	 * Gibt die Antworttexte als Array von Strings aus.
	 * 
	 * @return Die Antworttexte.
	 */
	public String[] getAntworttexte()
	{
		String[] antworttexte = new String[_antwortArray.length];
		
		for(int i = 0; i < _antwortArray.length; ++i)
		{
			antworttexte[i] = _antwortArray[i].getAntwortText();
		}
		
		return antworttexte;
	}
	
	/**
	 * Gibt die Antworten des Spielers als Object zurück.
	 * 
	 * @return Die Antworten des Spielers
	 */
	public Object getSpielerAntworten()
	{
		return _spielerAntworten;
	}
	
	/**
	 * Gibt die richtigen Antworten der Frage zurück.
	 * 
	 * @return Die richtigen Antworten.
	 */
	public Object getAntwortenWerte()
	{
		boolean[] antwortWerte = new boolean[_antwortArray.length];
		
		for(int i = 0; i < antwortWerte.length; ++i)
		{
			ChoiceAntwort antwort = (ChoiceAntwort) _antwortArray[i]; 
			antwortWerte[i] = antwort.istRichtig();
		}
		
		return antwortWerte;
	}
	
	/**
	 * Gibt die maximal zu erreichende Punktzahl der Frage zurück.
	 * 
	 * @return Die maximal erreichbare Punktzahl.
	 */
	public abstract int getMaxPunktzahl();
	
	/**
	 * Vergleicht die Antworten des Spielers mit den tatsächlichen
	 * Antworten. Zurückgegeben wird eine Punktzahl, die die
	 * erreichten Punkte für die Frage darstellt.
	 * 
	 * @return Das Punktzahl für alle Antworten.
	 */
	public abstract int vergleicheAntworten();
	
	/**
	 * Aktualisiert die Antworten des Spielers.
	 * 
	 * @param neueAntworten Die neue(n) Antwort(en) des Spielers.
	 */
	public abstract void aktualisiereSpielerAntworten(Object neueAntworten);
}
