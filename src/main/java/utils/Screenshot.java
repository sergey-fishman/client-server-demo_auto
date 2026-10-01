package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {
    public static void main(String[] args) {
        createFilename();
    }

    private static String createFilename() {
        SimpleDateFormat formatter = new SimpleDateFormat
                ("yyyy-MM-dd_HH-mm-ss");
        String currentDate = formatter.format(new Date());
        return "src/test/resources/screenshots/screen-"
                + currentDate + ".png";
    }

    public static void takeScreenshot(TakesScreenshot screenshot){
        String filename = createFilename();
        File screen = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Path targetPath = Paths.get(filename);

            // создаем папки, если их нет
            if (targetPath.getParent() != null)
                Files.createDirectories(targetPath.getParent());

            Files.copy(screen.toPath(), targetPath);
            System.out.println("Screenshot saved: "
                    + filename);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save a screenshot: "
                    + filename, e);
        }
    }
}
