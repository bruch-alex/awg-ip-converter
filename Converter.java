import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    private static final Pattern ip = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}(/[0-9]{1,3})?");

    public static void main(String[] args) throws IOException {
        File folder = new File("./ranges_raw/");
        File[] listOfFiles = folder.listFiles();

        for (var file : listOfFiles) {
            convertFile(file);
        }
        System.out.println("Done");
    }

    private static void convertFile(File file) throws IOException {
        String input = Files.readString(file.toPath());
        StringBuilder result = new StringBuilder();

        Matcher matcher = ip.matcher(input);

        result.append("[\n");
        while (matcher.find()) {
            result.append("{\"hostname\": \"").append(matcher.group()).append("\",\"ip\":\"\"},\n");
        }
        result.deleteCharAt(result.length() - 2).append(']'); // delete last coma and add ]

        try (FileWriter output = new FileWriter("./ranges_awg/" + file.getName().replace(".txt", ".json"))) {
            output.write(result.toString());
        }
    }

}