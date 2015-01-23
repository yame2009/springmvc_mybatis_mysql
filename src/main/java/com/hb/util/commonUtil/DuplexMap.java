package com.hb.util.commonUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 双向MAP 必须保证K和V都为一. 可以通过K找到V,也能通过V找到K
 * 
 * @author Administrator
 */
public class DuplexMap<K, V>
{
    class Entry
    {
        K k;
        V v;

        public Entry(K k, V v)
        {
            this.k = k;
            this.v = v;
        }

        public K getK()
        {
            return k;
        }

        public V getV()
        {
            return v;
        }

        public void setK(K k)
        {
            this.k = k;
        }

        public void setV(V v)
        {
            this.v = v;
        }
    }

    private final HashMap<K, Entry> kEntyMap = new HashMap<K, Entry>();
    private final HashMap<V, Entry> vEntyMap = new HashMap<V, Entry>();

    public boolean contains(K k)
    {
        return kEntyMap.containsKey(k);
    }

    public boolean containsValue(V v)
    {
        return vEntyMap.containsKey(v);
    }

    public V get(K k)
    {
        Entry e = kEntyMap.get(k);
        if (e == null)
        {
            return null;
        }
        return e.getV();
    }

    public K getbyValue(V v)
    {
        Entry e = vEntyMap.get(v);
        if (e == null)
        {
            return null;
        }
        return e.getK();
    }

    public boolean put(K k, V v)
    {
        if (k == null || v == null)
        {
            return false;
        }
        Entry e = new Entry(k, v);
        if (contains(k))
        {
            remove(k);
        }
        if (containsValue(v))
        {
            removeByValue(v);
        }
        kEntyMap.put(k, e);
        vEntyMap.put(v, e);
        return true;
    }

    public V remove(K k)
    {
        Entry e = kEntyMap.remove(k);
        if (e == null)
        {
            return null;
        }
        vEntyMap.remove(e.getV());
        return e.getV();
    }

    public K removeByValue(V v)
    {
        Entry e = vEntyMap.remove(v);
        if (e == null)
        {
            return null;
        }
        kEntyMap.remove(e.getK());
        return e.getK();
    }

    public int size()
    {
        return kEntyMap.size();
    }

    /**
     * 获得Map
     * 
     * @return 2014年7月29日
     */
    public HashMap<K, V> getEntyMap()
    {
        HashMap<K, V> newMap = new HashMap<K, V>();

        Collection<DuplexMap<K, V>.Entry> values = kEntyMap.values();
        if (values == null || values.isEmpty())
        {
            return newMap;
        }

        Iterator<DuplexMap<K, V>.Entry> iterator = values.iterator();
        if (iterator == null)
        {
            return newMap;
        }

        while (iterator.hasNext())
        {
            Entry obj = iterator.next();
            if (obj.getK() == null || "".equals(String.valueOf(obj.getK()))
                    || "null".equals(String.valueOf(obj.getK())))
            {
                continue;
            }
            newMap.put(obj.getK(), obj.getV());
        }

        return newMap;
    }

}
