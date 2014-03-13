import java.util.*;
/**
 * Noch nicht fertig.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Fragensatz
{
    FrageZerteiler _fragenzerteiler;
    List<Frage> _fragenliste;
    
    Frage _aktuelleFrage;
    
    public Fragensatz(String datei)
    {
        _fragenzerteiler = new FrageZerteiler(datei);
        _fragenliste = _fragenzerteiler.gibFragenAus();
        
        _aktuelleFrage = _fragenliste.get(0);
    }
    
    /**
     * Berechnet die Summe der in jeder Frage erreichten Punktzahl.
     * 
     * @return Summe der vom Kienten erreichten Punkte
     */
    public int WerteAus()
    {
        int punktzahl = 0;
        for(Frage frage : _fragenliste)
        {
            punktzahl = punktzahl + frage.vergleicheAntworten();
        }
        
        return punktzahl;
    }
    
    /**
     * Lässt den Fragensatz zur nächsten Frage springen. Der Wert von aktuelleFrage wird
     * durch den der nächsten Frage, in der Fragenliste, ersetzt. Falls das Ende der Liste
     * schon erreicht wurde, wird die erste Frage der Fragenliste die aktuelleFrage.
     */
    public void naechsteFrage()
    {
        int index = _fragenliste.indexOf(_aktuelleFrage) + 1;
        
        if(index >= (_fragenliste.size() - 1))
        {
            _aktuelleFrage = _fragenliste.get(0);
        }
        else
        {
            _aktuelleFrage = _fragenliste.get(index);
        }
    }
    
    public void FragenUndAntworttextAusgeben()
    {
        
    }
}
