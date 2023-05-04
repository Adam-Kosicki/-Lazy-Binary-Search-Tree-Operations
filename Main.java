import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    // Declare constants for commands
    private static String PRINT_TREE = "PrintTree";
    private static String HEIGHT = "Height";
    private static String SIZE = "Size";
    private static String INSERT = "Insert:";
    private static String DELETE = "Delete:";
    private static String FIND_MIN = "FindMin";
    private static String FIND_MAX = "FindMax";
    private static String CONTAINS = "Contains:";

    // Method to convert camel case to title case
    static String camelCase(boolean camel) {
        String result = String.valueOf(camel);
        return Character.toUpperCase(result.charAt(0)) + result.substring(1);
    }

    public static void main(String[] args) {

        // Check if two arguments are provided by the user
        if (args.length == 2) {

            // Declare necessary variables
            Scanner inputFile = null; // Used to read input file
            PrintWriter outputFile = null; // Used to write output file
            File outFile = new File(args[1]); // Output file path

            try {
                // Open input file via command-line arguments
                inputFile = new Scanner(new File(args[0]));

                // Create output file if it doesn't exist
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }

                // Open output file for writing
                outputFile = new PrintWriter(outFile);

                // Create LazyBinarySearchTree object
                LazyBinarySearchTree tree = new LazyBinarySearchTree();

                // Loop through each line in the input file
                while (inputFile.hasNextLine()) {

                    String line = inputFile.nextLine().trim(); // Read line and remove whitespace

                    // Check operation type and perform necessary action
                    if (line.indexOf(INSERT) == 0) { // INSERT
                        int key = Integer.parseInt(line.substring(line.indexOf(INSERT) + INSERT.length())); // Extract key from line
                        try {
                            outputFile.println(camelCase(tree.insert(key))); // Insert key into tree and write result to output file
                        } catch (IllegalArgumentException iae) {
                            outputFile.println(iae.getMessage()); // Write error message to output file if key is invalid
                        }

                    } else if (line.indexOf(DELETE) == 0) { // DELETE
                        int key = Integer.parseInt(line.substring(line.indexOf(DELETE) + DELETE.length())); // Extract key from line
                        try {
                            outputFile.println(camelCase(tree.delete(key))); // Delete key from tree and write result to output file
                        } catch (IllegalArgumentException iae) {
                            outputFile.println(iae.getMessage()); // Write error message to output file if key is invalid
                        }

                    } else if (line.indexOf(PRINT_TREE) == 0) { // PRINT_TREE
                        outputFile.println(tree); // Write tree to output file

                    } else if (line.indexOf(HEIGHT) == 0) { // HEIGHT
                        outputFile.println(tree.height()); // Write tree height to output file

                    } else if (line.indexOf(SIZE) == 0) { // SIZE
                        outputFile.println(tree.size()); // Write tree size to output file

                    } else if (line.indexOf(FIND_MIN) == 0) { // FIND_MIN
                        outputFile.println(tree.findMin()); // Write minimum value in tree to output file

                    } else if (line.indexOf(FIND_MAX) == 0) { // FIND_MAX
                        outputFile.println(tree.findMax()); // Write maximum value in tree to output file

                    } else if (line.indexOf(CONTAINS) == 0) { // CONTAINS
                        int key = Integer.parseInt(line.substring(line.indexOf(CONTAINS) + CONTAINS.length())); // Extract key from line
                        try {
                            outputFile.println(camelCase(tree.contains(key))); // Write whether key is found or not in output file
                        } catch (IllegalArgumentException iae) {
                            outputFile.println(iae.getMessage()); // Write error message to output file if key is invalid
                        }

                    } else {
                        outputFile.println("Error in Line: " + line); // Write error message to output file for an invalid line
                    }
                }

                // Close input and output files
                inputFile.close();
                outputFile.close();

                // Print a message after the program successfully finishes
                System.out.println("Output has been written to file: " + args[1]);
           } 
           catch (FileNotFoundException fnfe) {
               System.out.println(fnfe.getMessage());
           } 
           catch (IOException ioe) {
               ioe.printStackTrace();
           }
       } 
       else
           System.out.println("Arguments: inputFileName outputFileName");
   }
}