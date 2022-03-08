import com.fp.component.SimpleCache;

import java.util.Calendar;

public class Main {

    public static void main(String[] args) throws InterruptedException, SimpleCache.CacheItemNotFound {

        SimpleCache cache = SimpleCache.getInstance();
        while(true) {
            String cachedValue = cache.cacheItem("some.key",() -> {
                return "value";
            }, Calendar.MINUTE, 5);
            System.out.println(cachedValue);
            Thread.sleep(1000);
            String s = cache.getCacheItem("some.key");
        }

    }
}
