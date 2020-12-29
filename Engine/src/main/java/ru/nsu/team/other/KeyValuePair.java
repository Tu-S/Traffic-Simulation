package ru.nsu.team.other;

import java.util.Map;

public class KeyValuePair<K, V> implements Map.Entry<K, V> {
  private K key;
  private V value;

  public KeyValuePair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public K setKey(K key) {
    return this.key = key;
  }

  public V getValue() {
    return value;
  }

  public V setValue(V value) {
   return this.value = value;
  }
}
