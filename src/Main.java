import java.io.FileNotFoundException;
import java.util.Scanner;

void main(String[] args) {
    try {
        String sonnetText = Sonnet.readSonnet("Sonnet.txt");
        Sonnet game = new Sonnet(sonnetText);

        try (Scanner scanner = new Scanner(System.in)) {
            game.play(scanner);
        }
    } catch (FileNotFoundException e) {
        System.err.println("Error: File Not Found.");
        System.exit(1);
    }
}


