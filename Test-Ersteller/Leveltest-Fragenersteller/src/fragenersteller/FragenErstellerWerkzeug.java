package fragenersteller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class FragenErstellerWerkzeug
{
	private FragenErstellerWerkzeugUI _uiFragenErstellerWerkzeug;
	private DateiSchreiber _dateiSchreiber;
	
	private int _aktuellerBereich;
	
	private boolean _zusaetzlicheFrage;
	
	private String _fragetyp;
	private boolean _hatBild;
	private boolean _hatQuelltext;
	private String _fragetext;
	private String _antwortPunkte;
	private String _bildPath;
	private String[] _quelltext;
	private List<String> _antworten;
	private List<Boolean> _antwortWerte;
	
	public FragenErstellerWerkzeug()
	{
		_uiFragenErstellerWerkzeug = new FragenErstellerWerkzeugUI();
		
		_dateiSchreiber = new DateiSchreiber(new File("./Tests/"));
		
		_fragetyp = "";
		_aktuellerBereich = 0;
		_zusaetzlicheFrage = false;
		
		_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(false);
		
		_antworten = new ArrayList<String>();
		_antwortWerte = new ArrayList<Boolean>();
		
		liesFragenInformationenEin();
		
		registriereListener();
	}
	
	private void registriereListener()
	{
		JButton button = _uiFragenErstellerWerkzeug.getZurueckButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				zumVorherigenBereich();
			}
		});
		
		button = _uiFragenErstellerWerkzeug.getAbschliessenButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				schreibeFrage("test.txt");
			}
		});
		
		button = _uiFragenErstellerWerkzeug.getWeiterButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				zumNaechstenBereich();
			}
		});
		
		button = _uiFragenErstellerWerkzeug.getAntwortHinzufuegenButton();
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fuegeAntwortHinzu();
				pruefeObFrageAbgabebereit();
			}
		});
	}

	protected void zumVorherigenBereich()
    {
		--_aktuellerBereich;
		_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(true);
		if(_aktuellerBereich <= 0)
		{
			_aktuellerBereich = 0;
			_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(false);
		}
		
		switch(_aktuellerBereich)
		{
			case 4:
				if(_hatQuelltext)
				{
					_uiFragenErstellerWerkzeug.zumQuelltextBereich();
					break;
				}
				else
				{
					--_aktuellerBereich;
				}
			case 3:
				if(_hatBild)
				{
					_uiFragenErstellerWerkzeug.zumBildBereich();
					break;
				}
				else
				{
					--_aktuellerBereich;
				}
			case 2:
				_uiFragenErstellerWerkzeug.zumAntwortPunkteBereich();
				break;
			case 1:
				_uiFragenErstellerWerkzeug.zumFragetextBereich();
				break;
			case 0:
				_uiFragenErstellerWerkzeug.zumFragetypBereich();
				break;
		}
    }

	protected void zumNaechstenBereich()
    {
		++_aktuellerBereich;
		_uiFragenErstellerWerkzeug.getZurueckButton().setEnabled(true);
		if(_aktuellerBereich >= 5)
		{
			_aktuellerBereich = 5;
			_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(false);
		}
		else if(_aktuellerBereich == 1)
		{
			liesEigenschaftenEin();
		}
		
		switch(_aktuellerBereich)
		{
			case 1:
				_uiFragenErstellerWerkzeug.zumFragetextBereich();
				break;
			case 2:
				_uiFragenErstellerWerkzeug.zumAntwortPunkteBereich();
				break;
			case 3:
				if(_hatBild)
				{
					_uiFragenErstellerWerkzeug.zumBildBereich();
					break;
				}
				else
				{
					++_aktuellerBereich;
				}
			case 4:
				if(_hatQuelltext)
				{
					_uiFragenErstellerWerkzeug.zumQuelltextBereich();
					break;
				}
				else
				{
					++_aktuellerBereich;
					_uiFragenErstellerWerkzeug.getWeiterButton().setEnabled(false);
				}
			case 5:
				_uiFragenErstellerWerkzeug.getAbschliessenButton().setEnabled(false);
				liesFragenInformationenEin();
				pruefeObFrageAbgabebereit();
				_uiFragenErstellerWerkzeug.zumAntwortenBereich(_antworten, _antwortWerte);
				break;
		}
    }
	
	private void pruefeObFrageAbgabebereit()
    {
	    boolean fragetypVorhanden = false;;
	    if(!_fragetyp.isEmpty())
	    {
	    	fragetypVorhanden = true;
	    }
	    
	    boolean fragetextVorhanden = false;
	    if(!_fragetyp.isEmpty())
	    {
	    	fragetextVorhanden = true;
	    }
	    
	    boolean antwortPunkteVorhanden = false;
	    if(!_antwortPunkte.isEmpty())
	    {
	    	if(_antwortPunkte.matches("[0-9]+"))
	    	{
	    		antwortPunkteVorhanden = true;
	    	}
	    }
	    
	    boolean bildVorhanden = false;
	    if(!_hatBild)
	    {
	    	bildVorhanden = true;
	    }
	    else if(!_bildPath.isEmpty())
	    {
	    	bildVorhanden = true;
	    }
	    
	    boolean quelltextVorhanden = false;
	    if(!_hatQuelltext)
	    {
	    	quelltextVorhanden = true;
	    }
	    else if(!(_quelltext.length == 0))
	    {
	    	quelltextVorhanden = true;
	    }
	    
	    boolean antwortenVorhanden = false;
	    if(!_antworten.isEmpty())
	    {
	    	antwortenVorhanden = true;
	    }
	    
	    if(fragetypVorhanden && fragetextVorhanden && antwortPunkteVorhanden
	    		&& bildVorhanden && quelltextVorhanden && antwortenVorhanden)
	    {
	    	_uiFragenErstellerWerkzeug.getAbschliessenButton().setEnabled(true);
	    }
    }

	private void liesEigenschaftenEin()
	{
		switch(_uiFragenErstellerWerkzeug.getFragetyp())
		{
			case 0:
				_fragetyp = "Klick";
				break;
			case 1:
				_fragetyp = "Auswahl";
				break;
			case 2:
				_fragetyp = "Text";
				break;
		}
		_hatBild = _uiFragenErstellerWerkzeug.getBildVorhanden();
		_hatQuelltext = _uiFragenErstellerWerkzeug.getQuelltextVorhanden();
	}
	
	private void liesFragenInformationenEin()
	{
		liesEigenschaftenEin();
		
		_fragetext = _uiFragenErstellerWerkzeug.getFragetext();
		_antwortPunkte = _uiFragenErstellerWerkzeug.getAntwortPunkte();
		_bildPath = _uiFragenErstellerWerkzeug.getBildPfad();
		_quelltext = _uiFragenErstellerWerkzeug.getQuelltext();
	}
	
	private void fuegeAntwortHinzu()
	{
		String text = _uiFragenErstellerWerkzeug.getAntwortTextFeld().getText();
		boolean richtig = _uiFragenErstellerWerkzeug.getAntwortWertFeld().isSelected();
		_antworten.add(text);
		_antwortWerte.add(new Boolean(richtig));
		
		_uiFragenErstellerWerkzeug.getAntwortTextFeld().setText("");
		_uiFragenErstellerWerkzeug.getAntwortWertFeld().setSelected(false);
	}
	
	private void schreibeFrage(String dateiname)
	{
		String frage = _fragetext + "<T>::"
				+ _fragetyp + "<T>::"
				+ _antwortPunkte + "<T>::"
				+ _hatBild;
	
		if(_hatBild)
		{
			frage += "<<BB>>" + _bildPath + "<T>::";
		}
		else
		{
			frage += "<T>::";
		}
		
		frage += _hatQuelltext;
		
		if(_hatQuelltext)
		{
			for(int i = 0; i < _quelltext.length; ++i)
			{
				frage += "<T><<QQ>>" + _quelltext[i];
			}
			frage += "<T>::";
		}
		else
		{
			frage += "<T>::";
		}
		
		for(int i = 0; i < _antworten.size(); ++i)
		{
			frage += _antworten.get(i);
			if(!_fragetyp.equals("Text"))
			{
				frage += "<<!!>>" + _antwortWerte.get(i);
			}
			if(i < (_antworten.size() - 1))
			{
				frage += "<T>::";
			}
		}
		
		frage += "<T><<##>>";
		
		_dateiSchreiber.schreibeInDatei(frage, dateiname, _zusaetzlicheFrage);
		
		_zusaetzlicheFrage = true;
	}
}
