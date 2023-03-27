package com.ordis.base;

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

public class Property<T> {
    @Nonnull
    private final Class<T> type;

    @Nonnull
    private T value;

    public Property(@Nonnull Class<T> type, @Nonnull T value) {
        this.type = type;
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public boolean setUncheckedValue(Object obj) {
        if (type.isInstance(obj)) {
            T cast = type.cast(obj);
            if (cast != null)
                return setValue(cast);
        }
        return false;
    }

    public boolean setValue(@Nonnull T newValue) {
        T oldValue = this.value;
        if (oldValue != newValue) {
            this.value = newValue;
            fireValueChangedEvent(oldValue, newValue);
            return true;
        }
        return false;
    }

    @Nonnull
    public T getValue() {
        return value;
    }

    private PropertyEventHandler<T> handler;

    public void subscribe(PropertyEventListener<T> listener) {
        getEventHandler().getListeners().add(listener);
    }

    protected void fireValueChangedEvent(T oldValue, T newValue) {
        if (handler != null)
            getEventHandler().onChangedWith(this, oldValue, newValue);
    }

    public PropertyEventHandler<T> getEventHandler() {
        if (handler == null)
            return handler = new PropertyEventHandler<>();
        else
            return handler;
    }

    public static interface PropertyEventListener<T> extends EventListener {
        void onChanged(Property<T> property, T oldValue, T newValue);
    }

    public static class PropertyEventHandler<T> {
        private final Set<PropertyEventListener<T>> listeners = new HashSet<>();

        protected void onChangedWith(Property<T> property, T oldValue, T newValue) {
            for (PropertyEventListener<T> listener : listeners)
                listener.onChanged(property, oldValue, newValue);
        }

        public Set<PropertyEventListener<T>> getListeners() {
            return listeners;
        }
    }
}
