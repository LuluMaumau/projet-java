package Extension4;

import javax.swing.*;

//import satellite.subsystems.Component;

public class SousSystemeLabel extends JLabel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    String couleur;
    String nom;
    // Component comp;

    public SousSystemeLabel(String nom) {

        super();
        couleur = "black";
        this.nom = nom;
        // comp = sousSysteme;
        new JLabel(nom);

    }

    public void ChangeCoulour(String couleur) {

        this.couleur = couleur;
        this.setText("<html><body><font color=" + couleur + ">" + nom + "</body></html>");

    }

}
