package com.fp.component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SimpleCache {

    private HashMap<String, CacheItem<?>> cache = new HashMap<>();
    private static SimpleCache simpleCache;
    private final static Integer DEFAULT_EXPIRE_DURATION_MIN = 30;

    private SimpleCache(){}

    public static SimpleCache getInsance(){
        if( simpleCache == null ) simpleCache = new SimpleCache();
        return simpleCache;
    }

    public <T> T getCacheItem( String key, CacheFunctionalInterface<T> function){
        return this.<T> getCacheItem(key, function, Calendar.MINUTE, DEFAULT_EXPIRE_DURATION_MIN);
    }

    public <T> T getCacheItem( String key, CacheFunctionalInterface<T> function, Date expireDateTime){
        CacheItem<T> cacheItem = (CacheItem<T>) cache.get(key);
        if( cacheItem == null ){
            cacheItem = new CacheItem<>();
            cacheItem.setFunction(function);
            cacheItem.setExpireDateTime(expireDateTime);
            cacheItem.setLastUpdateDateTime(new Date());
            cacheItem.setCacheContent(function.execute());
            cache.put(key,cacheItem);
        } else {
            if( cacheItem.isEvicted() || cacheItem.getExpireDateTime().compareTo( new Date()) < 0 ) {
                cacheItem.setExpireDateTime(expireDateTime);
                cacheItem.setLastUpdateDateTime(new Date());
                cacheItem.setCacheContent(function.execute());
            }
        }
        return cacheItem.getCacheContent();
    }

    public <T> T getCacheItem(String key, CacheFunctionalInterface<T> function, Integer field, Integer amount ){
        Calendar expireDateTimeCalendar = Calendar.getInstance();
        expireDateTimeCalendar.add( field, amount );
        return this.<T> getCacheItem(key, function, expireDateTimeCalendar.getTime());
    }

    public void evict( String key ){
        if( cache.get(key) != null ) cache.get(key).setEvicted(true);
    }

    private static class CacheItem<T> {

        private Date expireDateTime = new Date();
        private Date lastUpdateDateTime = new Date();
        private boolean evicted = false;
        private T cacheContent;
        private CacheFunctionalInterface<T> function;

        public Date getExpireDateTime() {
            return expireDateTime;
        }

        public void setExpireDateTime(Date expireDateTime) {
            this.expireDateTime = expireDateTime;
        }

        public T getCacheContent() {
            return cacheContent;
        }

        public void setCacheContent(T cacheContent) {
            this.cacheContent = cacheContent;
        }

        public void setEvicted(boolean evicted) {
            this.evicted = evicted;
        }

        public boolean isEvicted() {
            return evicted;
        }

        public Date getLastUpdateDateTime() {
            return lastUpdateDateTime;
        }

        public void setLastUpdateDateTime(Date lastUpdateDateTime) {
            this.lastUpdateDateTime = lastUpdateDateTime;
        }

        public CacheFunctionalInterface<T> getFunction() {
            return function;
        }

        public void setFunction(CacheFunctionalInterface<T> function) {
            this.function = function;
        }
    }

    public static interface CacheFunctionalInterface<T> {
        public T execute();
    }

}
