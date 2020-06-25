package org.endertools.bungeeauth.command;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author TheRealDomm
 * @since 24.06.20
 */
public class SubCommandPool extends HashMap<String, ISubCommand> {

    public SubCommandPool(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SubCommandPool(int initialCapacity) {
        super(initialCapacity);
    }

    public SubCommandPool() {
        super();
    }

    public SubCommandPool(Map<? extends String, ? extends ISubCommand> m) {
        super(m);
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public ISubCommand get(Object key) {
        return super.get(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public ISubCommand put(String key, ISubCommand value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends ISubCommand> m) {
        super.putAll(m);
    }

    @Override
    public ISubCommand remove(Object key) {
        return super.remove(key);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    @Override
    public Set<String> keySet() {
        return super.keySet();
    }

    @Override
    public Collection<ISubCommand> values() {
        return super.values();
    }

    @Override
    public Set<Entry<String, ISubCommand>> entrySet() {
        return super.entrySet();
    }

    @Override
    public ISubCommand getOrDefault(Object key, ISubCommand defaultValue) {
        return super.getOrDefault(key, defaultValue);
    }

    @Override
    public ISubCommand putIfAbsent(String key, ISubCommand value) {
        return super.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return super.remove(key, value);
    }

    @Override
    public boolean replace(String key, ISubCommand oldValue, ISubCommand newValue) {
        return super.replace(key, oldValue, newValue);
    }

    @Override
    public ISubCommand replace(String key, ISubCommand value) {
        return super.replace(key, value);
    }

    @Override
    public ISubCommand computeIfAbsent(String key, Function<? super String, ? extends ISubCommand> mappingFunction) {
        return super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public ISubCommand computeIfPresent(String key, BiFunction<? super String, ? super ISubCommand, ? extends ISubCommand> remappingFunction) {
        return super.computeIfPresent(key, remappingFunction);
    }

    @Override
    public ISubCommand compute(String key, BiFunction<? super String, ? super ISubCommand, ? extends ISubCommand> remappingFunction) {
        return super.compute(key, remappingFunction);
    }

    @Override
    public ISubCommand merge(String key, ISubCommand value, BiFunction<? super ISubCommand, ? super ISubCommand, ? extends ISubCommand> remappingFunction) {
        return super.merge(key, value, remappingFunction);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super ISubCommand> action) {
        super.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super ISubCommand, ? extends ISubCommand> function) {
        super.replaceAll(function);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
