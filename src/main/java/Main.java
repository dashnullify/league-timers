import me.brennan.timers.Timers;

/**
 * made for LOLTimers
 *
 * @author Brennan
 * @since 5/10/2020
 **/
public class Main {

    public static void main(String[] args) {
        try {
            Timers.INSTANCE.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
