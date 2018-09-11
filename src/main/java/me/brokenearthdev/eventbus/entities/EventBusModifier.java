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

/**
 * {@link EventBusModifier} modifies the {@link ModifiableEventBus}'s options
 * such as their event annotations.
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 * @see ModifiableEventBus
 */
public class EventBusModifier {

    /**
     * The {@link ModifiableEventBus} instance initialized by a constructor
     */
    private ModifiableEventBus bus;

    /**
     * The constructor requires a {@link ModifiableEventBus} object. The
     * specified {@link ModifiableEventBus} object will be the EventBus
     * that will be modified
     *
     * @param bus The {@link ModifiableEventBus} instance
     */
    public EventBusModifier(ModifiableEventBus bus) {
        this.bus = bus;
    }

    /**
     * Sets the event annotation
     * An event annotation is the annotation in which a method needs to be annotated with
     * this annotation to be considered as an event method. Calling an event will first look methods
     * with the specified annotation. A method is an event method if
     * <ul>
     *     <li>The method is annotated with the specified event annotation</li>
     *      <li>The method is public and not static</li>
     *      <li>They only have one parameter</li>
     *      <li>That one parameter is requiring an event object. If you call that event
     *      (or any event that is a subclass of the specified event), that method will run</li>
     * </ul>
     *
     * @param annotation The annotation that will be the event annotation
     * @return This object
     */
    public EventBusModifier setEventAnnotation(Class<? extends Annotation> annotation) {
        bus.annotation = annotation;
        return this;
    }

}