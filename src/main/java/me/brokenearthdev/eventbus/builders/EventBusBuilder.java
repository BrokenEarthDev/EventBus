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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the {@link EventBus} and
 * is responsible for registering, unregistering, and triggering events
 *
 * @param <T> The event class. More information at {@link EventBus}
 * @param <L> The event listener event class. More information at {@link EventBus}
 */
public final class EventBusBuilder<T, L> implements EventBus<T, L> {
    /**
     * The list of registered event listeners
     */
    private List<L> events = new ArrayList<>();
    /**
     * The {@link EventBus}'s options
     */
    private EventBusOptions<L> options = new EventBusOptionsBuilder<>();

    @Override
    public void register(L eventListener) {
        events.add(eventListener);
    }

    @Override
    public void unregister(L eventListener) {
        events.remove(eventListener);
    }

    @Override
    public void trigger(T event) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Trigger<T, L> trigger = new Trigger<>(this);
        trigger.trigger(event);
        EventBus e = new EventBusBuilder();

    }

    @Override
    public EventBusOptions<L> getOptions() {
        return options;
    }

    @Override
    public List<L>  getRegisteredEvents() {
        return events;
    }
}
