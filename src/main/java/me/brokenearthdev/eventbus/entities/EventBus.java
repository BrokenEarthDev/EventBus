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

import me.brokenearthdev.eventbus.annotations.CancellableEvent;
import me.brokenearthdev.eventbus.annotations.SubscribeEvent;
import me.brokenearthdev.eventbus.exceptions.EventBusException;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@link EventBus} is responsible for handling, cancelling, and calling events.
 * You can register a listener by using:
 * <pre>
 *     EventBus&lt;Event&gt; eventBus = new EventBus&lt;&gt;();
 *     eventBus.register(listenerObject);
 * </pre>
 * And now to call an event, use
 * <pre>
 *     eventBus.callEvent(eventObj);
 * </pre>
 * Please note that event type should be inherited or be the same as the Event superclass
 * specified in the type parameters
 * You can unregister an event listener by using {@link #unregister(Object)} and
 * cancel an event using {@link #cancelEvent(Class)}. To uncancel an event, use
 * {@link #uncancelEvent(Class)}
 *
 * @param <T> The superclasses of all events. The event bus is designed to be heavily
 *           customizable. The superclasses of all events will then be used as a type
 *           to call an event, cancel an event, or uncancel an event inherited or the
 *           same as the event specified
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 4.0
 * @since 1.0
 */
public class EventBus<T> implements Serializable {

    /**
     * The registered event listeners are stored here. Registered event listeners are
     * stored in a list. When an event is called, every event method in this list will
     * be invoked. To register an event listener, use {@link #register(Object)} and to
     * unregister an event listener, use {@link #unregister(Object)}
     */
    final List<Object> registered = new ArrayList<>();

    /**
     * The cancelled events are stored in this list. When a cancelled event is called,
     * no event method will be invoked. To cancel an event, use {@link #cancelEvent(Class)}
     * and to uncancel an event, use {@link #uncancelEvent(Class)}
     */
    final List<Class<?>> cancelled = new ArrayList<>();
    /**
     * The annotation in which a method needs to be annotated with this annotation to be
     * considered as an event method. Calling an event will first look methods with the
     * specified annotation. A method is an event method if
     * <ul>
     *    <li>The method is annotated with the specified event annotation</li>
     *    <li>The method is public and not static</li>
     *    <li>They only have one parameter</li>
     *    <li>That one parameter is requiring an event object. If you call that event
     *    (or any event that is a subclass of the specified event), that method will run</li>
     * </ul>
     */
    Class<? extends Annotation> annotation;

    /**
     * The {@link ListenerList} contains a list of registered listeners and cancelled events
     * where you can retrieve them because they're public.
     * You can also get the event annotation from the listener list by using {@link ListenerList#getAnnotation()}
     */
    private final ListenerList<T> listenerList = new ListenerList<>(this);

    /**
     * The {@link EventCaller} is responsible for calling events. {@link #callEvent(Object)} calls
     * {@link EventCaller#callEvent(Object)} by {@link #callEvent(Object)}.
     * {@link EventCaller} calls an event efficiently
     */
    private final EventCaller<T> caller;

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
    public EventBus(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        this.caller = new EventCaller<>(this);
    }

    /**
     * Creating an instance of this class with an empty constructor allows {@link SubscribeEvent}
     * to be the event annotation. This constructor will call the main constructor, which is
     * {@link #EventBus(Class)} to set the event annotation to {@link SubscribeEvent}. Any methods
     * that are annotated with {@link SubscribeEvent} are looked first before calling an event.
     * Refer to {@link #EventBus(Class)} for more information
     */
    public EventBus() {
        this(SubscribeEvent.class);
    }

    /**
     * Registers a listener and adds it to {@link #registered}.
     * When an event is called using
     * {@link #callEvent(Object)}, all event methods in the registered listeners will be searched
     * before invoking the event methods. A method is an event method if
     * <ul>
     *     <li>The method is annotated with the specified event annotation</li>
     *     <li>The method is public and not static</li>
     *     <li>They only have one parameter</li>
     *     <li>That one parameter is requiring an event object. If you call that event
     *     which is inherited or equal to the value specified in {@link T}, that method
     *     will run</li>
     * </ul>
     *
     * @param object The listener object
     * @return This object
     */
    public EventBus register(Object object) {
        registered.add(object);
        return this;
    }

    /**
     * Registers the specified listeners and adds them to {@link #registered}.
     * You can specify as many listener objects as you want since the parameter uses varargs
     * When an event is called using
     * {@link #callEvent(Object)}, all event methods in the registered listeners will be searched
     * before invoking the event methods. A method is an event method if
     * <ul>
     *     <li>The method is annotated with the specified event annotation</li>
     *     <li>The method is public and not static</li>
     *     <li>They only have one parameter</li>
     *     <li>That one parameter is requiring an event object. If you call that event
     *     which is inherited or equal to the value specified in {@link T}, that method
     *     will run</li>
     * </ul>
     *
     * @param objects The listener objects. You can specify as many objects as you want
     *                since the parameter uses varargs.
     * @return This object
     */
    public EventBus register(Object ...objects) {
        for (Object o : objects) {
            register(o);
        }
        return this;
    }

    /**
     * Unregisters the specified listener and removes it from {@link #registered}.
     * The {@link EventBus} will no longer look for events method in the specified object.
     * If the specified object isn't registered, nothing will happen
     *
     * @param object The registered object
     * @return This object
     */
    public EventBus unregister(Object object) {
        registered.remove(object);
        return this;
    }

    /**
     * Unregisters the specified listeners and removes them from {@link #registered}.
     * The {@link EventBus} will no longer look for event methods in the specified objects.
     * If one of the specified object is not registered, other specified objects that are
     * registered will be unregistered.
     *
     * @param objects The objects that will be unregistered if they are registered
     * @return This object
     */
    public EventBus unregister(Object ...objects) {
        for (Object o : objects) {
            unregister(o);
        }
        return this;
    }

    /**
     * This method will call all event methods that requires the specified object.
     * An event method will also be called if they requires any object that is a superclass and
     * higher-up classes. That means that any method that requires the {@link T} object as the
     * parameter will be called if any event is called. The specified event won't be called if it
     * is cancelled.
     *
     * @param event The event object. Method that'll be called will
     *              be affected by the type of event. Any method that will
     *              be called will have a superclass (or a higher class) or
     *              the specified event class as a parameter. The specified
     *              event won't be called if it is cancelled
     * @return This object
     */
    public EventBus callEvent(T event) {
        if (isCancelled(event.getClass())) return this;
        caller.callEvent(event);
        return this;
    }

    /**
     * An event will be cancellable if the class or the class inherits from
     * a class that is annotated with {@link CancellableEvent}
     *
     * @param event The specified event class that will be checked if the
     *              event is cancellable or not.
     * @return Whether if the specified event class is cancellable or not.
     */
    public boolean isCancellable(Class<? extends T> event) {
        return event.isAnnotationPresent(CancellableEvent.class);
    }

    /**
     * @param event The specified event class that will have the event
     *              class or it's superclass (or above) checked if it's
     *              cancelled or not.
     * @return Whether if the event class (or it's superclass or above) is
     * cancelled or not
     * @see #cancelEvent(Class)
     */
    public boolean isCancelled(Class<?> event) {
        return cancelled.contains(event) || event.getSuperclass() != null && isCancelled(event.getSuperclass());
    }

    /**
     * Cancels the specified event. Cancelled events won't run.
     * If the event is not cancellable, {@link EventBusException}
     * will be thrown.
     *
     * @param event The event to cancel
     * @return This object
     */
    public EventBus cancelEvent(Class<? extends T> event) {
        if (!isCancellable(event))
            throw new EventBusException("Specified event is not cancellable");
        cancelled.add(event);
        return this;
    }

    /**
     * Uncancels the specified event. If the event is not cancelled, nothing
     * will happen. If the event is not cancellable, {@link EventBusException}
     * will be thrown.
     *
     * @param event The event to uncancel.
     * @return This object
     */
    public EventBus uncancelEvent(Class<? extends T> event) {
        if (!isCancellable(event))
            throw new EventBusException("Specified event is not cancellable");
        cancelled.remove(event);
        return this;
    }

    /**
     * @return Whether if the {@link EventBus} is modifiable or not. Modifiable event buses
     * must be {@link ModifiableEventBus}, meaning that this object must be an instanceof
     * {@link ModifiableEventBus}.
     */
    public boolean isModifiable() {
        return this instanceof ModifiableEventBus;
    }

}
