package se1test.werkzeuge;

import java.awt.Color;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

class FragenWerkzeugUI
{
    public static final String NAME = "SE1-Test";
    
    private JLabel _fragetextLabel;
    private JLabel _bildLabel;
    
	private JTextArea _quelltextLabel;
    
    private JButton _naechsteFrageButton;
    private JButton _vorherigeFrageButton;
    private JButton _abgabeButton;
    
    private JPanel _anzeige;
    private JPanel _frageBereich;
    private JPanel _bildBereich;
    private JPanel _quelltextBereich;
    private JPanel _antwortenBereich;
    private JPanel _abgabeBereich;
    private JPanel _bedienBereich;

	private JFrame _frame;
	
	/**
	 * Erstellt eine neue GUI für ein FragenWerkzeug und fügt dieses
	 * zu den Observern hinzu.
	 * 
	 * @param fragenwerkzeug Das beobachtende FragenWerkzeug.
	 */
	public FragenWerkzeugUI(JPanel antwortenPanel)
	{
		initGUI(antwortenPanel);
	}
	
	/**
	 * Initilisiert die Interfaceelemente der GUI.
	 */
	private void initGUI(JPanel antwortenPanel)
	{
		_anzeige = new JPanel();
		_anzeige.setLayout(new BoxLayout(_anzeige, BoxLayout.Y_AXIS));
		
		erzeugeFrageBereich();
		erzeugeBildBereich();
		erzeugeQuelltextBereich();
		erzeugeAntwortenBereich(antwortenPanel);
		erzeugeBedienBereich();
		erzeugeAbgabeBereich();
		
		erzeugeHauptfenster();
	}
	
	/**
	 * Erzeugt einen Bildbereich mit einem JLabel.
	 */
	private void erzeugeBildBereich()
    {
	    _bildBereich = new JPanel();
	    _anzeige.add(_bildBereich);
	    
	    _bildLabel = new JLabel();
	    
	    _bildBereich.add(_bildLabel);
    }

	/**
	 * Erzeugt einen Fragenbereich mit einem JLabel
	 */
	private void erzeugeFrageBereich()
	{
		_frageBereich = new JPanel();
		_anzeige.add(_frageBereich);
		
		_fragetextLabel = new JLabel("Frage?");
		
		_frageBereich.add(_fragetextLabel);
	}
	
	/**
	 * Erzeugt einen Quelltextbereich mit einem JLabel.
	 */
	private void erzeugeQuelltextBereich()
	{
		_quelltextBereich = new JPanel();
		_anzeige.add(_quelltextBereich);
		
		_quelltextLabel = new JTextArea("Quelltext");
		_quelltextLabel.setEditable(false);
		_quelltextLabel.setForeground(Color.BLUE);
		
		_quelltextBereich.add(_quelltextLabel);
		_quelltextBereich.setEnabled(false);
	}
	
	/**
	 * Erzeugt einen Antwortenbereich.
	 */
	private void erzeugeAntwortenBereich(JPanel antwortenPanel)
	{
		_antwortenBereich = antwortenPanel;
		_anzeige.add(_antwortenBereich);
	}
	
	/**
	 * Erzeugt einen BedienBereich mit Vorwärts- und Zurück-Knopf.
	 */
	private void erzeugeBedienBereich()
	{
		_bedienBereich = new JPanel();
		_anzeige.add(_bedienBereich);
		
		_vorherigeFrageButton = new JButton("Vorherige Frage");
		
		_naechsteFrageButton = new JButton("Nächste Frage");
		
		_bedienBereich.add(_vorherigeFrageButton);
		_bedienBereich.add(_naechsteFrageButton);
	}
	
	/**
	 * Erzeug einen AbgabeBereich mit Abgabebutton.
	 */
	private void erzeugeAbgabeBereich()
	{
		_abgabeBereich = new JPanel();
		_anzeige.add(_abgabeBereich);
		
		_abgabeButton = new JButton("Test abgeben");
		
		_abgabeBereich.add(_abgabeButton);
	}
	
	/**
	 * Erzeugt das Hauptfenster der GUI.
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
	 * Passt das Fenster an die Bedienelemente an.
	 */
	public void passeFensterAn()
	{
		_frame.pack();
	}
	
	/**
	 * Aktualisiert den Text im Fragebereich.
	 * 
	 * @param neuerFragetext Der neue Fragetext.
	 */
	public void aktualisiereFrage(String neuerFragetext)
	{
		_fragetextLabel.setText(neuerFragetext);
	}

	/**
	 * Beendet den Test und gibt die erreichte Punktzahl aus.
	 * 
	 * @param endergebnis Erreichte Punktzahl
	 * @param maxPunktzahl Die maximal erreichbare Punktzahl
	 */
	public void beendeTest(int endergebnis, int maxPunktzahl)
    {
	    _abgabeBereich.add(new JLabel("Erreichte Punktzahl: " + endergebnis
	    													+ "/" + maxPunktzahl));
	    _abgabeBereich.remove(_abgabeButton);
    }
	
	/**
	 * Aktualisiert den Quelltextbereich.
	 * 
	 * @param neuerQuelltext Der neue Quelltext.
	 */
	public void aktualisiereQuelltext(String neuerQuelltext)
	{
		_quelltextLabel.setText(neuerQuelltext);
	}
	
	/**
	 * Aktualisiert den Bildbereich mit einem Bild.
	 * 
	 * @param neuesBild Das neue Bild.
	 * 
	 * @require neuesBild != null
	 */
	public void aktualisiereBild(File neuesBild)
	{
		assert neuesBild != null : "Vorbedingung verletzt : neuesBilde != null";
		
		_bildLabel.setIcon(new ImageIcon(neuesBild.getPath()));
	}
	
	/**
	 * Entfernt das Bild aus dem Bildbereich.
	 */
	public void entferneBild()
	{
		_bildLabel.setIcon(null);
	}
	
	/**
	 * Gibt den Button der zur nächsten Frage springt zurück.
	 * 
	 * @return Der JButton
	 */
	public JButton getVorwaertsButton()
	{
		return _naechsteFrageButton;
	}
	
	/**
	 * Gibt den Button der zur vorigen Frage springt zurück.
	 * 
	 * @return Der JButton
	 */
	public JButton getRueckwaertsButton()
	{
		return _vorherigeFrageButton;
	}
	
	/**
	 * Gibt den Button für die Abgabe zurück.
	 * 
	 * @return Der JButton
	 */
	public JButton getAbgabeButton()
	{
		return _abgabeButton;
	}
}
