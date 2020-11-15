package Extension4;

import java.awt.event.ActionEvent;
import java.awt.*;

import javax.swing.*;

import Controle.Control;
import Controle.Database;
import data.ReturnedData;
import satellite.*;

public class DataAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Satellite Satellite;
    SousSystemeLabel SousSysteme;
    JPanel historique;
    Database BDD;

    public DataAction(Satellite Satellite, SousSystemeLabel SousSysteme, JPanel historique, Database BDD) {

        super("DATA");
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

        ReturnedData answer = Satellite.executeTM(SousSysteme.nom);

        if (answer.isSuccess()) {
            Control.archive(BDD, answer.getRecoveredData());
            historique.add(new JLabel("<html><body><font color= green>" + this.Satellite.getFullname() + ":"
                    + this.SousSysteme.nom + ":" + "DATA </body></html>"), c);
            SousSysteme.ChangeCoulour("black");
            SousSysteme.ChangeCoulour("green");
            System.out.println("DATA");
        }

        else {

            historique.add(new JLabel("<html><body><font color= red>" + this.Satellite.getFullname() + ":"
                    + this.SousSysteme.nom + ":" + "DATA </body></html>"), c);
            SousSysteme.ChangeCoulour("black");
            SousSysteme.ChangeCoulour("red");

        }

    }
}