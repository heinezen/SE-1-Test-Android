package se1test.werkzeuge;

import java.awt.Component;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

class TestAuswahlWerkzeugUI
{
	private static final String NAME = "Tests";
	
	private JPanel _auswahl;
	private JPanel _anzeige;
	
	private JFrame _frame;
	
	public TestAuswahlWerkzeugUI(List<String> testDateien, TestAuswahlWerkzeug testAuswahlWerkzeug)
	{
		_anzeige = new JPanel();
		_anzeige.setLayout(new BoxLayout(_anzeige, BoxLayout.Y_AXIS));
		
		erzeugeAuswahlBereich(testDateien);
		
		erzeugeHauptfenster();
	}

	private void erzeugeHauptfenster()
    {
		_frame = new JFrame(NAME);
        _frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame.setTitle(NAME);
        _frame.setLocationRelativeTo(null);
        _frame.setVisible(true);
        _frame.add(_auswahl);
        _frame.pack();
    }

	private void erzeugeAuswahlBereich(List<String> testDateien)
    {
	    _auswahl = new JPanel();
	    _auswahl.setLayout(new BoxLayout(_auswahl, BoxLayout.Y_AXIS));
	    
	    for(int i = 0; i < testDateien.size(); ++i)
	    {
	    	JButton auswahlButton = new JButton(testDateien.get(i));
	    	
	    	_auswahl.add(auswahlButton);
	    }
	    
	    _anzeige.add(_auswahl);
    }
	
	public void schließeAuswahlFenster()
	{
		_frame.dispose();
	}

	public Component[] getButtons()
    {
	    return _auswahl.getComponents();
    }
	
	public void showNoQuestionError()
	{
		JOptionPane.showMessageDialog(_frame, "Datei enthält keine Fragen", 
												"Fehler", JOptionPane.ERROR_MESSAGE);
	}
}
