import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame okno = new JFrame("GUI");
            GridBagLayout layout = new GridBagLayout();
            okno.setLayout(layout);
            JTextArea textArea = new JTextArea(5, 20);
            JButton runButton = new JButton("Uruchom program");
            JTextArea texta = new JTextArea(20,30);
            texta.setFont(new Font("Calibri", Font.PLAIN, 20));
            

            // Utworzenie obsługi zdarzenia dla przycisku
         
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx=0;
            gbc.gridy=0;
            // Dodanie komponentów do ramki
            okno.add(textArea, gbc);
            gbc.gridy=1;
            okno.add(runButton,gbc);
          
            okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            okno.setLocationRelativeTo ( null );
            okno.setVisible(true);
            gbc.gridy=2;
            okno.add(texta, gbc);
            okno.pack();
            runButton.addActionListener(e -> {
                texta.setText("wynik: ");
                String[] inputArgs = textArea.getText().split("\\s+");
                try {
                    // Dodanie nazwy programu jako pierwszego argumentu w tablicy argumentów
                    String[] fullArgs = new String[inputArgs.length + 1];
                    fullArgs[0] = "a.exe";
                    System.arraycopy(inputArgs, 0, fullArgs, 1, inputArgs.length);
                    Process process = Runtime.getRuntime().exec(fullArgs);
        
                    InputStream inputStream = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String str= texta.getText();
                        texta.setText(str+line+"\n");
                    }
        
        
                    // Wyświetlenie komunikatów o błędach
                    InputStream errorStream = process.getErrorStream();
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.err.println("Błąd: " + errorLine);
                    }
        
                    // Czekanie na zakończenie procesu
                    process.waitFor();
        
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }); 
        });
    }
}
