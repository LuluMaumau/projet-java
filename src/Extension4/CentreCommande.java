package Extension4;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

//import Controle.Control;
import dmanager.Database;
import satellite.subsystems.*;
import satellite.subsystems.Component;
import satellite.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CentreCommande extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JTabbedPane BarreOnglets;
    private JPanel historique;

    public CentreCommande() {

        build();
        BarreOnglets = new JTabbedPane();
        historique = createHistorique();

    }

    private void build() {

        setTitle("Centre de commande"); // On donne un titre à l'application
        setSize(300, 300); // On donne une taille à notre fenêtre
        setLocationRelativeTo(null); // On centre la fenêtre sur l'écran
        setResizable(true); // On autorise la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // On dit à l'application de se fermer lors du clic sur la croix
        setContentPane(buildContentPane());

    }

    private JPanel buildContentPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        panel.setBackground(Color.white);
        return panel;
    }

    private JPanel createHistorique() {

        JPanel historique = new JPanel();

        historique.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        historique.add(new JLabel("Historique"), c);

        return historique;

    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {

        Database BDD = new Database();
        HashMap<String, Component> Components = new HashMap<String, Component>();
        HashMap<String, Satellite> Satellites = new HashMap<String, Satellite>();
        ArrayList<String> commands = new ArrayList<String>();

        IMG Imager = new IMG("IMAGER", commands);
        Components.put(Imager.getName(), Imager);
        BDD.makeFamily("FAM", Components);

        String[] elements = new String[] { "TEST", "Procedure2", "Procedure3" };

        CentreCommande CC = new CentreCommande();
        GridBagConstraints c = new GridBagConstraints();

        Satellites = BDD.getSatList();

        for (Satellite Sat : Satellites.values()) {

            Onglet SatelliteOnglet = new Onglet(Sat, CC.historique, elements, BDD);
            int u = 1;

            for (Component comp : Sat.getComponents().values()) {

                SatelliteOnglet.addLigne(comp.getName(), u, CC.historique, BDD);
                u++;

            }

            CC.BarreOnglets.add(Sat.getName(), SatelliteOnglet);
        }

        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        Container CentreCommandePanel = CC.getContentPane();

        CentreCommandePanel.add(CC.BarreOnglets, c);
        c.gridy = 2;
        CentreCommandePanel.add(CC.historique, c);

        CC.setVisible(true);

    }
}
