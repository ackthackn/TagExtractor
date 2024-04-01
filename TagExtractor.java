import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TagExtractor extends JFrame {
    JPanel panel, displayFilePanel, mainPanel, buttonPanel;
    JScrollPane tagScrollPane, fileScrollPane;
    JTextArea tagTextArea, fileTextArea;
    JButton quit, chooseFile;

    Font font = new Font("Bookman Old Style", Font.PLAIN, 14);
    public TagExtractor() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        createFileDisplayPanel();
        panel.add(displayFilePanel, BorderLayout.NORTH);

        createMainPanel();
        panel.add(mainPanel, BorderLayout.CENTER);

        createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setSize(600,350);
        setTitle("Tag Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createFileDisplayPanel() {
        displayFilePanel = new JPanel();

        fileTextArea = new JTextArea(1,40);
        fileTextArea.setEditable(false);
        fileTextArea.setFont(font);

        fileScrollPane = new JScrollPane(fileTextArea);
        fileScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        displayFilePanel.add(fileScrollPane);
    }

    public void createMainPanel(){
        mainPanel = new JPanel();

        tagTextArea = new JTextArea(10,40);
        tagTextArea.setEditable(false);
        tagTextArea.setFont(font);

        tagScrollPane = new JScrollPane(tagTextArea);
        tagScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(tagScrollPane);
    }

    public void createButtonPanel(){
        buttonPanel = new JPanel();

        quit = new JButton("Quit");
        quit.setFont(font);
        quit.addActionListener((ActionEvent ae) -> {
            System.exit(0);
        });

        chooseFile = new JButton("Choose File");
        chooseFile.setFont(font);
        chooseFile.addActionListener((ActionEvent ae) -> {
            FileChooser fileChooser = new FileChooser();

            fileTextArea.setText(fileChooser.file.getName());
            System.out.println(fileChooser.fileLines);
        });

        buttonPanel.add(chooseFile);
        buttonPanel.add(quit);
    }
    public static void main(String[] args){
        JFrame frame = new TagExtractor();
    }
}