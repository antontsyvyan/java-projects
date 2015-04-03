package com.foursoft.gpa.utils.cache;

import java.util.ArrayList;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;


public class MemoryCache<K, V> {
	
    public static int maxItems=15000;
    public static long timeToLiveSec=120; 
    private static long timeToLive=timeToLiveSec * 1000;
       
    private static LRUMap cacheMap = new LRUMap(maxItems);
    
    private static MemoryCache<Object, Object> singletonInstance = null;
    
	public MemoryCache() {	       
	}
	
	public static MemoryCache<Object, Object> getMemoryCacheSingletonInstance() {
		if (singletonInstance == null) {
			singletonInstance = new MemoryCache<Object, Object>();
		}
		return singletonInstance;
	}

	 
	public void put(K key, CacheObject<V> value) {
		synchronized (cacheMap) {
			cacheMap.put(key, value);
		}
	}
	
	
	@SuppressWarnings("unchecked")
    public CacheObject<V> get(K key) {
        synchronized (cacheMap) {
            CacheObject<V> c = (CacheObject<V>) cacheMap.get(key);
            if(c!=null){
            	c.lastAccessed=System.currentTimeMillis();
            	c.numberUsed++;
            }          
            return c;
        }
    }
	public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }
 
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    @SuppressWarnings("unchecked")
    public void cleanup() {
 
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;
 
        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();         
            deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
            K key = null;
            CacheObject<V> c = null;
 
            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (CacheObject<V>) itr.getValue();
                
                //System.out.println("Cache :"+key+" "+c.lastAccessed + " > used: "+c.numberUsed);
 
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }
 
        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
 
            Thread.yield();
        }
        
        
        //System.out.println("Cache size="+cacheMap.size());
        
    }
    
    public void cleanupNow() {
    	synchronized (cacheMap) {
    		if(cacheMap!=null && cacheMap.size()>0){
    			cacheMap.clear();
    		}
    	}
    }

}
