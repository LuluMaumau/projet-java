package Extension4;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import satellite.Satellite;

public class ProcedureAction extends AbstractAction {

    /**
    *
    */
    private static final long serialVersionUID = 1L;

    JComboBox selection;
    JPanel historique;
    Satellite satellite;

    ProcedureAction(JComboBox selection, JPanel historique, Satellite sat) {

        super("LAUNCH PROCEDURE");
        this.selection = selection;
        satellite = sat;
        this.historique = historique;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.LINE_START;
        String procedure = "" + selection.getSelectedItem();

        JLabel result = new JLabel(
                "<html><body><font color= green>" + this.satellite.getFullname() + ":" + procedure + " </body></html>");
        historique.add(result, c);
        historique.updateUI();

    }

}
