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

import me.brokenearthdev.eventbus.annotations.SubscribeEvent;

import java.lang.annotation.Annotation;

/**
 * {@link ModifiableEventBus} is a type of {@link EventBus} that can be
 * modified by {@link EventBusModifier}. {@link #isModifiable()}
 * will return true when using {@link ModifiableEventBus}
 *
 * @param <T> The superclasses of all events. The event bus is designed to be heavily
 *           customizable. The superclasses of all events will then be used as a type
 *           to call an event, cancel an event, or uncancel an event inherited or the
 *           same as the event specified
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
public class ModifiableEventBus<T> extends EventBus<T> {

    /**
     * This constructor requires an annotation as the parameter. You need to specify
     * the event annotation in the constructor.
     * Any methods that are annotated with the specified annotation are looked first
     * before calling an event. A method is an event method if
     * <ul>
     *     <li>The method is annotated with the specified event annotation</li>
     *     <li>The method is public and not static</li>
     *     <li>They only have one parameter</li>
     *     <li>That one parameter is requiring an event object. If you call that event
     *     which is inherited or equal to the value specified in {@link T}, that method
     *     will run</li>
     * </ul>
     *
     * @param annotation The annotation specified. Any method annotated with this annotation
     *                   and that method is public and not static and has one parameter requiring
     *                   an event object which is inherited or equal to the value specified in {@link T}
     *                   will be considered as an event method
     */
    public ModifiableEventBus(Class<? extends Annotation> annotation) {
        super(annotation);
    }

    /**
     * Creating an instance of this class with an empty constructor allows {@link SubscribeEvent}
     * to be the event annotation. This constructor will call the main constructor, which is
     * {@link EventBus#EventBus(Class)} to set the event annotation to {@link SubscribeEvent}. Any methods
     * that are annotated with {@link SubscribeEvent} are looked first before calling an event.
     * Refer to {@link EventBus#EventBus(Class)} for more information
     */
    public ModifiableEventBus() {
        super();
    }
}
