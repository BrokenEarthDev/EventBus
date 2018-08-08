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

import java.lang.reflect.InvocationTargetException;
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
 * @param <L> The Listener class. You need a listener class if you want to register an event. If you don't
 *           want to create a Listener class, then use {@link Object} instead
 */
public interface EventBus<T, L> {
    /**
     * Registers a specified event so that the event listener class will be triggered
     * with the trigger method
     *
     * @param eventListener The class that inherits from the event listener you
     *                      provided
     */
    void register(L eventListener);
    /**
     * Unregisters a specified event so that the event listener will no longer be triggered
     * with the trigger method
     *
     * @param eventListener The class that inherits from the event listener you
     *                      provided
     */
    void unregister(L eventListener);
    /**
     * Triggers all methods in the registered event listeners - according to the restriction type,
     * though {@link EventBusOptions}
     *
     * @param event the event instance that will be passed into all the parameters of the methods
     *              in each event listener - according to the restriction type. For more information,
     *              see {@link EventBusOptions}
     * @throws IllegalAccessException When the target method is private or package protected in an another package.
     *              Declare all methods public
     * @throws InstantiationException Thrown when the registered event listener class has params in their constructor
     *              (and not an empty constructor)
     * @throws InvocationTargetException Thrown when a method that is yet to be invoked throws an exception. Handle the
     *              exception in the method with try and catch and don't include the throws cause
     */
    void trigger(T event) throws IllegalAccessException, InstantiationException, InvocationTargetException;
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
    EventBusOptions<L> getOptions();
    /**
     * Gets the registered events listeners that would be triggered - depending on their
     * restriction type
     *
     * @return the registered event listeners
     */
    List<L> getRegisteredEvents();

}
