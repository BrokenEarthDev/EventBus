/*
 * Copyright 2018 github.com/BrokenEarthDev
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package me.brokenearthdev.eventbus.entities;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

/**
 * This class contains a list of cancelled events, registered
 * listeners and the {@link EventBus}'s event annotation
 *
 * @param <T> The type parameter of {@link EventBus}.
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 * @see EventBus
 */
public class ListenerList<T> {

    /**
     * The {@link EventBus} initialized by the constructors. Used
     * for getting cancelled events, registered listeners, and
     * event method annotations
     */
    private EventBus<T> bus;

    /**
     * This constructor requires an {@link EventBus} to get
     * the cancelled events, registered listeners, and its event method
     * annotation
     *
     * @param bus The {@link EventBus} required. If
     *            the specified {@link EventBus} is null,
     *            {@link NullPointerException} is thrown
     */
    public ListenerList(EventBus<T> bus) {
        Objects.requireNonNull(bus, "Parameter(s) can't be null");
        this.bus = bus;
    }

    /**
     * @return The cancelled events in the initialized {@link EventBus}
     */
    public List<Class<?>> getCancelledEvents() {
        return bus.cancelled;
    }

    /**
     * @return The registered listeners in the initialized {@link EventBus}
     */
    public List<Object> getRegisteredListeners() {
        return bus.registered;
    }

    /**
     * @return The event method annotation in the initialized {@link EventBus}
     */
    public Class<? extends Annotation> getAnnotation() {
        return bus.annotation;
    }

}
