package com.example.drugsafety.util;

import java.util.HashMap;

public class CollectionUtil {
	public static ChainedMap newChanedMap() {
		return new ChainedMap();
	}
	public static class ChainedMap<K, V> extends HashMap<K, V> {

		public ChainedMap add(K key, V value) {
			// TODO Auto-generated method stub
			super.put(key, value);
			return this;
		}

	}
}

