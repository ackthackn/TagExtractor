import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class TagExtractor extends JFrame {
    JPanel panel, displayFilePanel, mainPanel, buttonPanel;
    JScrollPane tagScrollPane, fileScrollPane;
    JTextArea tagTextArea, fileTextArea;
    JButton quit, chooseFile, chooseNoise, extractNoise, save;
    FileChooser noiseFileChooser, fileChooser;
    Map<String, Integer> tagFrequencyMap;
    File chosenFile, noiseWordsFile;
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
        setSize(700,350);
        setTitle("Tag Extractor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        tagFrequencyMap = new HashMap<>();
    }

    public void createFileDisplayPanel() {
        displayFilePanel = new JPanel();

        fileTextArea = new JTextArea(1,45);
        fileTextArea.setEditable(false);
        fileTextArea.setFont(font);

        fileScrollPane = new JScrollPane(fileTextArea);
        fileScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        displayFilePanel.add(fileScrollPane);
    }

    public void createMainPanel(){
        mainPanel = new JPanel();

        tagTextArea = new JTextArea(10,45);
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
            setChooseFile();
        });

        chooseNoise = new JButton("Choose Noise Words File");
        chooseNoise.setFont(font);
        chooseNoise.addActionListener((ActionEvent ae) -> {
            setChooseNoise();
        });

        extractNoise = new JButton("Extract Noise");
        extractNoise.setFont(font);
        extractNoise.addActionListener((ActionEvent ae) -> {
            extractNoise();
        });

        save = new JButton("Save Tags");
        save.setFont(font);
        save.addActionListener((ActionEvent ae) -> {
            saveTags();
        });

        buttonPanel.add(chooseFile);
        buttonPanel.add(chooseNoise);
        buttonPanel.add(extractNoise);
        buttonPanel.add(save);
        buttonPanel.add(quit);
    }

    public void setChooseFile(){
        fileChooser = new FileChooser();

        chosenFile = fileChooser.file;

        fileTextArea.setText(fileChooser.file.getName());
    }

    public void setChooseNoise(){
        noiseFileChooser = new FileChooser();

        noiseWordsFile = noiseFileChooser.file;
    }

    public void extractNoise(){
        if(fileChooser == null || noiseFileChooser == null) {
            JOptionPane.showMessageDialog(this, "Select both text file and noise words file first");
        } else {
            tagFrequencyMap.clear();
            try (Scanner scanner = new Scanner(chosenFile)) {
                Set<String> stopWords = loadStopWords();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().toLowerCase().replaceAll("[^a-zA-Z\\s]", "");
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        if (!stopWords.contains(word)) {
                            tagFrequencyMap.put(word, tagFrequencyMap.getOrDefault(word, 0) + 1);
                        }
                    }
                }
                displayTags();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<String> loadStopWords(){
        Set<String> stopWords = new HashSet<>();
        try (Scanner scanner = new Scanner(noiseWordsFile)) {
            while (scanner.hasNextLine()) {
                stopWords.add(scanner.nextLine().toLowerCase());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return stopWords;
    }

    public void displayTags() {
        tagTextArea.append("\nTags and their frequencies:\n");
        for (Map.Entry<String, Integer> entry : tagFrequencyMap.entrySet()) {
            tagTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

    public void saveTags() {
        FileChooser saveFileChooser = new FileChooser();
        try (PrintWriter writer = new PrintWriter(saveFileChooser.file)) {
            for (Map.Entry<String, Integer> entry : tagFrequencyMap.entrySet()) {
                writer.println(entry.getKey() + ": " + entry.getValue());
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        JFrame frame = new TagExtractor();
    }
}