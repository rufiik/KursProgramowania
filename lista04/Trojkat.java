import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

class window extends WindowAdapter {
    public void windowClosing(WindowEvent e) { System.exit(0); }
  }
  class MojException extends Exception {};
public class Trojkat {
  
    public static void main(String[] args) {

        Frame okno = new Frame("Trojkat Pascala");
        TextField textField = new TextField();
        Button button = new Button("Stworz");
        textField.setPreferredSize(new Dimension(50,20));
        okno.add(textField);
        okno.add(button);
        okno.setBounds(100,100,640,480);
        GridBagLayout layout = new GridBagLayout();
        okno.setLayout(layout);
        okno.setVisible(true);
        okno.setLocationRelativeTo ( null );
        okno.addWindowListener(new window());
        Button backButton = new Button("Cofnij");
        okno.setBackground(Color.LIGHT_GRAY);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okno.removeAll(); // Usuń wszystkie istniejące komponenty
                okno.add(textField);
                okno.add(button);
                okno.setBounds(100,100,640,480);
                okno.setLocationRelativeTo ( null );
                okno.validate(); // Aktualizuj okno
                okno.repaint();
            }
            });
AtomicInteger n = new AtomicInteger(0);
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        String text = textField.getText().trim();
        try {
            n.set(Integer.parseInt(text));
            if (n.get() < 0) {
                throw new MojException();
            }
            okno.removeAll();
            GridBagConstraints gbc = new GridBagConstraints();
            for(int i=0; i<=n.get(); i++){
                int t=1;
                for(int j=0; j<=i; j++){
                    gbc.gridx = (j*2 + (n.get()-i));
                    gbc.gridy = i;
                    String element = String.valueOf(t);
                    Label label = new Label(element);
                    label.setForeground(Color.BLUE);
                    label.setFont(new Font("Calibri", Font.PLAIN, 15));
                    t=t*(i-j)/(j+1);
                    okno.add(label, gbc);
                }
            }
            gbc.gridx = n.get();
            gbc.gridy = n.get() + 1; 
            okno.add(backButton, gbc);
             okno.pack();
            okno.setLocationRelativeTo ( null );
            okno.validate(); 
            okno.repaint();
        } catch (NumberFormatException | MojException ex) {
            textField.setText("Podaj liczbe calkowita nieujemna");
        }
    }
});







      }
}