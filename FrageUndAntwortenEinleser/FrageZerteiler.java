import java.util.*;
/**
 * Zerteilt den vom TextEinleser eingelesenen Text in Blöcke mit denen dann Fragen und Antworten
 * erzeugt werden. Gespeichert wird das Ergebnis in einer (Fragen-)Liste. Die Fragen und Antworten
 * enstehen indem der String aus dem Texteinleser gesplittet wird. Dies geschieht nach folgenden
 * Regeln:
 * 
 * "<<##>>" am Anfang und Ende eines Fragenblockes.
 * "<<??>>" am Anfang und Ende eines Antwortenblockes.
 * "<<!!>>" am Anfang und Ende eines Antwortenwertes.
 * 
 * Die Blöcke enthalten Frage und Antwortenwerte in Rohform.
 * 
 * @Heine 
 * @03/03/14
 */
public class FrageZerteiler
{
    TextEinleser _texteinleser;
    String _rohText;
    
    List<Frage> _fragenliste;
    
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
     */
    public List<Frage> gibFragenAus()
    {
        return _fragenliste;
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
        String[] rohFragen = _rohText.split("<<##>>");
        
        for(int i = 1; i < rohFragen.length; ++i)
        {
            _fragenliste.add(liesFrageEin(rohFragen[i]));
        }
    }
    
    /**
     * Zerteilt den rohen Text des Frageblockes in Antwortenblöcke. Dabei ist der erste String des
     * Arrays rohAntworten der Fragetext für die Frage aus dem Frageblock. Die Frage wird als Objekt
     * zurückgegeben mit dem Fragetext und den durch liesAntwortEin erhaltenen Antworten.
     */
    private Frage liesFrageEin(String rohfrage)
    {
        String[] rohAntworten = rohfrage.split("<<??>>");
        List<Antwort> eingeleseneAntworten = new ArrayList<Antwort>();
        
        // Erster String des Arrays ist immer der Antworttext
        String fragetext = rohAntworten[0];
        // Alle anderen Objekte des Array sind Antworten
        for(int i = 1; i < rohAntworten.length; ++i)
        {
            eingeleseneAntworten.add(liesAntwortEin(rohAntworten[i]));
        }
        
        Antwort[] antworten = (Antwort[]) eingeleseneAntworten.toArray();
        
        return new Frage(fragetext, antworten);
    }
    
    /**
     * Zerteilt den String rohantwort in die einzelnen Wertebereiche und wandelt diese in eine
     * Antwort um. Wenn ein Wert nicht den Konventionen entspricht wird eine Warnung ausgegeben.
     */
    private Antwort liesAntwortEin(String rohantwort)
    {
        String[] antwortWerte = rohantwort.split("<<!!>>");
        
        if(antwortWerte.length != 3)
        {
            System.out.println("Warnung: Integrität der Antwort nicht gegeben!");
        }
        
        // Text der Antwort auslesen
        String antwortText = "";
        if(antwortWerte[0].length() > 0)
        {
            antwortText = antwortWerte[0];
        }
        else
        {
            System.out.println("Warnung: Antworttext ist leerer String!");
        }
        
        // Punktzahl der Antwort auslesen
        int antwortPunkte = Integer.parseInt(antwortWerte[1]);
        
        // Wert der Frage auslesen
        boolean antwortWert = false;
        if(antwortWerte[2].equals("true"))
        {
            antwortWert = true;
        }
        else if(antwortWerte[2].equals("false"))
        {
            antwortWert = false;
        }
        else
        {
            System.out.println("Warnung: Wert der Antwort ist nicht auslesbar!");
        }
        
        return new Antwort(antwortText, antwortPunkte, antwortWert);
    }
}
