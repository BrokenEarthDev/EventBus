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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles triggering events. If you want
 * to trigger an event please check out {@link EventBus}
 *
 * @param <T> The main event class. More information at {@link EventBus}
 * @param <L> The Listener class. More information at {@link EventBus}
 */
public final class Trigger<T, L> {
    /**
     * The {@link EventBus} instance. Created by the constructor
     */
    private EventBus<T, L> bus;
    /**
     * Creates the {@link EventBus} and the EventBus must be
     * specified
     *
     * @param bus The current {@link EventBus}
     */
    public Trigger(EventBus<T, L> bus) {
        this.bus = bus;
    }
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
    public void trigger(T event) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        if (bus.getOptions().isRestrictedToAnnotation()) {
            Class<? extends Annotation> annotation = bus.getOptions().getRestrictedAnnotation();
            for (L l : bus.getRegisteredEvents()) {
                for (Method method : getAnnotatedMethodsWith(l.getClass(), annotation)) {
                    if (method.getParameterTypes().length == 1 && contains(method.getParameterTypes()[0], event.getClass())) {
                        method.invoke(l.getClass().newInstance(), event);
                    }
                }
            }
        } else if (bus.getOptions().isRestrictedToClass()) {
            Class c = bus.getOptions().getRestrictedClass();
            for (Method method : c.getMethods()) {
                if (method.getParameterTypes().length == 1 && contains(method.getParameterTypes()[0], event.getClass())) {
                    method.invoke(c.newInstance(), event);
                }
            }
        } else if (bus.getOptions().isUnRestricted()) {
            for (L l : bus.getRegisteredEvents()) {
                for (Method method : l.getClass().getMethods()) {
                    if (method.getParameterTypes().length == 1 && contains(method.getParameterTypes()[0], event.getClass())) {
                        method.invoke(l.getClass().newInstance(), event);
                    }
                }
            }
        }
    }
    /**
     * Gets the methods annotated with the specified
     * annotation
     *
     * @param c The class in which the methods annotated methods will be in
     * @param annotation the specified annotation
     * @return The methods annotated with the specified annotation
     */
    private static List<Method> getAnnotatedMethodsWith(Class c, Class<? extends Annotation> annotation) {
        List<Method> annotated = new ArrayList<>();
        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(annotation)) annotated.add(method);
        }
        return annotated;
    }
    /**
     * Credit goes to: github.com/ReflxctionDev<br>
     * Looks for the triggered events
     *
     * @param c1 the first class
     * @param c2 the second class
     * @return Whether the events to look for is there
     */
    private static boolean contains(Class c1, Class c2) {
        return c1.equals(c2) || c1.getSuperclass() != null && contains(c1.getSuperclass(), c2);

    }
}
