/**
 * implelemts database interface
 * @author:Zack Hudgins
 */
import java.util.Collection;

public interface DB<K,V> {
    public V addValue(V value);
    public V getValue(K key);
    public boolean hasKey(K key);
    public Collection<V> getAllValues();
}
