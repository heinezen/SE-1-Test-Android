package se1test.werkzeuge;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import se1test.exceptions.NoQuestionsInFileException;
import se1test.werkzeuge.einleser.FrageZerteiler;

public class TestAuswahlWerkzeug
{
	private FrageZerteiler _fragenZerteiler;

	private TestAuswahlWerkzeugUI _uiTestAuswahl;

	private List<String> _testDateien;

	public TestAuswahlWerkzeug(List<String> testDateien)
	{
		_testDateien = testDateien;

		_uiTestAuswahl = new TestAuswahlWerkzeugUI(testDateien, this);

		registriereButtons();
	}

	private void registriereButtons()
	{
		Component[] dateien = _uiTestAuswahl.getButtons();

		for (int i = 0; i < dateien.length; ++i)
		{
			final int buttonIndex = i;

			if (dateien[i] instanceof JButton)
			{
				((JButton) dateien[i]).addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e)
					{
						erzeugeFragenWerkzeug(buttonIndex);
					}
				});
			}
		}
	}

	private void erzeugeFragenWerkzeug(int testFragen)
	{
		_fragenZerteiler = new FrageZerteiler(_testDateien.get(testFragen));

		try
		{
			new FragenWerkzeug(_fragenZerteiler.gibFragenAus());
			_uiTestAuswahl.schließeAuswahlFenster();
		} 
		catch (NoQuestionsInFileException e)
		{
			_uiTestAuswahl.showNoQuestionError();
		}
	}
}
