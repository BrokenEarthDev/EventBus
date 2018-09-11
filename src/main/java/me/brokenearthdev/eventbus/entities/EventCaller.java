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

import me.brokenearthdev.eventbus.annotations.CallerEventBus;
import me.brokenearthdev.eventbus.annotations.DelayedEvent;
import me.brokenearthdev.eventbus.exceptions.EventBusException;
import me.brokenearthdev.eventbus.exceptions.EventListenerException;
import me.brokenearthdev.eventbus.utils.Action;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for calling events efficiently
 *
 * @param <T> The type parameter of the initialized {@link EventBus}
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
public class EventCaller<T> {

    /**
     * This is the caller {@link EventBus} initialized by the constructors
     */
    private EventBus<T> bus;

    /**
     * This is the main constructor that initializes {@link #bus} to
     * the variable set in the parameter
     *
     * @param bus The caller {@link EventBus}
     */
    public EventCaller(EventBus<T> bus) {
        this.bus = bus;
    }

    /**
     * Initializes the caller {@link EventBus} annotatied with in
     * {@link CallerEventBus} in an event class which isa accessible
     * by an {@link EventBus}
     *
     * @param event The event class
     */
    private void initCallerEventBus(T event) {
        Class<?> eventClass = event.getClass();
        Field[] fields = eventClass.getFields();
        List<Field> fields1 = new ArrayList<>();
        for (Field field : fields) {
            if (field.getAnnotation(CallerEventBus.class) != null) {
                if (!isEventBusVariable(field.getType()))
                    throw new EventBusException("Field isn't a type of " + EventBus.class.getName());
                if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()))
                    throw new EventBusException("Cannot initialize caller event bus when they are static or final");
                fields1.add(field);
            }
        }
        for (Field field : fields1) {
            try {
                field.setAccessible(true);
                field.set(event, bus);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param class_ The {@link Class} to check if it is an {@link EventBus},
     *               or is a subclass (or below) of {@link EventBus}
     * @return Whether if the data provided in the parameters is an {@link EventBus},
     * or is a subclass (or below) of {@link EventBus}
     */
    private static boolean isEventBusVariable(Class class_) {
        return class_.equals(EventBus.class) || class_.getSuperclass() != null && isEventBusVariable(class_.getSuperclass());
    }

    /**
     * Calls the specified event. It searches for the event methods for the
     * specified event and invokes them.
     *
     * @param event The event that will affect what methods will be called
     */
    public void callEvent(T event) {
        if (getRegisteredClasses().size() == 0) return;
        Class eventClass = event.getClass();
        if (getDelay(event.getClass()) < 0)
            throw new EventBusException("Delay can't be negative");
        Action action = () -> {
            for (Object o : getRegisteredClasses()) {
                for (Method method : o.getClass().getMethods()) {
                    if (isEventMethod(method, event)) {
                        try {
                            method.invoke(o, event);
                        } catch (IllegalAccessException e) {
                            throw new EventListenerException("One of the event listener methods isn't accessible");
                        } catch (InvocationTargetException e) {
                            throw new EventListenerException("One of the event listeners had an uncaught exception", e.getCause());
                        }
                    }
                }
            }
        };
        if (getDelay(eventClass) == 0) {
            action.run();
        } else {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    action.run();
                }
            }, getDelay(eventClass));
        }
        initCallerEventBus(event);
    }

    /**
     * @return The registered classes
     */
    private List<Object> getRegisteredClasses() {
        return bus.registered;
    }

    /**
     * Specifies whether if the method is an event method with the event class (or above)
     * specified.
     *
     * @param method The method that'll be checked if it is an event method
     *               with the event class (or above) specified
     * @param obj The event class
     * @return Whether if the method is an event method with the event class (or its superclass
     * or above) specified.
     */
    private boolean isEventMethod(Method method, T obj) {
        return Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()) &&
                method.getParameterCount() == 1 && contains(obj.getClass(), method.getParameterTypes()[0])
                && method.getAnnotation(bus.annotation) != null;
    }

    /**
     * Checks whether the first parameter is the same as the second parameter
     * or the second parameter is the superclass (or above) of the first parameter
     *
     * @param c1 The class to check if it is equal to the second parameter
     *           or is a subclass (or below) of the second parameter
     * @param c2 The class to check if it is equal to the first parameter
     *           or is a superclass (or above) of the first parameter
     * @return Whether if the first parameter is equal to the second
     * parameter or the second parameter is the superclass (or above) of
     * the first parameter {@code firstParameter instanceof secondParameter}
     */
    private static boolean contains(Class c1, Class c2) {
        return c1.equals(c2) || c1.getSuperclass() != null && contains(c1.getSuperclass(), c2);
    }

    /**
     * Checks whether if the event class is delayed (annotated with {@link DelayedEvent})
     * or not
     *
     * @param class_ The class to check if it is delayed (annotated with {@link DelayedEvent)
     * @return Whether if the event class is annotated with {@link DelayedEvent}
     */
    private static boolean isDelayed(Class class_) {
        return class_.isAnnotationPresent(DelayedEvent.class);
    }

    /**
     * Gets the delay for the specified event class. If the specified event class
     * is not annotated with {@link DelayedEvent} or not {@link #isDelayed(Class)},
     * the method will return 0.
     *
     * @param class_ The class to retrieve its delay
     * @return The method will return 0 if the event class is not annotated with
     * {@link DelayedEvent} or will return {@link DelayedEvent#value()} if it is
     * annotated with {@link DelayedEvent}
     */
    private static int getDelay(Class class_) {
        return (!isDelayed(class_)) ? 0 : ((DelayedEvent) class_.getAnnotation(DelayedEvent.class)).value();
    }

}
