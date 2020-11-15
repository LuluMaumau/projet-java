package Extension4;

import java.awt.event.ActionEvent;
import java.awt.*;

import javax.swing.*;

import Controle.Control;
import Controle.Database;
import data.ReturnedData;
import satellite.*;

public class OnAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Satellite Satellite;
    SousSystemeLabel SousSysteme;
    JPanel historique;
    Database BDD;

    public OnAction(Satellite Satellite, SousSystemeLabel SousSysteme, JPanel historique, Database BDD) {

        super("ON");
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

        ReturnedData answer = Satellite.executeTC(this.SousSysteme.nom, "ACTIVATE");
        if (answer.isSuccess()) {

            historique.add(new JLabel("<html><body><font color= green>" + this.Satellite.getFullname() + ":"
                    + this.SousSysteme.nom + ":" + "ON </body></html>"), c);
            SousSysteme.ChangeCoulour("black");
            SousSysteme.ChangeCoulour("green");
        }

        else {

            historique.add(new JLabel("<html><body><font color= red>" + this.Satellite.getFullname() + ":"
                    + this.SousSysteme.nom + ":" + "ON </body></html>"), c);
            SousSysteme.ChangeCoulour("black");
            SousSysteme.ChangeCoulour("red");

        }

        Control.printResult(answer.isSuccess());

    }
}