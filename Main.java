import java.util.ArrayList;

public class Main {
    private static String inputPath = "C:\\Users\\hidir\\IdeaProjects\\Ders1\\src\\InputData.txt";
    private static String outputPath = "C:\\Users\\hidir\\IdeaProjects\\Ders1\\src\\MyData.txt";

    private static int patternID;

    public static void main(String[] args) {
        SystemManager fileManager = new SystemManager(3, inputPath, outputPath, null, null);
        SystemManager oldData = new SystemManager(1, null, null, null, null);

        ArrayList<String> inputLines = fileManager.getLines();
        for(int i = 0; i < inputLines.size(); i++) {
            patternID = SystemManager.findPattern(inputLines.get(i));
            if(patternID != 0) {
                SystemManager newData = new SystemManager(2, null, null, inputLines.get(i), patternID);
                if (!SystemManager.dateComparison(newData, oldData)) {
                    fileManager.writeLine(newData.toFinalText());
                    oldData = newData;
                }
            }
        }
        fileManager.closeIO();
    }
}