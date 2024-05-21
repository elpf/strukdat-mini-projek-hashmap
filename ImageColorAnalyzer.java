import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ImageColorAnalyzer {
    private final HashMap<Integer, Integer> colorFrequencyMap;

    public ImageColorAnalyzer() {
        this.colorFrequencyMap = new HashMap<>();
    }

    public void readAndProcessImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        BufferedImage image = ImageIO.read(file);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                int color = image.getRGB(x, y) & 0xFFFFFF;

                colorFrequencyMap.put(color, colorFrequencyMap.getOrDefault(color, 0) + 1);
            }
        }
    }

    public int getDominantColor() {
        int dominantColor = 0;
        int maxFrequency = 0;

        for (Map.Entry<Integer, Integer> entry : colorFrequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                dominantColor = entry.getKey();
            }
        }

        return dominantColor;
    }

    public int getMaxFrequency() {
        int maxFrequency = 0;

        for (int frequency : colorFrequencyMap.values()) {
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
            }
        }

        return maxFrequency;
    }

    public void displayResults() {
        int dominantColor = getDominantColor();
        int maxFrequency = getMaxFrequency();

        System.out.printf("Warna dominan pada gambar adalah: #%06X%n", dominantColor);
        System.out.printf("Jumlah frekuensi warna #%06X: %d Pixel%n", dominantColor, maxFrequency);
    }

    public static void main(String[] args) {
        ImageColorAnalyzer colorAnalyzer = new ImageColorAnalyzer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selamat datang di Program Deteksi Warna Dominan pada Gambar!\n");
        System.out.print("Masukkan path gambar: ");
        String imagePath = scanner.nextLine();

        try {
            colorAnalyzer.readAndProcessImage(imagePath);
            colorAnalyzer.displayResults();
        } catch (IOException e) {
            System.err.println("Gagal memuat gambar. Pastikan path yang dimasukkan benar.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}