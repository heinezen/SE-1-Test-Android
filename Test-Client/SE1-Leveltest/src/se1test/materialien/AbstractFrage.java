package se1test.materialien;

public abstract class AbstractFrage
{
	/**
	 * Der Text der Frage.
	 */
	private final String _frageText;
	
	/**
	 * Punkte für richtige Antworten.
	 */
	protected final byte _punkteFuerRichtigeAntwort;
	
	/**
	 * Erzeugt eine Frage mit Fragetext.
	 * 
	 * @param fragetext Der Fragetext.
	 * @param fragepunkte Die Punkte pro richtiger Antwort.
	 * 
	 * @require !fragetext.isEmpty()
	 * @require fragepunkte > 0
	 */
	public AbstractFrage(String fragetext, int fragepunkte) 
	{
		assert !fragetext.isEmpty() : "Vorbedingung verletzt : !fragetext.isEmpty()";
		assert fragepunkte > 0 : "Vorbedingung verletzt : fragepunkte > 0";
		
		_frageText = fragetext;
		
		_punkteFuerRichtigeAntwort = (byte) fragepunkte;
	}
	
	/**
	 * Gibt den Text der Frage als String zurück.
	 * 
	 * @return Der Fragetext.
	 */
	public final String getFragetext()
	{
		return _frageText;
	}
	
	/**
	 * Gibt die Punkte für eine richtige Antwort aus.
	 * 
	 * @return Punkte pro richtiger Frage.
	 */
	public int getPunkteFuerAntwort()
	{
		return (int) _punkteFuerRichtigeAntwort;
	}
	
	/**
	 * Gibt die maximal zu erreichende Punktzahl der Frage zurück.
	 * 
	 * @return Die maximal erreichbare Punktzahl.
	 */
	public abstract int getMaxPunktzahl();
	
	/**
	 * Gibt den Fragetyp der Frage zurück.
	 * 
	 * @return Der Fragetyp
	 */
	public abstract String getFragetyp();
}
