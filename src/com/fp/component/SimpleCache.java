package com.fp.component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SimpleCache {

    private HashMap<String, CacheItem<?>> cache = new HashMap<>();
    private static SimpleCache simpleCache;

    private SimpleCache(){}

    public static SimpleCache getInsance(){
        if( simpleCache == null ) simpleCache = new SimpleCache();
        return simpleCache;
    }

    public <T> T getCacheItem( String key, Date expireDateTime, CacheFunctionalInterface<T> function ){
        CacheItem<T> cacheItem = null;
        if( cache.get(key) == null ){
            this.<T> loadCache(key, expireDateTime, function);
        } else {
            cacheItem = (CacheItem<T>) cache.get(key);
            if( cacheItem.getExpireDateTime().compareTo( new Date()) < 0 ) {
                this.<T>loadCache(key, expireDateTime, function);
            }
        }
        cacheItem = (CacheItem<T>) cache.get(key);
        return cacheItem.getCacheContent();
    }

    public <T> T getCacheItem(String key, Integer field, Integer amount, CacheFunctionalInterface<T> function ){
        Calendar expireDateTimeCalendar = Calendar.getInstance();
        expireDateTimeCalendar.add( field, amount );
        return this.<T> getCacheItem(key, expireDateTimeCalendar.getTime(), function);
    }

    public void evict( String key ){
        if( cache.get(key) != null ) cache.get(key).setEvicted(true);
    }

    private <T> void loadCache( String key, Date expireDateTime, CacheFunctionalInterface<T> function ){
        CacheItem<T> cacheItem = new CacheItem<>();
        cacheItem.setExpireDateTime(expireDateTime);
        cacheItem.setLastUpdateDateTime(new Date());
        cacheItem.setCacheContent( function.execute() );
        cache.put( key, cacheItem );
    }

    private static class CacheItem<T> {

        private Date expireDateTime = new Date();
        private Date lastUpdateDateTime = new Date();
        private boolean evicted = false;
        private T cacheContent;

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
    }

    public static interface CacheFunctionalInterface<T> {
        public T execute();
    }

}
