import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemManager {
    private static BufferedReader reader;
    private static PrintWriter writer;
    private int dayVal;
    private int monthVal;
    private int yearVal;
    private String weekdayVal;

    SystemManager(int systemID, @Nullable String inputFilePath, @Nullable String outputFilePath, @Nullable String date, @Nullable Integer patternID) {
        if(systemID == 1) {
            dayVal = 0;
            monthVal = 0;
            yearVal = 0;
            weekdayVal = "";
        } else if(systemID == 2) {
            dateSplit(patternID, date);
        } else if(systemID == 3) {
            try {
                if(inputFilePath != null) {
                    File inputFile = new File(inputFilePath);
                    File outputFile = new File(outputFilePath);

                    FileWriter fw = new FileWriter(outputFile);
                    BufferedWriter bw = new BufferedWriter(fw);

                    writer = new PrintWriter(bw);
                    reader = new BufferedReader(new FileReader(inputFile));
                }

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getLines() {
        ArrayList<String> lines = new ArrayList<String>();
        String tempLine;
        try {
            while ((tempLine = reader.readLine()) != null) {
                lines.add(tempLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public void closeIO() {
        try{
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int findPattern (String line) {
        String[] patternList = new String[]{
                "\\d\\d/\\d\\d/\\d{4}+ \\w",
                "\\d\\d/\\d/\\d{4}+ \\w",
                "\\d{4}+-\\d\\d-\\d\\d \\w",
                "\\w \\d\\d.\\d\\d.\\d{4}+"};
        for(int patternID = 0; patternID < patternList.length; patternID++) {
            Pattern pattern = Pattern.compile(patternList[patternID]);
            Matcher matcher = pattern.matcher(line);

            boolean matchFound = matcher.find();
            if(matchFound) {
                return patternID;
            }
        }
        System.out.println("Invalid format: " + line);
        return 0;
    }

    public void dateSplit(int pos, String date) {
        if(pos == 0) {
            dayVal = Integer.parseInt(date.substring(0, 2));
            monthVal = Integer.parseInt(date.substring(3, 5));
            yearVal = Integer.parseInt(date.substring(6, 10));
            weekdayVal = date.substring(11);
        } else if(pos == 1) {
            dayVal = Integer.parseInt(date.substring(0, 2));
            monthVal = Integer.parseInt(date.substring(3, 4));
            yearVal = Integer.parseInt(date.substring(5, 9));
            weekdayVal = date.substring(10);
        } else if(pos == 2) {
            dayVal = Integer.parseInt(date.substring(8, 10));
            monthVal = Integer.parseInt(date.substring(5, 7));
            yearVal = Integer.parseInt(date.substring(0, 4));
            weekdayVal = date.substring(11);
        } else if(pos == 3) {
            String[] splittedDate = date.split(" ");
            dayVal = Integer.parseInt(splittedDate[1].substring(0, 2));
            monthVal = Integer.parseInt(splittedDate[1].substring(3, 5));
            yearVal = Integer.parseInt(splittedDate[1].substring(6, 10));
            weekdayVal = splittedDate[0];
        }
    }
    public static boolean dateComparison (SystemManager newData, SystemManager oldData) {
        return (oldData.getDay() == newData.getDay()
                && oldData.getMonth() == newData.getMonth()
                && oldData.getYear() == newData.getYear()
                && oldData.getWeekday().equals(newData.getWeekday()));
    }

    public void writeLine (String newLine) {
        writer.println(newLine);
    }

    public int getDay() {
        return dayVal;
    }

    public int getMonth() {
        return monthVal;
    }

    public int getYear() {
        return yearVal;
    }

    public String getWeekday() {
        return weekdayVal;
    }

    public String toFinalText() {
        return "day = " + dayVal + ", month = " + monthVal + ", year = " + yearVal +", weekday = " + weekdayVal +".";
    }
}
