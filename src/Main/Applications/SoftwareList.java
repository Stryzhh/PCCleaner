package Main.Applications;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SoftwareList implements Map<String, Software> {

    private final Map<String, Software> list = new HashMap<>();

    @Override
    public Software put(String key, Software newSoft) {
        Software software = list.get("key");

        if (software != null) {

            if (software.getIcon() == null && newSoft.getIcon() != null) {
                software.setIcon(newSoft.getIcon());
            }
            if (software.getLocation() == null && newSoft.getLocation() != null) {
                software.setLocation(newSoft.getLocation());
            }
            if (software.getVersion() == null && newSoft.getVersion() != null) {
                software.setVersion(newSoft.getVersion());
            }

            software.addRegKeys(newSoft.getRegKeys());
            return software;
        }

        return list.put(key, newSoft);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return list.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return list.containsValue(value);
    }

    @Override
    public Software get(Object key) {
        return list.get(key);
    }

    @Override
    public Software remove(Object key) {
        return list.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Software> m) {
        list.putAll(m);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Set<String> keySet() {
        return list.keySet();
    }

    @Override
    public Collection<Software> values() {
        return list.values();
    }

    @Override
    public Set<Entry<String, Software>> entrySet() {
        return list.entrySet();
    }

}
