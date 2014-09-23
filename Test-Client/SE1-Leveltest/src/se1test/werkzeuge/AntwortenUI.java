package se1test.werkzeuge;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class AntwortenUI
{
	/**
	 * Der Antwortenbereich der UI
	 */
    private JPanel _antwortenBereich;
    
    /**
     * Wird auf true gesetzt sobald der Test beendet wurde.
     */
	private boolean _testBeendet;
	
    public AntwortenUI()
    {
    	_testBeendet = false;
    	erzeugeAntwortenBereich();
    }
    
	/**
	 * Erzeugt einen Antwortenbereich.
	 */
	private void erzeugeAntwortenBereich()
	{
		_antwortenBereich = new JPanel();
		_antwortenBereich.setLayout(new BoxLayout(_antwortenBereich, BoxLayout.Y_AXIS));
	}
	
	/**
	 * Gibt den Antwortenbereich zurück.
	 * 
	 * @return Den Antwortenbereich
	 */
	public JPanel getAntwortenPanel()
    {
	    return _antwortenBereich;
    }

	/**
	 * Aktualisiert den Antwortenbereich. Bedienelemente passen sich dem
	 * jeweiligen Fragetyp an.
	 * 
	 * @param antwortTexte Die neuen Texte der Antworten.
	 * @param antwortWerte Die neuen Werte der Antworten.
	 * @param fragetyp 
	 */
	public void aktualisiereAntworten(String[] antwortTexte, Object antwortWerte, String fragetyp)
	{
		switch(fragetyp)
		{
			case "Klick":
			{
				_antwortenBereich.removeAll();
				
				boolean[] neueAntwortWerte = (boolean[]) antwortWerte;
				
				for(int i = 0; i < antwortTexte.length; ++i)
				{
					JCheckBox neueCheckBox = new JCheckBox(antwortTexte[i]);
					neueCheckBox.setSelected(neueAntwortWerte[i]);
					_antwortenBereich.add(neueCheckBox);
					
					if(_testBeendet)
					{
						neueCheckBox.setEnabled(false);
					}
				}
				break;
			}
			case "Auswahl":
			{
				_antwortenBereich.removeAll();
				
				boolean[] neueAntwortWerte = (boolean[]) antwortWerte;
				ButtonGroup radioGruppe = new ButtonGroup();
				
				for(int i = 0; i < antwortTexte.length; ++i)
				{
					JRadioButton neuerRadioButton = new JRadioButton(antwortTexte[i]);
					radioGruppe.add(neuerRadioButton);
					neuerRadioButton.setSelected(neueAntwortWerte[i]);
					_antwortenBereich.add(neuerRadioButton);
					
					if(_testBeendet)
					{
						neuerRadioButton.setEnabled(false);
					}
				}
				break;
			}
			case "Text":
			{
				_antwortenBereich.removeAll();
				
				String neueAntwortWerte = (String) antwortWerte;
				JTextField textfeld = new JTextField();
				
				_antwortenBereich.add(textfeld);
				
				textfeld.setText(neueAntwortWerte);
				
				if(_testBeendet)
				{
					textfeld.setEnabled(false);
				}
				
				break;
			}
			//TODO Andere Antwortmöglichkeiten.
		}
	}
	
	/**
	 * Gibt die Eingaben des Spielers zur derzeitigen Antwort zurück.
	 * dabei wird je nach Fragetyp ein unterschiedlicher Rückgabetyp
	 * verwendet. Beim casten ist auf den Fragetyp zu achten.
	 * @param fragetyp 
	 * 
	 * @return Ein Object mit den derzeitigen Antwortwerten.
	 */
	public Object getEingaben(String fragetyp)
	{
		switch(fragetyp)
		{
			case "Klick":
			{
				JCheckBox[] klickAntworten = new JCheckBox[_antwortenBereich.getComponentCount()];
				
				for(int i = 0; i < _antwortenBereich.getComponentCount(); ++i)
				{
					klickAntworten[i] = (JCheckBox) _antwortenBereich.getComponent(i);				
				}
				
				boolean[] werte = new boolean[klickAntworten.length];
				
				for(int i = 0; i < klickAntworten.length; ++i)
				{
					werte[i] = klickAntworten[i].isSelected();
				}
				
				return werte;
			}
			case "Auswahl":
			{
				JRadioButton[] auswahlAntworten = new JRadioButton[_antwortenBereich.getComponentCount()];
				
				for(int i = 0; i < _antwortenBereich.getComponentCount(); ++i)
				{
					auswahlAntworten[i] = (JRadioButton) _antwortenBereich.getComponent(i);				
				}
				
				boolean[] werte = new boolean[auswahlAntworten.length];
				
				for(int i = 0; i < auswahlAntworten.length; ++i)
				{
					werte[i] = auswahlAntworten[i].isSelected();
				}
				
				return werte;
			}
			case "Text":
			{
				JTextField textfeld = (JTextField) _antwortenBereich.getComponent(0);
				
				String wert = textfeld.getText();
				
				return wert;
			}
				// TODO Andere Antworttypen
			default:
				return null;
		}
	}

	/**
	 * Beendet den Test, sodass keine Antworten mehr gegeben werden können.
	 */
	public void setzeBeendet()
    {
	    _testBeendet = true;
	    sperreAntwortenBereich();
    }
	
	/**
	 * Sperrt den Antwortenbereich für Eingaben.
	 */
	private void sperreAntwortenBereich()
	{    
	    for(Component comp : _antwortenBereich.getComponents())
	    {
	    	comp.setEnabled(false);
	    }
	}
}
