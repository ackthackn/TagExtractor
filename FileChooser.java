import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class FileChooser {
    public File file;
    public FileReader fileReader;
    public BufferedReader buffer;
    public ArrayList<String> fileLines = new ArrayList<String>();
    public FileChooser() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            int response = chooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(chooser.getSelectedFile().getAbsolutePath());

                fileReader = new FileReader(file);
                buffer = new BufferedReader(fileReader);

                String lines;
                while ((lines = buffer.readLine()) != null) {
                    fileLines.add(lines);
                }

                buffer.close();
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
