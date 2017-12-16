import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

class Calculatrice extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel container = new JPanel();
    //Tableau stockant les éléments à afficher dans la calculatrice
    private String[] tab_string = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", ".", "=", "C", "+", "-", "*", "/"};
    //Un bouton par élément à afficher
    private JButton[] tab_button = new JButton[tab_string.length];
    private JLabel ecran = new JLabel();
    private Dimension dim = new Dimension(50, 40);
    private Dimension dim2 = new Dimension(50, 31);
    private double chiffre1;
    private boolean clicOperateur = false, update = false;
    private String operateur = "";

    Calculatrice() {
        this.setSize(240, 250);
        this.setTitle("Calculette");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        //On initialise le conteneur avec tous les composants
        initComposant();
        //On ajoute le conteneur
        this.setContentPane(container);
        this.setVisible(true);
        container.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void initComposant() {
        //On définit la police d'écriture à utiliser
        Font police = new Font("Arial", Font.BOLD, 20);
        ecran = new JLabel("0");
        ecran.setForeground(Color.decode("#00b686"));
        ecran.setFont(police);
        //On aligne les informations à droite dans le JLabel
        ecran.setHorizontalAlignment(JLabel.RIGHT);
        ecran.setPreferredSize(new Dimension(220, 20));
        //on definie internal padding
        Border border = ecran.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        ecran.setBorder(new CompoundBorder(border, margin));

        JPanel operateur = new JPanel();
        operateur.setPreferredSize(new Dimension(55, 225));
        JPanel chiffre = new JPanel();
        chiffre.setPreferredSize(new Dimension(165, 225));
        JPanel panEcran = new JPanel();
        panEcran.setPreferredSize(new Dimension(220, 30));



    /* important Statement */
        setUndecorated(true);

        //On parcourt le tableau initialisé
        //afin de créer nos boutons
        for (int i = 0; i < tab_string.length; i++) {
            tab_button[i] = new JButton(tab_string[i]);
            tab_button[i].setPreferredSize(dim);
            switch (i) {
                //Pour chaque élément situé à la fin du tableau
                //et qui n'est pas un chiffre
                //on définit le comportement à avoir grâce à un listener
                case 11:
                    tab_button[i].addActionListener(new EgalListener());
                    chiffre.add(tab_button[i]);
                    break;
                case 12:
                    tab_button[i].setForeground(Color.decode("#ff1744"));
                    Map<TextAttribute, Object> attributes = new HashMap<>();
                    attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
                    tab_button[i].setFont(Font.getFont(attributes));
                    tab_button[i].addActionListener(new ResetListener());
                    operateur.add(tab_button[i]);
                    break;
                case 13:
                    tab_button[i].addActionListener(new PlusListener());
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    break;
                case 14:
                    tab_button[i].addActionListener(new MoinsListener());
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    break;
                case 15:
                    tab_button[i].addActionListener(new MultiListener());
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    break;
                case 16:
                    tab_button[i].addActionListener(new DivListener());
                    tab_button[i].setPreferredSize(dim2);
                    operateur.add(tab_button[i]);
                    break;
                default:
                    //Par défaut, ce sont les premiers éléments du tableau
                    //donc des chiffres, on affecte alors le bon listener
                    chiffre.add(tab_button[i]);
                    tab_button[i].addActionListener(new ChiffreListener());
                    break;
            }
        }
        panEcran.add(ecran);
        panEcran.setBorder(BorderFactory.createLineBorder(Color.black));
        container.add(panEcran, BorderLayout.NORTH);
        container.add(chiffre, BorderLayout.CENTER);
        container.add(operateur, BorderLayout.EAST);
    }

    //Méthode permettant d'effectuer un calcul selon l'opérateur sélectionné
    private void calcul() {
        if (operateur.equals("+")) {
            chiffre1 = chiffre1 +
                    Double.valueOf(ecran.getText());
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("-")) {
            chiffre1 = chiffre1 -
                    Double.valueOf(ecran.getText());
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("*")) {
            chiffre1 = chiffre1 *
                    Double.valueOf(ecran.getText());
            ecran.setText(String.valueOf(chiffre1));
        }
        if (operateur.equals("/")) {
            try {
                chiffre1 = chiffre1 /
                        Double.valueOf(ecran.getText());
                ecran.setText(String.valueOf(chiffre1));
            } catch (ArithmeticException e) {
                ecran.setText("0");
            }
        }
    }

    //Listener utilisé pour les chiffres
    //Permet de stocker les chiffres et de les afficher
    class ChiffreListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //On affiche le chiffre additionnel dans le label
            String str = ((JButton) e.getSource()).getText();
            if (update) {
                update = false;
            } else {
                if (!ecran.getText().equals("0"))
                    str = ecran.getText() + str;
            }
            ecran.setText(str);
        }
    }

    //Listener affecté au bouton =
    class EgalListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            calcul();
            update = true;
            clicOperateur = false;
        }
    }

    //Listener affecté au bouton +
    class PlusListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            operation();
            operateur = "+";
            update = true;
        }
    }

    private void operation() {
        if (clicOperateur) {
            calcul();
            ecran.setText(String.valueOf(chiffre1));
        } else {
            chiffre1 = Double.valueOf(ecran.getText());
            clicOperateur = true;
        }
    }

    //Listener affecté au bouton -
    class MoinsListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            operation();

            operateur = "-";
            update = true;
        }
    }

    //Listener affecté au bouton *
    class MultiListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            operation();
            operateur = "*";
            update = true;
        }
    }

    //Listener affecté au bouton /
    class DivListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            operation();
            operateur = "/";
            update = true;
        }
    }

    //Listener affecté au bouton de remise à zéro
    class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            clicOperateur = false;
            update = true;
            chiffre1 = 0;
            operateur = "";
            ecran.setText("");
        }
    }
}