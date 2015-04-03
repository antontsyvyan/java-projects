package com.foursoft.gpa.utils.cache;

public class CacheObject<V> {
	public long lastAccessed = System.currentTimeMillis();
	public long numberUsed=0;
	public V value;
	
	public CacheObject(V value) {
        this.value = value;
    }

	public V getValue() {
		return value;
	}
	
	
}
