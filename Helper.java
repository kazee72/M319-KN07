package KN07.M319_KN07;

public class Helper {
    public static void ClearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void Delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
