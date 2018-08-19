/*
Copyright 2018 github.com/BrokenEarthDev // gitlab.com/BrokenEarth
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
 */
package me.brokenearthdev.eventbus.builders;

import me.brokenearthdev.eventbus.EventBus;
import me.brokenearthdev.eventbus.EventBusOptions;
import me.brokenearthdev.eventbus.utils.EventListenerException;
import me.brokenearthdev.eventbus.utils.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link EventBus} and
 * is responsible for registering, unregistering, and triggering events
 *
 * @param <T> The event class. More information at {@link EventBus}
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 2.0
 * @since 1.0
 */
public final class EventBusBuilder<T> implements EventBus<T> {

    private static EventBusOptions defaultOptions = new EventBusOptionsBuilder()
            .setRestrictedToAnnotation(SubscribeEvent.class)
            .setListenerException(new EventListenerException("one of the event listeners had an uncaught exception"));

    /**
     * The list of registered event listeners
     */
    private List<Object> events = new ArrayList<>();
    /**
     * The {@link EventBus}'s options
     */
    private EventBusOptions options = defaultOptions;
    private List<Class<?>> cancelled = new ArrayList<>();

    @Override
    public EventBus register(Object eventListener) {
        events.add(eventListener);
        return this;
    }

    @Override
    public EventBus unregister(Object eventListener) {
        events.remove(eventListener);
        return this;
    }

    @Override
    public EventBus callEvent(T event) {
        if (isCancelledEvent(event.getClass())) return this;
        Trigger<T> trigger = new Trigger<>(this);
        trigger.trigger(event);
        return this;
    }

    @Override
    public EventBusOptions getOptions() {
        return options;
    }

    @Override
    public List<Object> getRegisteredEvents() {
        return events;
    }

    @Override
    public EventBusOptions getDefaultOptions() {
        return defaultOptions;
    }

    @Override
    public EventBus cancelEvent(Class<? extends T> event) {
        if (!isCancellable(event)) throw new IllegalArgumentException("event is not cancellable");
        cancelled.add(event);
        return this;
    }

    @Override
    public EventBus unCancelEvent(Class<? extends T> event) {
        if (!isCancellable(event)) throw new IllegalArgumentException("event is not cancellable");
        cancelled.remove(event);
        return this;
    }

    @Override
    public boolean isCancelled(Class<? extends T> event) {
        return isCancelledEvent(event);
    }

    private boolean isCancelledEvent(Class<?> event) {
        return cancelled.contains(event) || event.getSuperclass() != null && isCancelledEvent(event.getSuperclass());
    }

    List<Class<?>> getCancelledEvents() {
        return cancelled;
    }

}
