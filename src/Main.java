import com.fp.component.SimpleCache;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        SimpleCache cache = SimpleCache.getInsance();

        while(true) {
            String c = cache.getCacheItem("some.key", Calendar.SECOND, 5, () -> {
                return "XXX";
            });
            System.out.println(c);
            Thread.sleep(1000);
        }

    }
}
