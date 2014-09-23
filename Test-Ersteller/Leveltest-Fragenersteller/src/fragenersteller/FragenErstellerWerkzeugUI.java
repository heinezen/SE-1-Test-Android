package fragenersteller;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class FragenErstellerWerkzeugUI
{
	private static final String NAME = "Testersteller";
	
	// Beschreibungstext für jeweilige Eingabe
	private JLabel _beschreibungsLabel;
	
	// Buttons zur Navigation
	private JButton _zurueckButton;
	private JButton _abschliessenButton;
	private JButton _weiterButton;
	
	// Button zum Hinzufügen einer Antwort
	private JButton _antwortHinzufuegenButton;
	
	// Auswahl für den Fragetyp
	private JRadioButton[] _fragetypenButtons;
	private JCheckBox[] _fragetypenOptional;
	
	// Eingabefelder
	private JTextField _fragetextFeld;
	private JTextField _antwortPunkteFeld;
	private JTextField _bildpfad;
	private JTextArea _quelltextArea;
	private JTextField _antwortFeld;
	private JCheckBox _antwortWert;
	
	// Beschreibung der jeweiligigen Eingabe
	private JPanel _beschreibungsBereich;
	
	// Bereiche mit Eingabefeldern
	private JPanel _fragetypBereich;
	private JPanel _fragetextBereich;
	private JPanel _antwortPunkteBereich;
	private JPanel _bildBereich;
	private JPanel _quelltextBereich;
	private JPanel _antwortenBereich;
	
	// Bereiche mit Anzeige und Buttons
	private JPanel _buttonBereich;
	private JPanel _anzeige;
	
	private JFrame _frame;
	
	/**
	 * Erstellt ein UI des Fragenerstellers und initialisiert
	 * dieses.
	 */
	public FragenErstellerWerkzeugUI()
	{
		initGUI();
	}

	/**
	 * Initialisiert die GUI-Elemente des Testerstellers.
	 */
	private void initGUI()
    {
		_anzeige = new JPanel();
		_anzeige.setLayout(new BoxLayout(_anzeige, BoxLayout.Y_AXIS));
		
		erzeugeBeschreibungsBereich();
	    erzeugeEingabeBereiche();
	    erzeugeButtonBereich();
	    
	    erzeugeHauptfenster();
	    
	    zumFragetypBereich();
    }

	/**
	 * Erzeugt die Eingabebereiche der GUI.
	 */
	private void erzeugeEingabeBereiche()
    {
	    erzeugeFragetypBereich();
	    erzeugeFragetextBereich();
	    erzeugeAntwortPunkteBereich();
	    erzeugeBildBereich();
	    erzeugeQuelltextBereich();
	    erzeugeAntwortenBereich();
    }

	/**
	 * Erzeugt den Beschreibungsbereich der GUI.
	 */
	private void erzeugeBeschreibungsBereich()
    {
	    _beschreibungsBereich = new JPanel();
	    _anzeige.add(_beschreibungsBereich);
	    
	    _beschreibungsLabel = new JLabel("Fragetyp auswählen");
	    _beschreibungsBereich.add(_beschreibungsLabel);
    }

	/**
	 * Erzeugt den buttonbereich der GUI.
	 */
	private void erzeugeButtonBereich()
    {
		_buttonBereich = new JPanel();
		_anzeige.add(_buttonBereich);
		
		_zurueckButton = new JButton("Zurück");
		_abschliessenButton = new JButton("Frage abschließen");
	    _weiterButton = new JButton("Weiter");
	    
	    _buttonBereich.add(_zurueckButton);
	    _buttonBereich.add(_weiterButton);
    }

	/**
	 * Erzeugt das Hauptfenster.
	 */
	private void erzeugeHauptfenster()
    {
		_frame = new JFrame(NAME);
        _frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame.setTitle(NAME);
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
        _frame.add(_anzeige);
        _frame.pack();
    }

	/**
	 * Erzeugt den Bereich in dem der Fragetyp ausgewählt wird.
	 */
	private void erzeugeFragetypBereich()
    {
		_fragetypBereich = new JPanel();
		_fragetypBereich.setLayout(new BoxLayout(_fragetypBereich, BoxLayout.PAGE_AXIS));
		_anzeige.add(_fragetypBereich);
		
		ButtonGroup typGruppe = new ButtonGroup();
		_fragetypenButtons = new JRadioButton[3];
		_fragetypenOptional = new JCheckBox[2];
		
		_fragetypenButtons[0] = new JRadioButton("Multiple-Choice");
		typGruppe.add(_fragetypenButtons[0]);
		_fragetypBereich.add(_fragetypenButtons[0]);
		
		_fragetypenButtons[1] = new JRadioButton("Single-Choice");
		typGruppe.add(_fragetypenButtons[1]);
		_fragetypBereich.add(_fragetypenButtons[1]);
		
		_fragetypenButtons[2] = new JRadioButton("Textantwort");
		typGruppe.add(_fragetypenButtons[2]);
		_fragetypBereich.add(_fragetypenButtons[2]);
		
		_fragetypenOptional[0] = new JCheckBox("Bild");
		_fragetypBereich.add(_fragetypenOptional[0]);
		
		_fragetypenOptional[1] = new JCheckBox("Quelltext");
		_fragetypBereich.add(_fragetypenOptional[1]);
    }
	
	/**
	 * Erzeugt den Bereich in dem der Fragetext eingegeben wird.
	 */
	private void erzeugeFragetextBereich()
    {
		_fragetextBereich = new JPanel();
		_fragetypBereich.setLayout(new BoxLayout(_fragetypBereich, BoxLayout.PAGE_AXIS));
		
		_fragetextFeld = new JTextField(30);
		
		_fragetextBereich.add(_fragetextFeld);
    }
	
	/**
	 * Erzeugt den Bereich in dem die Punkte pro
	 * richtiger Antwort eingegeben werden.
	 */
	private void erzeugeAntwortPunkteBereich()
    {
		_antwortPunkteBereich = new JPanel();
		
		_antwortPunkteFeld = new JTextField(4);
		
		_antwortPunkteBereich.add(_antwortPunkteFeld);
    }
	
	/**
	 * Erzeugt den Bereich in dem der Pfad zu dem 
	 * Bild angegeben wird.
	 */
	private void erzeugeBildBereich()
    {
		_bildBereich = new JPanel();
		
		_bildpfad = new JTextField(20);
		
		_bildBereich.add(_bildpfad);
    }
	
	/**
	 * Erzeugt den Bereich in dem der Quelltext eingegeben wird.
	 */
	private void erzeugeQuelltextBereich()
    {
		_quelltextBereich = new JPanel();
		
		_quelltextArea = new JTextArea(10, 40);
		
		_quelltextBereich.add(_quelltextArea);
    }
	
	/**
	 * Erzeugt den Bereich in dem die Antworten eingegeben
	 * und hinzugefügt werden.
	 */
	private void erzeugeAntwortenBereich()
    {
		_antwortenBereich = new JPanel();
		_antwortenBereich.setLayout(new BoxLayout(_antwortenBereich, BoxLayout.PAGE_AXIS));
		
		_antwortFeld = new JTextField(30);
		_antwortWert = new JCheckBox("Richtig");
		_antwortHinzufuegenButton = new JButton("Antwort hinzufügen");
		
		_antwortenBereich.add(_antwortFeld);
		_antwortenBereich.add(_antwortWert);
		_antwortenBereich.add(_antwortHinzufuegenButton);
    }
	
	/**
	 * Lässt die GUI den Fragetypbereich anzeigen.
	 */
	public void zumFragetypBereich()
	{
		_beschreibungsLabel.setText("Fragetyp angeben");
		
		_anzeige.remove(1);
		_anzeige.add(_fragetypBereich, 1);
		
		_frame.pack();
	}
	
	/**
	 * Lässt die GUI den Fragetextbereich anzeigen.
	 */
	public void zumFragetextBereich()
	{
		_beschreibungsLabel.setText("Fragetext eingeben");
		
		_anzeige.remove(1);
		_anzeige.add(_fragetextBereich, 1);
		
		_frame.pack();
	}
	
	/**
	 * Lässt die GUI den Antwortpunktebereich anzeigen.
	 */
	public void zumAntwortPunkteBereich()
	{
		_beschreibungsLabel.setText("Punkte pro richtiger Antwort angeben");
		
		_buttonBereich.remove(_abschliessenButton);
		
		_anzeige.remove(1);
		_anzeige.add(_antwortPunkteBereich, 1);
		
		_frame.pack();
	}
	
	/**
	 * Lässt die GUI den Bildbereich anzeigen.
	 */
	public void zumBildBereich()
	{
		_beschreibungsLabel.setText("Bildpfad auswählen");
		
		_buttonBereich.remove(_abschliessenButton);
		
		_anzeige.remove(1);
		_anzeige.add(_bildBereich, 1);
		
		_frame.pack();
	}
	
	/**
	 * Lässt die GUI den Quelltextbereich anzeigen.
	 */
	public void zumQuelltextBereich()
	{
		_beschreibungsLabel.setText("Quelltext eingeben");
		
		_buttonBereich.remove(_abschliessenButton);
		
		_anzeige.remove(1);
		_anzeige.add(_quelltextBereich, 1);
		
		_frame.pack();
	}
	
	/**
	 * Lässt die GUI den Antwortenbereich anzeigen.
	 */
	public void zumAntwortenBereich(List<String> antwortTexte, List<Boolean> antwortWerte)
	{
		_beschreibungsLabel.setText("Antworten eingeben");
		
		_buttonBereich.add(_abschliessenButton, 1);
		
		_anzeige.remove(1);
		_anzeige.add(_antwortenBereich, 1);
		
		_frame.pack();
	}
	
	/**
	 * Gibt den Zurück-Button zurück.
	 * @return Der Zurück-Button.
	 */
	public JButton getZurueckButton()
	{
		return _zurueckButton;
	}
	
	/**
	 * Gibt den Abschliessen-Button zurück.
	 * @return Der Abschliessen-Button.
	 */
	public JButton getAbschliessenButton()
	{
		return _abschliessenButton;
	}
	
	/**
	 * Gibt den Weiter-Button zurück.
	 * @return Der Weiter-Button.
	 */
	public JButton getWeiterButton()
	{
		return _weiterButton;
	}
	
	/**
	 * Gibt den Button zurück, mit dem Antworten hinzugefügt
	 * werden.
	 * @return Der Antwort-Hinzufügen-Button.
	 */
	public JButton getAntwortHinzufuegenButton()
	{
		return _antwortHinzufuegenButton;
	}
	
	/**
	 * Gibt den Fragetyp der aktuell zu erstellenden Frage zurück.
	 * 
	 * @return 0,1,2 für die Fragetypen Klick, Auswahl, Text
	 * 			-1, falls nichts angewählt ist
	 */
	public int getFragetyp()
	{
		for(int i = 0; i < _fragetypenButtons.length; ++i)
		{
			if(_fragetypenButtons[i].isSelected())
			{
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Gibt zurück, ob die Frage ein Bild enthält.
	 * @return Wahrheitswert, ob Frage Bild enthält.
	 */
	public boolean getBildVorhanden()
	{
		return _fragetypenOptional[0].isSelected();
	}
	
	/**
	 * Gibt zurück, ob die Frage Quelltext enthält.
	 * @return Wahrheitswert, ob Frage Quelltext enthält.
	 */
	public boolean getQuelltextVorhanden()
	{
		return _fragetypenOptional[1].isSelected();
	}
	
	/**
	 * Gibt den Fragetext aus dem Eingabefeld zurück.
	 * @return Der Fragetext.
	 */
	public String getFragetext()
	{
		return _fragetextFeld.getText();
	}
	
	/**
	 * Gibt die Antwortpunkte aus dem Eingabefeld zurück.
	 * @return Die Antwortpunkte.
	 */
	public String getAntwortPunkte()
	{
		return _antwortPunkteFeld.getText();
	}
	
	/**
	 * Gibt den Pfad des Bildes aus dem Eingabefeld zurück.
	 * @return Der Pfad als String.
	 */
	public String getBildPfad()
	{
		return _bildpfad.getText();
	}
	
	/**
	 * Gibt den Quelltext aus dem Eingabefeld zurück.
	 * @return Der Quelltext.
	 */
	public String[] getQuelltext()
	{
		return _quelltextArea.getText().split("\\n");
	}
	
	/**
	 * Gibt den Antworttext aus dem Eingabefeld zurück.
	 * @return Der Antworttext.
	 */
	public JTextField getAntwortTextFeld()
	{
		return _antwortFeld;
	}
	
	/**
	 * Gibt den Antwortwert der Antwort zurück.
	 * @return Der Antwortwert.
	 */
	public JCheckBox getAntwortWertFeld()
	{
		return _antwortWert;
	}
}
