import java.io.*;
import java.util.StringTokenizer;

public class Interface {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please specify an input file.");
            return;
        }

        // Initialize the dictionary and input reader
        BSTDictionary dictionary = new BSTDictionary();
        StringReader keyboard = new StringReader();

        // Load records from the input file
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            loadRecords(br, dictionary);
        } catch (IOException e) {
            System.out.println("Error reading the input file: " + e.getMessage());
            return;
        }

        String command;
        while (true) {
            command = keyboard.read("Enter next command: ");
            if (command.equals("exit")) {
                break;
            } else {
                processCommand(command, dictionary);
            }
        }
    }

    // Loads records from BufferedReader and inserts into dictionary
    private static void loadRecords(BufferedReader br, BSTDictionary dictionary) throws IOException {
        String label;
        while ((label = br.readLine()) != null) {
            label = label.toLowerCase();
            String line = br.readLine();
            if (line == null) break;

            int type;
            String data;

            // Determine type based on the first character of the line
            if (line.startsWith("-")) {
                type = 3;  // Sound file
                data = line.substring(1).trim();
            } else if (line.startsWith("+")) {
                type = 4;  // Music file
                data = line.substring(1).trim();
            } else if (line.startsWith("*")) {
                type = 5;  // Voice file
                data = line.substring(1).trim();
            } else if (line.startsWith("/")) {
                type = 2;  // Translation
                data = line.substring(1).trim();
            } else {
                data = line.trim();
                if (data.endsWith(".gif")) {
                    type = 7;  // Animated image
                } else if (data.endsWith(".jpg")) {
                    type = 6;  // Image
                } else if (data.endsWith(".html")) {
                    type = 8;  // Webpage
                } else {
                    type = 1;  // Regular dictionary entry
                }
            }

            Record record = new Record(new Key(label, type), data);
            try {
                dictionary.put(record);
            } catch (DictionaryException e) {
                System.out.println("Duplicate record for label: " + label + " with type: " + type);
            }
        }
    }


    // Processes user commands
    private static void processCommand(String command, BSTDictionary dictionary) {
        StringTokenizer tokenizer = new StringTokenizer(command);
        String cmd = tokenizer.nextToken();

        try {
            switch (cmd) {
                case "define":
                    handleDefine(tokenizer, dictionary);
                    break;
                case "translate":
                    handleTranslate(tokenizer, dictionary);
                    break;
                case "sound":
                    handleSound(tokenizer, dictionary);
                    break;
                case "play":
                    handlePlay(tokenizer, dictionary);
                    break;
                case "say":
                    handleSay(tokenizer, dictionary);
                    break;
                case "show":
                    handleShow(tokenizer, dictionary);
                    break;
                case "animate":
                    handleAnimate(tokenizer, dictionary);
                    break;
                case "browse":
                    handleBrowse(tokenizer, dictionary);
                    break;
                case "delete":
                    handleDelete(tokenizer, dictionary);
                    break;
                case "add":
                    handleAdd(tokenizer, dictionary);
                    break;
                case "list":
                    handleList(tokenizer, dictionary);
                    break;
                case "first":
                    handleFirst(dictionary);
                    break;
                case "last":
                    handleLast(dictionary);
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }
    }

    // Handles "define" command
    private static void handleDefine(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        // First try to get a regular definition (type 1)
        Record record = dictionary.get(new Key(word, 1));
        if (record != null) {
            System.out.println(record.getDataItem());
        } else {
            // If not found, try getting a translation (type 2)
            record = dictionary.get(new Key(word, 2));
            if (record != null) {
                System.out.println(record.getDataItem());
            } else {
                System.out.println("The word " + word + " is not in the dictionary.");
            }
        }
    }



    // Looks up translation of a word
    private static void handleTranslate(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 2));
        if (record != null) {
            System.out.println(record.getDataItem());
        } else {
            System.out.println("There is no definition for the word " + word);
        }
    }

    // Plays sound file
    private static void handleSound(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 3)); // Sound file type
        if (record != null) {
            String soundFile = record.getDataItem();

            // Check if it's a valid sound file path
            if (soundFile.startsWith("-")) {
                soundFile = soundFile.substring(1).trim();
            }

            // Check if the file exists
            File file = new File(soundFile);
            if (!file.exists()) {
                System.out.println("The sound file " + soundFile + " does not exist.");
                return;
            }

            try {
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.play(soundFile);  // Play the sound from the correct path
            } catch (MultimediaException e) {
                System.out.println("Error playing sound file: " + e.getMessage());
            }
        } else {
            System.out.println("There is no sound file for " + word);
        }
    }


    // Plays music file
    private static void handlePlay(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 4));
        if (record != null) {
            try {
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.play(record.getDataItem());
            } catch (MultimediaException e) {
                System.out.println("Error playing music file: " + e.getMessage());
            }
        } else {
            System.out.println("There is no music file for " + word);
        }
    }

    // Plays voice file
    private static void handleSay(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 5));
        if (record != null) {
            try {
                SoundPlayer soundPlayer = new SoundPlayer();
                soundPlayer.play(record.getDataItem());
            } catch (MultimediaException e) {
                System.out.println("Error playing voice file: " + e.getMessage());
            }
        } else {
            System.out.println("There is no voice file for " + word);
        }
    }

    // Displays an image
    private static void handleShow(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 6));
        if (record != null) {
            try {
                PictureViewer pictureViewer = new PictureViewer();
                pictureViewer.show(record.getDataItem());
            } catch (MultimediaException e) {
                System.out.println("Error displaying image file: " + e.getMessage());
            }
        } else {
            System.out.println("There is no image file for " + word);
        }
    }

    // Displays animated image
    private static void handleAnimate(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 7));
        if (record != null) {
            try {
                PictureViewer pictureViewer = new PictureViewer();
                pictureViewer.show(record.getDataItem());
            } catch (MultimediaException e) {
                System.out.println("Error displaying animated image file: " + e.getMessage());
            }
        } else {
            System.out.println("There is no animated image file for " + word);
        }
    }

    // Opens a webpage
    private static void handleBrowse(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        Record record = dictionary.get(new Key(word, 8));

        if (record != null) {
            ShowHTML showHTML = new ShowHTML();
            showHTML.show(record.getDataItem()); // Display the webpage without try-catch
        } else {
            System.out.println("There is no webpage called " + word);
        }
    }

    // Deletes record based on word or type
    private static void handleDelete(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        int type = Integer.parseInt(tokenizer.nextToken());
        try {
            dictionary.remove(new Key(word, type));
            System.out.println("Record with key (" + word + ", " + type + ") deleted.");
        } catch (DictionaryException e) {
            System.out.println("No record in the ordered dictionary has key (" + word + ", " + type + ")");
        }
    }

    // Adds new reord
    private static void handleAdd(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String word = tokenizer.nextToken().toLowerCase();
        int type = Integer.parseInt(tokenizer.nextToken());
        String data = tokenizer.nextToken();
        try {
            dictionary.put(new Record(new Key(word, type), data));
            System.out.println("Record added: (" + word + ", " + type + ") -> " + data);
        } catch (DictionaryException e) {
            System.out.println(
                    "A record with the given key (" + word + ", " + type + ") is already in the ordered dictionary");
        }
    }

    // Lists all records that start with certain prefix
    private static void handleList(StringTokenizer tokenizer, BSTDictionary dictionary) {
        String prefix = tokenizer.nextToken().toLowerCase();  // Ensure the prefix is converted to lowercase
        boolean found = false;

        // Iterate over all records in the dictionary
        for (Record record : dictionary.list()) {
            if (record.getKey().getLabel().startsWith(prefix)) {
                System.out.println(record.getKey().getLabel());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No label attributes in the dictionary start with prefix " + prefix);
        }
    }


    // Displays record wth smallest key
    private static void handleFirst(BSTDictionary dictionary) {
        Record smallest = dictionary.smallest();
        if (smallest != null) {
            System.out.println(
                    smallest.getKey().getLabel() + ", " + smallest.getKey().getType() + ", " + smallest.getDataItem());
        } else {
            System.out.println("The dictionary is empty.");
        }
    }

    // Displays record wth largest key
    private static void handleLast(BSTDictionary dictionary) {
        Record largest = dictionary.largest();
        if (largest != null) {
            System.out.println(
                    largest.getKey().getLabel() + ", " + largest.getKey().getType() + ", " + largest.getDataItem());
        } else {
            System.out.println("The dictionary is empty.");
        }
    }
}
