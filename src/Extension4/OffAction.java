package Extension4;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dtype.ReturnedData;
import exec.Control;
import dmanager.Database;
import satellite.*;

public class OffAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Satellite Satellite;
    SousSystemeLabel SousSysteme;
    JPanel historique;
    Database BDD;

    public OffAction(Satellite Satellite, SousSystemeLabel SousSysteme, JPanel historique, Database BDD) {

        super("OFF");
        this.Satellite = Satellite;
        this.SousSysteme = SousSysteme;
        this.historique = historique;
        this.BDD = BDD;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.LINE_START;

        ReturnedData answer = Satellite.executeTC(this.SousSysteme.nom, "DESACTIVATE");
        if (answer.isSuccess()) {

            historique.add(new JLabel("<html><body><font color= green>" + this.Satellite.getName() + ":"
                    + this.SousSysteme.nom + ":" + "OFF </body></html>"), c);
            SousSysteme.ChangeCoulour("black");
            SousSysteme.ChangeCoulour("green");
        }

        else {

            historique.add(new JLabel("<html><body><font color= red>" + this.Satellite.getName() + ":"
                    + this.SousSysteme.nom + ":" + "OFF </body></html>"), c);
            SousSysteme.ChangeCoulour("black");
            SousSysteme.ChangeCoulour("red");

        }
        Control.printResult(answer.isSuccess());

    }
}
