import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FileChooser {
    public File file;
    public FileReader fileReader;
    public BufferedReader buffer;
    public JFileChooser chooser;
    public FileChooser() {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            int response = chooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                file = new File(chooser.getSelectedFile().getAbsolutePath());
            }
    }
}
