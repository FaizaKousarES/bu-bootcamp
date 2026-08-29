import java.io.*;
import java.util.ArrayList;

public class GradeAnalyzer {

    public static void main(String[] args) {
        // Step 1: Read scores from file
        String inputFile = "scores.txt";
        ArrayList<Integer> scores = readScores(inputFile);
        
        if (scores.isEmpty()) {
            System.out.println("No valid scores found to process.");
            return;
        }
        
        // Step 4: Calculate average
        double avg = calculateAverage(scores);
        
        // Step 5: Find the Highest and Lowest Scores manually
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        
        for (int score : scores) {
            if (score > highest) {
                highest = score;
            }
            if (score < lowest) {
                lowest = score;
            }
        }
        
        // Step 6: Count the Grade Bands
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        
        for (int score : scores) {
            if (score >= 90) {
                countA++;
            } else if (score >= 80) {
                countB++;
            } else if (score >= 70) {
                countC++;
            } else if (score >= 60) {
                countD++;
            } else {
                countF++;
            }
        }
        
        // Step 7: Pass all data to writeReport
        String outputFile = "report.txt";
        writeReport(scores, avg, highest, lowest, countA, countB, countC, countD, countF, outputFile);
    }

    // Step 3: Implement readScores using BufferedReader and while loop
    public static ArrayList<Integer> readScores(String filename) {
        ArrayList<Integer> scores = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Call line.trim() first to remove whitespace
                String trimmedLine = line.trim();
                
                // Skip blank lines
                if (trimmedLine.isEmpty()) {
                    continue;
                }
                
                // Use Integer.parseInt inside try-catch to parse each line
                try {
                    int score = Integer.parseInt(trimmedLine);

                    // Reject anything outside a valid 0-100 grade range
                    if (score < 0 || score > 100) {
                        System.out.println("Warning: Skipping out-of-range score -> " + trimmedLine);
                        continue;
                    }

                    scores.add(score);
                } catch (NumberFormatException e) {
                    // Print a warning and skip that line
                    System.out.println("Warning: Skipping invalid score line -> " + trimmedLine);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        
        return scores;
    }

    // Step 4: Implement calculateAverage
    public static double calculateAverage(ArrayList<Integer> scores) {
        // If the scores list is empty, return 0.0 immediately
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        
        // Accumulate total in a double variable
        double total = 0.0;
        for (int score : scores) {
            total += score;
        }
        
        // Return total divided by scores.size()
        return total / scores.size();
    }

    // Step 7: Implement writeReport using BufferedWriter and String.format()
    public static void writeReport(ArrayList<Integer> scores, double avg, int high, int low, 
                                   int countA, int countB, int countC, int countD, int countF, 
                                   String outputFile) {
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            // Format report neatly using String.format() for aligned columns
            String line1 = "========== GRADE DISTRIBUTION REPORT ==========\n";
            String line2 = String.format("Total scores processed : %d%n", scores.size());
            String line3 = String.format("Average score          : %.2f%n", avg);
            String line4 = String.format("Highest score          : %d%n", high);
            String line5 = String.format("Lowest score           : %d%n", low);
            String line6 = "-----------------------------------------------\n";
            String line7 = String.format("Grade A (90+)          : %d%n", countA);
            String line8 = String.format("Grade B (80-89)        : %d%n", countB);
            String line9 = String.format("Grade C (70-79)        : %d%n", countC);
            String line10 = String.format("Grade D (60-69)        : %d%n", countD);
            String line11 = String.format("Grade F (Below 60)     : %d%n", countF);
            
            // Build the complete report string
            String completeReport = line1 + line2 + line3 + line4 + line5 + line6 
                                  + line7 + line8 + line9 + line10 + line11;
            
            // Print the lines to the terminal using System.out.print
            System.out.print(completeReport);
            
            // Write to report.txt using the BufferedWriter
            writer.write(completeReport);
            
            System.out.println("\nReport successfully saved to " + outputFile);
            
        } catch (IOException e) {
            System.out.println("Error writing report file: " + e.getMessage());
        }
    }
}