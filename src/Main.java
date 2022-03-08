import com.fp.component.SimpleCache;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        SimpleCache cache = SimpleCache.getInsance();
        while(true) {
            String cachedValue = cache.getCacheItem("some.key",() -> {
                return "value";
            }, Calendar.SECOND, 5);
            System.out.println(cachedValue);
            Thread.sleep(1000);
        }

    }
}
