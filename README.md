# SimpleCache
Simple java in memory cache

The usage is pretty simple as follow:
```
SimpleCache cache = SimpleCache.getInstance();
String cachedValue = cache.cacheItem("some.key",() -> {
        return "value"; // implementation for cache value update
}, Calendar.MINUTE, 5);
String cachedValueCopy = cache.getCacheItem("some.key");
```
