/*
Copyright 2018 github.com/BrokenEarthDev // gitlab.com/BrokenEarth
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
 */
package me.brokenearthdev.eventbus;

import me.brokenearthdev.eventbus.utils.CancellableEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for registering, unregistering,
 * getting options, etc.
 * You can use the EventBus in many ways. For example:
 * <pre>
 *     EventBus&#60;Event, Listener&#62; event = new EventBusBuilder&#60;&#62;()
 *     event.registerEvent(new Event(), this);
 * </pre>
 * Then to trigger the event use
 * <pre>
 *     event.triggerEvent(new Event());
 * </pre>
 * And then all methods - depending on the restriction type (see {@link EventBusOptions})
 * will run
 *
 * @param <T> The event class - it can be created by you. It is recommended that the other events
 *           (for example: ChatEvent) inherit from the Event so you put in the ChatEvent's instance -
 *           because the ChatEvent inherits from Event
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 2.0
 * @since 1.0
 */
public interface EventBus<T>  {
    /**
     * Registers a specified event so that the event listener class will be triggered
     * with the trigger method
     *
     * @param eventListener The class that inherits from the event listener you
     *                      provided
     * @return This object
     */
    EventBus register(Object eventListener);
    /**
     * Unregisters a specified event so that the event listener will no longer be triggered
     * with the trigger method
     *
     * @param eventListener The class that inherits from the event listener you provided
     * @return This object
     */
    EventBus unregister(Object eventListener);
    /**
     * Triggers all methods in the registered event listeners - according to the restriction type,
     * though {@link EventBusOptions}
     *
     * @param event the event instance that will be passed into all the parameters of the methods
     *              in each event listener - according to the restriction type. For more information,
     *              see {@link EventBusOptions}
     * @return This object
     */
    EventBus callEvent(T event);
    /**
     * Gets the options for the EventBus to specify which restriction you want. By default all methods are
     * unrestricted, which means as long as the registered class (or classes) have a method' (or methods')
     * parameter specified to an event, and will be triggered, the event would run.
     * <br>For example
     * <pre>
     *     // this event will run depending on the restriction type and if the
     *     // declaring class inherits from the event listener
     *     public void onEvent(Event event) {
     *         // code
     *     }
     * </pre>
     *
     * @return the event bus options {@link EventBusOptions}
     */
    EventBusOptions getOptions();
    /**
     * Gets the registered events listeners that would be triggered - depending on their
     * restriction type
     *
     * @return the registered event listeners
     */
    List<Object> getRegisteredEvents();
    /**
     * Gets the default options for every event bus. You can customize the options
     * because it'll return an {@link EventBusOptions} object.
     * Here you can edit the options such as
     * <pre>
     *     options.setRestrictedToAnnotation(CustomAnnotation.class);
     * </pre>
     *
     * @return The default options for every event bus
     */
    EventBusOptions getDefaultOptions();
    /**
     * Specified whether if the event class is cancellable
     * or not by checking if a custom annotation is present over the
     * event class or not
     *
     * @param event The event class
     * @return Whether if the event class is cancellable or not
     */
    default boolean isCancellable(Class<? extends T> event) {
        return event.isAnnotationPresent(CancellableEvent.class);
    }
    /**
     * Cancels the specified event if the specified event
     * is cancellable.
     * Cancelling an event will also cancel all events that
     * are subclasses of the specified event
     * If the specified event is not cancellable,
     * {@link IllegalArgumentException} will be thrown
     *
     * @param event The event class
     * @return This object
     */
    EventBus cancelEvent(Class<? extends T> event);
    /**
     * uncancels the specified event if the specified
     * event is cancellable.
     * uncancelling an event will uncancel all events
     * that are subclasses of the specified event
     * If the specified event is not cancellable,
     * {@link IllegalArgumentException} will be thrown
     *
     * @param event The event class
     * @return This object
     */
    EventBus unCancelEvent(Class<? extends T> event);
    /**
     * Checks whether if the specified event class
     * is cancelled or not.
     *
     * @param event The event class
     * @return This object
     */
    boolean isCancelled(Class<? extends T> event);


}
