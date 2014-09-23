package se1test.werkzeuge;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import se1test.materialien.Frage;
import se1test.materialien.OptionalFrage;
import se1test.services.FragenService;
import se1test.services.FragenServiceImpl;

/**
 * Das FragenWerkzeug benutzt die GUI, um Fragen anzuzeigen und 
 * den Test abzugeben. Es ist zusätzlich ein Observer der GUI.
 * 
 * @author Christophad
 *
 */
public class FragenWerkzeug
{
	/**
	 * Der FragenService.
	 */
	private FragenService _fragenService;
	
	/**
	 * Die aktuelle Frage.
	 */
	private Frage _aktuelleFrage;
	
	/**
	 * Das UI des FragenWerkzeugs.
	 */
	private FragenWerkzeugUI _uiFragenWerkzeug;
	
	/**
	 * Das AntwortenWerkzeug.
	 */
	private AntwortenWerkzeug _antwortenWerkzeug;
	
	/**
	 * Erstellt ein FragenWerkzeug mit einem Satz an Fragen.
	 * 
	 * @param fragensatz Die Fragen für diese Instanz des Tests.
	 */
	public FragenWerkzeug(Frage[] fragensatz)
	{
		_fragenService = new FragenServiceImpl(fragensatz);
		
		_aktuelleFrage = _fragenService.getAktuelleFrage();
		
		_antwortenWerkzeug = new AntwortenWerkzeug();
		
		_uiFragenWerkzeug = new FragenWerkzeugUI(_antwortenWerkzeug.getPanel());
		_antwortenWerkzeug.setAktuellenFragetyp(_aktuelleFrage.getFragetyp());
		
		registriereButtons();
		
		aendereElemente();
		
		_uiFragenWerkzeug.passeFensterAn();
	}
	
	private void registriereButtons()
    {
		_uiFragenWerkzeug.getAbgabeButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				testAbgeben();
				_uiFragenWerkzeug.passeFensterAn();
			}
		});
		_uiFragenWerkzeug.getVorwaertsButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				naechsteFrage();
				_uiFragenWerkzeug.passeFensterAn();
			}
		});
		_uiFragenWerkzeug.getRueckwaertsButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				vorherigeFrage();
				_uiFragenWerkzeug.passeFensterAn();
			}
		});
    }

	/**
	 * Ändert die GUI-Elemente und passt sie an die Frage an.
	 */
	private void aendereElemente()
	{
		aendereFrage();
		aendereQuelltext();
		aendereBild();
		aendereAntworten();
	}

	private void aendereBild()
    {
		if(_aktuelleFrage instanceof OptionalFrage)
		{
			if(((OptionalFrage) _aktuelleFrage).hatBild())
			{
				_uiFragenWerkzeug.aktualisiereBild(((OptionalFrage) _aktuelleFrage).getBild());
			}
		}
		else
		{
			_uiFragenWerkzeug.entferneBild();
		}
    }

	/**
	 * Ändert den angezeigten Fragebereich.
	 */
	private void aendereFrage()
	{
		_uiFragenWerkzeug.aktualisiereFrage(_aktuelleFrage.getFragetext());
	}
	
	/**
	 * Ändert den angezeigten Quelltext
	 */
	private void aendereQuelltext()
	{
		if(_aktuelleFrage instanceof OptionalFrage)
		{
			_uiFragenWerkzeug.aktualisiereQuelltext(((OptionalFrage) _aktuelleFrage).getQuelltext());
		}
		else
		{
			_uiFragenWerkzeug.aktualisiereQuelltext("");
		}
	}
	
	/**
	 * Ändert den angezeigten Antwortenbereich.
	 */
	private void aendereAntworten()
	{
		_antwortenWerkzeug.aktualisiereAntworten(_aktuelleFrage.getAntworttexte(), 
													_aktuelleFrage.getSpielerAntworten());
	}
	
	/**
	 * Springt zur nächsten Frage im aktuellen Test.
	 */
	private void naechsteFrage()
	{
		_aktuelleFrage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());
		
		_aktuelleFrage = _fragenService.zurNaechstenFrage();
		_antwortenWerkzeug.setAktuellenFragetyp(_aktuelleFrage.getFragetyp());
		
		aendereElemente();
	}
	
	/**
	 * Springt zur vorherigen Frage im aktuellen Test.
	 */
	private void vorherigeFrage()
	{
		_aktuelleFrage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());
		
		_aktuelleFrage = _fragenService.zurVorherigenFrage();
		_antwortenWerkzeug.setAktuellenFragetyp(_aktuelleFrage.getFragetyp());
		
		aendereElemente();
	}

	/**
	 * Gibt den Test ab und nennt die Gesamtpunktzahl.
	 * Außerdem werden alle AntwortElemente gesperrt.
	 */
	private void testAbgeben()
    {
		_aktuelleFrage.aktualisiereSpielerAntworten(_antwortenWerkzeug.getEingaben());
		
	    int endergebnis = _fragenService.berechneGesamtpunktzahl();
	    int maxErgebnis = _fragenService.getMaxPunktzahl();
	    
	    _antwortenWerkzeug.setzeBeendet();
	    _uiFragenWerkzeug.beendeTest(endergebnis, maxErgebnis);
    }
}
