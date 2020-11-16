package Extension4;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import dmanager.Database;
import satellite.*;

public class Onglet extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Database BDD;
    Satellite Satellite;
    GridBagConstraints gbc;
    JPanel historique;
    ArrayList<SousSystemeLabel> SousSystemes;

    public Onglet(Satellite Satellite, JPanel historique, String[] elements, Database BDD) {

        this.Satellite = Satellite;
        this.historique = historique;
        SousSystemes = new ArrayList<SousSystemeLabel>();

        new JPanel();
        GridBagLayout gb = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gb);
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(8, 8, 8, 8);

        JComboBox ChoixProcedure = new JComboBox(elements);
        this.add(ChoixProcedure);
        JButton Procedure = new JButton(new ProcedureAction(ChoixProcedure, historique, Satellite, BDD));
        this.add(Procedure);
    }

    public void addLigne(String SousSysteme, int line, JPanel historique, Database BDD) {

        gbc.gridy = line;

        SousSystemeLabel SousSystemeLabel = new SousSystemeLabel(SousSysteme);
        SousSystemes.add(SousSystemeLabel);
        this.add(SousSystemeLabel, this.gbc);

        SousSystemeLabel.ChangeCoulour("black");

        JButton ButtonOn = new JButton(new OnAction(this.Satellite, SousSystemeLabel, historique, BDD));

        this.add(ButtonOn, this.gbc);

        JButton ButtonOff = new JButton(new OffAction(this.Satellite, SousSystemeLabel, historique, BDD));

        this.add(ButtonOff, this.gbc);

        JButton ButtonData = new JButton(new DataAction(this.Satellite, SousSystemeLabel, historique, BDD));

        this.add(ButtonData, this.gbc);

    }

}
