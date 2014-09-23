package se1test.werkzeuge;

import javax.swing.JPanel;

public class AntwortenWerkzeug
{
	/**
	 * Das UI des AntwortenWerkzeugs.
	 */
	private AntwortenUI _uiAntworten;
	
	/**
	 * Der Fragetyp der angezeigten Frage.
	 */
	private String _aktuellerFragetyp;
	
	public AntwortenWerkzeug()
	{
		_uiAntworten = new AntwortenUI();
	}
	
	/**
	 * Gibt das Panel des Antwortebbereiches zurück.
	 * 
	 * @return Der Panel des Antwortenbereichs
	 */
	public JPanel getPanel()
    {
	    return _uiAntworten.getAntwortenPanel();
    }

	/**
	 * Aktualisiert den Antwortenbereich. Bedienelemente passen sich dem
	 * jeweiligen Fragetyp an.
	 * 
	 * @param antwortTexte Die neuen Texte der Antworten.
	 * @param antwortWerte Die neuen Werte der Antworten.
	 */
	public void aktualisiereAntworten(String[] antwortTexte, Object antwortWerte)
    {
	    _uiAntworten.aktualisiereAntworten(antwortTexte, antwortWerte, _aktuellerFragetyp);
    }

	/**
	 * Gibt die Eingaben des Spielers zur derzeitigen Antwort zurück.
	 * dabei wird je nach Fragetyp ein unterschiedlicher Rückgabetyp
	 * verwendet. Beim casten ist auf den Fragetyp zu achten.
	 * 
	 * @return Ein Object mit den derzeitigen Antwortwerten.
	 */
	public Object getEingaben()
	{
		return _uiAntworten.getEingaben(_aktuellerFragetyp);
	}
	
	/**
	 * Setzt den aktuellen Fragetyp.
	 * 
	 * @param fragetyp Der Fragetyp als String.
	 */
	public void setAktuellenFragetyp(String fragetyp)
	{
		_aktuellerFragetyp = fragetyp;
	}
	
	/**
	 * Setzt den Status des Antwirtenbereichs auf beendet. Dadurch werden 
	 * Bedienelemente im Antwortenbereich gesperrt.
	 */
	public void setzeBeendet()
	{
		_uiAntworten.setzeBeendet();
	}
}
