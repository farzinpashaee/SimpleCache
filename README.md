# SimpleCache
Simple java in memory cache

## Add to project
You can just copy the .java calss to your project (update pakage name) and it is good to go.

## Usage
The usage is pretty simple as follow:
```
SimpleCache cache = SimpleCache.getInstance();
String cachedValue = cache.cacheItem("some.key",() -> {
        return "value"; // implementation for cache value update
}, Calendar.MINUTE, 5);
String cachedValueCopy = cache.getCacheItem("some.key");
```
