package se1test.werkzeuge.einleser;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import se1test.exceptions.CorruptQuestionException;
import se1test.exceptions.NoQuestionsInFileException;
import se1test.materialien.Antwort;
import se1test.materialien.Frage;
import se1test.materialien.choice.ChoiceAntwort;
import se1test.materialien.choice.multiChoice.KlickFrage;
import se1test.materialien.choice.multiChoice.OptionalKlickFrage;
import se1test.materialien.choice.singleChoice.AuswahlFrage;
import se1test.materialien.choice.singleChoice.OptionalAuswahlFrage;
import se1test.materialien.lueckentext.OptionalTextFrage;
import se1test.materialien.lueckentext.TextFrage;


/**
 * Zerteilt den vom TextEinleser eingelesenen Text in Blöcke mit denen dann Fragen und Antworten
 * erzeugt werden. Gespeichert wird das Ergebnis in einer (Fragen-)Liste. Die Fragen und Antworten
 * enstehen indem der String aus dem Texteinleser gesplittet wird. Dies geschieht nach folgenden
 * Regeln:
 * 
 * "<<##>>" am Ende eines Fragenblockes.
 * "::" am Ende eines Antwortenblockes.
 * "<<QQ>>" am Ende eines Quelltextblockes.
 * "<<!!>>" am Ende eines Antwortenwertes.
 * 
 * Die Blöcke enthalten Frage und Antwortenwerte in Rohform.
 * 
 * @Heine 
 * @03/03/14
 */
public class FrageZerteiler
{
	/**
	 * Die Indizes für Teile einer Frage
	 */
	private final int FRAGETEXT_INDEX = 0;
	private final int FRAGETYP_INDEX = 1;
	private final int FRAGEPUNKTE_INDEX = 2;
	private final int BILDPFAD_INDEX = 3;
	private final int QUELLTEXT_INDEX = 4;
	private final int ANTWORTEN_INDEX = 5;
	
	/**
	 * Die Trenner mit denen Werte im Dokument getrennt sind.
	 */
	private final String FRAGEN_SPLITTER = "<<##>>";
	private final String ANTWORTEN_SPLITTER = "::";
	private final String BILDPFAD_SPLITTER = "<<BB>>";
	private final String QUELLTEXT_SPLITTER = "<<QQ>>";
	private final String ANTWORTWERT_SPLITTER = "<<!!>>";
	
    private TextEinleser _texteinleser;
    private String _rohText;
    
    private List<Frage> _fragenliste;
    
    /**
     * Erstellt einen neuen Fragenzerteiler der die Textdatei aus dem String datei einliest.
     */
    public FrageZerteiler(String datei)
    {
        _texteinleser = new TextEinleser(datei);
        _rohText = _texteinleser.toString();
        
        _fragenliste = new LinkedList<Frage>();
        
        liesFragenEin();
    }
    
    /**
     * @return Die aus dem Textdokument eingelesenen Fragen und Antworten
     * 
     * @throws NoQuestionsInFileException
     */
    public Frage[] gibFragenAus() throws NoQuestionsInFileException
    {
    	if(!_fragenliste.isEmpty())
    	{	
    		Frage[] fragen = new Frage[_fragenliste.size()];
            
            for(int i = 0; i < _fragenliste.size(); ++i)
            {
            	fragen[i] = (Frage) _fragenliste.toArray()[i];
            }
            
            return fragen;
    	}
    	else
    	{
    		throw new NoQuestionsInFileException();
    	}
    }
    
    /**
     * Zerteilt den rohen Text aus dem Quelldokument in einzelne Frageblöcke.
     * Die Methode liesFrageEin gibt eine Frage zurück, die der Frageliste hinzugefügt wird.
     * 
     * Wichtig: Der erste String des Array rohFragen ist immer der Header der Textdatei, also
     * irrelevant für das Auslesen.
     */
    private void liesFragenEin()
    {
        String[] rohFragen = _rohText.split(FRAGEN_SPLITTER);
        
        for(int i = 0; i < rohFragen.length; ++i)
        {
            try
            {
	            _fragenliste.add(liesFrageEin(rohFragen[i]));
            }
            catch (CorruptQuestionException e)
            {
	            JOptionPane.showMessageDialog(null, e.getMessage() + (i + 1) + ")", 
	            								"Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Zerteilt den rohen Text des Frageblockes in Antwortenblöcke. Dabei ist der erste String des
     * Arrays rohAntworten der Fragetext für die Frage aus dem Frageblock. Die Frage wird als Objekt
     * zurückgegeben mit dem Fragetext und den durch liesAntwortEin erhaltenen Antworten.
     * 
     * @param rohfrage Die unformattierte Frage
     * 
     * @throws CorruptQuestionException
     */
    private Frage liesFrageEin(String rohfrage) throws CorruptQuestionException
    {
        String[] rohAntworten = rohfrage.split(ANTWORTEN_SPLITTER);
        
        /** Die Frage muss Fragetext, Fragetyp, Punkte und Überprüfung
        * auf Quelltext und Bild ermöglichen, sowie mindestens eine 
        * Antwortmöglichkeit haben.
        */
        if(rohAntworten.length < ANTWORTEN_INDEX + 1)
        {
        	throw new CorruptQuestionException("Frage kann nicht gelesen werden. (Frage ");
        }
        
        // Erster String des Arrays ist immer der Fragetext
        String fragetext = rohAntworten[FRAGETEXT_INDEX];
        if(fragetext.isEmpty())
        {
        	throw new CorruptQuestionException("Fragetext ist nicht vorhanden. (Frage ");
        }
        // Zweiter String des Arrays ist immer der Fragetyp.
        String fragetyp = rohAntworten[FRAGETYP_INDEX];
        // Dritter String des Arrays sind die Punkte für eine richtige Antwort
        if(!rohAntworten[FRAGEPUNKTE_INDEX].matches("[0-8]"))
        {
        	throw new CorruptQuestionException("Fragepunkte sind nicht lesbar. (Frage ");
        }
        int fragepunkte = Integer.parseInt(rohAntworten[FRAGEPUNKTE_INDEX]);
        if(fragepunkte <= 0)
        {
        	throw new CorruptQuestionException("Fragepunkte sind inkorrekt. (Frage ");
        }
        // Vieter String des Array ist die Bilddatei.
        File bild = null;
        if(ueberpruefeAufOptionaleElemente(rohAntworten[BILDPFAD_INDEX]))
        {
        	bild = new File(liesBildPfadEin(rohAntworten[BILDPFAD_INDEX]));
        	
        	if(!bild.exists())
        	{
        		throw new CorruptQuestionException("Das Bild im Pfad " + bild.getAbsolutePath()
        											+ " existiert nicht.");
        	}
        	else if(!bild.canRead())
        	{
        		throw new CorruptQuestionException(bild.getPath() + "\nZugriff nicht möglich.");
        	}
        }
        // Fünfter String des Arrays ist der Quelltext.
        String quelltext = "";
        if(ueberpruefeAufOptionaleElemente(rohAntworten[QUELLTEXT_INDEX]))
        {
        	quelltext = liesQuelltextEin(rohAntworten[QUELLTEXT_INDEX]);
        }
        
        switch(fragetyp)
        {
        	// TODO Bildatei in Frage überführen.
        	case "Klick":
        		return erstelleKlickFrage(fragetext, rohAntworten,
        				fragepunkte, quelltext, bild);
        	case "Auswahl":
        		return erstelleAuswahlFrage(fragetext, rohAntworten,
        				fragepunkte, quelltext, bild);
        	case "Text":
        		return erstelleTextFrage(fragetext, rohAntworten, 
        				fragepunkte, quelltext, bild);
            default:
            	throw new CorruptQuestionException("Fragetyp unklar. (Frage ");
        }
    }

	/**
     * Liest den Pfad des Bildes einer Frage ein.
     * 
     * @param rohPfad Der unformatierte Pfad.
     * @return String mit Pfad des Bildes.
     */
    private String liesBildPfadEin(String rohPfad)
    {
    	String[] pfad = rohPfad.split(BILDPFAD_SPLITTER);
    	String endergebnis = pfad[1];
    	
    	return endergebnis;
    }

	/**
     * Erstellt eine TextFrage aus den gegebenen Informationen.
     * 
     * @param fragetext Der Fragetext.
     * @param rohAntworten Die Antwortmöglichkeiten.
     * @param fragepunkte Die Punkte pro richtiger Antwort.
     * @param quelltext Der Quelltext.
     * @return Eine TextFrage oder QuelltextTextFrage.
     */
    private Frage erstelleTextFrage(String fragetext, String[] rohAntworten,
            int fragepunkte, String quelltext, File bild)
    {
    	List<String> eingeleseneAntworten = new ArrayList<String>();
		
        // Alle anderen Objekte des Array sind Antworten
        for(int i = ANTWORTEN_INDEX; i < rohAntworten.length; ++i)
        {
            eingeleseneAntworten.add(rohAntworten[i]);
        }
        
        String[] antworten = new String[eingeleseneAntworten.size()];
        
        for(int i = 0; i < eingeleseneAntworten.size(); ++i)
        {
        	antworten[i] = (String) eingeleseneAntworten.toArray()[i];
        }
        
        if(quelltext.isEmpty())
        {
        	return new TextFrage(fragetext, fragepunkte, antworten);
        }
        else if(bild == null)
        {
        	return new OptionalTextFrage(fragetext, fragepunkte, antworten, quelltext);
        }
        else if(quelltext.isEmpty())
        {
        	return new OptionalTextFrage(fragetext, fragepunkte, antworten, bild);
        }
        else
        {
        	return new OptionalTextFrage(fragetext, fragepunkte, antworten, quelltext, bild);
        }
    }

    /**
     * Erstellt eine AuswahlFrage aus den gegebenen Informationen.
     * 
     * @param fragetext Der Fragetext.
     * @param rohAntworten Die Antwortmöglichkeiten.
     * @param fragepunkte Die Punkte pro richtiger Antwort.
     * @param quelltext Der Quelltext.
     * @return Eine AuswahlFrage oder QuelltextAuswahlfrage.
     */
	private Frage erstelleAuswahlFrage(String fragetext, String[] rohAntworten,
			int fragepunkte, String quelltext, File bild)
    {
		List<ChoiceAntwort> eingeleseneAntworten = new ArrayList<ChoiceAntwort>();
		
        // Alle anderen Objekte des Array sind Antworten
        for(int i = ANTWORTEN_INDEX; i < rohAntworten.length; ++i)
        {
            try
            {
	            eingeleseneAntworten.add((ChoiceAntwort) liesChoiceAntwortEin(rohAntworten[i]));
            }
            catch (CorruptQuestionException e)
            {
            	JOptionPane.showMessageDialog(null, e.getMessage(), 
						"Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
		
    	ChoiceAntwort[] antworten = new ChoiceAntwort[eingeleseneAntworten.size()];
        
        for(int i = 0; i < eingeleseneAntworten.size(); ++i)
        {
        	antworten[i] = (ChoiceAntwort) eingeleseneAntworten.toArray()[i];
        }
        
        if(quelltext.isEmpty() && bild == null)
        {
        	return new AuswahlFrage(fragetext, fragepunkte, antworten);
        }
        else if(bild == null)
        {
        	return new OptionalAuswahlFrage(fragetext, fragepunkte, antworten, quelltext);
        }
        else if(quelltext.isEmpty())
        {
        	return new OptionalAuswahlFrage(fragetext, fragepunkte, antworten, bild);
        }
        else
        {
        	return new OptionalAuswahlFrage(fragetext, fragepunkte, antworten, quelltext, bild);
        }
    }

	/**
     * Erstellt eine KlickFrage aus den gegebenen Informationen.
     * 
     * @param fragetext Der Fragetext.
     * @param rohAntworten Die Antwortmöglichkeiten.
	 * @param fragepunkte Die Punkte pro richtiger Antwort.
	 * @param quelltext Der Quelltext.
     * @return Eine KlickFrage oder QuelltextKlickFrage.
     */
	private Frage erstelleKlickFrage(String fragetext, String[] rohAntworten,
			int fragepunkte, String quelltext, File bild)
    {
		List<ChoiceAntwort> eingeleseneAntworten = new ArrayList<ChoiceAntwort>();
		
        // Alle anderen Objekte des Array sind Antworten
        for(int i = ANTWORTEN_INDEX; i < rohAntworten.length; ++i)
        {
        	try
            {
	            eingeleseneAntworten.add((ChoiceAntwort) liesChoiceAntwortEin(rohAntworten[i]));
            }
            catch (CorruptQuestionException e)
            {
            	JOptionPane.showMessageDialog(null, e.getMessage(), 
						"Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }
		
    	ChoiceAntwort[] antworten = new ChoiceAntwort[eingeleseneAntworten.size()];
        
        for(int i = 0; i < eingeleseneAntworten.size(); ++i)
        {
        	antworten[i] = (ChoiceAntwort) eingeleseneAntworten.toArray()[i];
        }
        
        if(quelltext.isEmpty())
        {
        	return new KlickFrage(fragetext, fragepunkte, antworten);
        }
        else if(bild == null)
        {
        	return new OptionalKlickFrage(fragetext, fragepunkte, antworten, quelltext);
        }
        else if(quelltext.isEmpty())
        {
        	return new OptionalKlickFrage(fragetext, fragepunkte, antworten, bild);
        }
        else
        {
        	return new OptionalKlickFrage(fragetext, fragepunkte, antworten, quelltext, bild);
        }
    }

	/**
	 * Liest den Quelltext einer Frage ein.
	 * 
	 * @param rohQuelltext Der unformatierte Quelltext.
	 * @return
	 */
	private String liesQuelltextEin(String rohQuelltext)
    {
    	String[] quelltext = rohQuelltext.split(QUELLTEXT_SPLITTER);
    	String endergebnis = quelltext[1];
    	
    	for(int i = 2; i < quelltext.length; ++i)
    	{
    		endergebnis += "\n" + quelltext[i];
    	}
    	
    	return endergebnis;
    }

	/**
	 * Prüft einen String auf das Vorhandensein von optionalen
	 * Element. Dies kann ein Quelltext oder ein Bild sein.
	 * 
	 * @param pruefung String mit möglichem Quelltext oder Bildpfad.
	 * @return Ist Quelltext vorhanden oder nicht.
	 */
	private boolean ueberpruefeAufOptionaleElemente(String pruefung)
    {
	    String indikator = pruefung.substring(0, 4);
	    
	    if(indikator.equals("true"))
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
    }

	/**
	 * Zerteilt den String rohantwort in die einzelnen Wertebereiche und wandelt diese in eine
     * Antwort um.
     * 
	 * @param rohantwort Die unformatierte Antwort.
	 * 
	 * @return Eine Antwort.
	 * 
	 * @throws CorruptQuestionException 
	 */
    private Antwort liesChoiceAntwortEin(String rohantwort) throws CorruptQuestionException
    {
        String[] antwortWerte = rohantwort.split(ANTWORTWERT_SPLITTER);
        
        // Text der Antwort auslesen
        String antwortText = "";
        if(antwortWerte[0].isEmpty())
        {
            throw new CorruptQuestionException("Antworttext kann nicht gelesen werden.");
        }
        antwortText = antwortWerte[0];
        
        // Wert der Antwort auslesen
        boolean antwortWert = false;
        if(antwortWerte[1].equals("true"))
        {
            antwortWert = true;
        }
        else if(antwortWerte[1].equals("false"))
        {
            antwortWert = false;
        }
        else
        {
        	throw new CorruptQuestionException("Antwortwert kann nicht gelesen werden.");
        }
        
        return new ChoiceAntwort(antwortText, antwortWert);
    }
}
