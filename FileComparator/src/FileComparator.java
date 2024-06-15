import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileComparator {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Lütfen birinci klasörü seçin:");
        String firstFolderPath = scanner.nextLine();

        System.out.println("Lütfen ikinci klasörü seçin:");
        String secondFolderPath = scanner.nextLine();

        File firstFolder = new File(firstFolderPath);
        File secondFolder = new File(secondFolderPath);

        if (!firstFolder.exists() || !firstFolder.isDirectory() ||
            !secondFolder.exists() || !secondFolder.isDirectory()) {
            System.out.println("Seçilen klasörler geçerli değil.");
            return;
        }

        final File[] firstFiles = firstFolder.listFiles();
        final File[] secondFiles;
        secondFiles = secondFolder.listFiles();

        List<String> differences = new ArrayList<>();

        for (File firstFile : firstFiles) {
            String firstFileName = firstFile.getName();
            File secondFile = new File(secondFolder, firstFileName);

            if (!secondFile.exists() || !secondFile.isFile()) {
                differences.add("İkinci klasörde " + firstFileName + " adında bir dosya bulunmuyor.");
                continue;
            }

            try {
                if (!compareFiles(firstFile, secondFile)) {
                    differences.add(firstFileName + " dosyaları farklı.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Farklar:");
        for (String difference : differences) {
            System.out.println(difference);
        }
    }

    private static boolean compareFiles(File file1, File file2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        String line1, line2;
        boolean areEqual = true;

        while ((line1 = reader1.readLine()) != null && (line2 = reader2.readLine()) != null) {
            if (!line1.equals(line2)) {
                areEqual = false;
                break;
            }
        }

        if ((line1 != null && line2 == null) || (line1 == null && line2 != null)) {
            areEqual = false;
        }

        reader1.close();
        reader2.close();

        return areEqual;
    }
}
