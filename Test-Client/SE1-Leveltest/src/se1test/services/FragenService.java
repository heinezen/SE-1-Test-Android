package se1test.services;

import se1test.materialien.Frage;

/**
 * Ein Fragenservice zum Beantworten der Fragen und Berechnen der
 * Gesamtpunktzahl.
 * 
 * @author Christophad
 *
 */
public interface FragenService 
{	
	/**
	 * Berechnet die Gesamtpunktzahl für alle Fragen.
	 * 
	 * @return Erreichte Gesamtpunktzahl.
	 */
	public int berechneGesamtpunktzahl();

	/**
	 * Lässt den Klienten zur nächsten Frage springen. Falls die letzte Frage
	 * schon erreicht ist, wird die erste Frage zur aktuellen Frage.
	 * 
	 */
	public Frage zurNaechstenFrage();

	/**
	 * Lässt den Klienten zur vorherigen Frage springen. Falls die erste Frage
	 * schon erreicht ist, wird die letzte Frage zur aktuellen Frage.
	 * 
	 */
	public Frage zurVorherigenFrage();
	
	/**
	 * Lasst den Klienten zu einer bestimmten Frage springen.
	 * 
	 * @param index Index der angewählten Frage.
	 * 
	 * @require index < _fragensatz.length
	 * @require index >= 0
	 */
	public Frage zuFrage(int index);
	
	/**
	 * Bringt die Fragen aus dem Fragensatz in eine zufällige
	 * Reihenfolge.
	 */
	public Frage[] mischeFragensatz(Frage[] fragensatz);
	
	/**
	 * Gibt die maximale Punktzahl für alle Fragen zurück.
	 * 
	 * @return Maximal erreichbare Punktzahl aller Fragen.
	 */
	public int getMaxPunktzahl();
	
	/**
	 * Gibt die aktuelle Frage zurück.
	 * 
	 * @return Die aktuelle Frage im Fragenservice
	 */
	public Frage getAktuelleFrage();
}
