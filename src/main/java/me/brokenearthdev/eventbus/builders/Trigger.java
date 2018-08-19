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
import me.brokenearthdev.eventbus.utils.DelayedEvent;
import me.brokenearthdev.eventbus.utils.ExcludeListener;
import me.brokenearthdev.eventbus.utils.ExcludeMethod;
import me.brokenearthdev.eventbus.utils.SubscribeEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handles triggering events. If you want
 * to trigger an event please check out {@link EventBus}
 *
 * @param <T> The main event class. More information at {@link EventBus}
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 2.0
 * @since 1.0
 */
final class Trigger<T> {
    /**
     * The {@link EventBus} instance. Created by the constructor
     */
    private EventBus<T> bus;
    /**
     * Creates the {@link EventBus} and the EventBus must be
     * specified
     *
     * @param bus The current {@link EventBus}
     */
    public Trigger(EventBus<T> bus) {
        this.bus = bus;
    }
    /**
     * Triggers all methods in the registered event listeners - according to the restriction type,
     * though {@link EventBusOptions}
     *
     * @param event the event instance that will be passed into all the parameters of the methods
     *              in each event listener - according to the restriction type. For more information,
     *              see {@link EventBusOptions}
     */
    public void trigger(T event) {
        if (getDelay(event.getClass()) < (short) 0) {
            try {
                throw new Exception("delay can't be less than 0");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        TimerTask t = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (bus.getOptions().isRestrictedToAnnotation()) {
                        Class<? extends Annotation> annotation = bus.getOptions().getRestrictedAnnotation();
                        for (Object l : bus.getRegisteredEvents()) {
                            boolean isExcluded = l.getClass().isAnnotationPresent(ExcludeListener.class);
                            if (isExcluded) continue;

                            for (Method method : getAnnotatedMethodsWith(l.getClass(), annotation)) {
                                if (isExcluded(method)) continue;
                                if (method.getParameterTypes().length == 1 && contains(event.getClass(), method.getParameterTypes()[0])) {
                                    method.invoke(l.getClass().newInstance(), event);
                                }
                            }
                        }
                    } else if (bus.getOptions().isRestrictedToClass()) {
                        Class c = bus.getOptions().getRestrictedClass();
                        boolean isExcluded = c.isAnnotationPresent(ExcludeListener.class);
                        if (isExcluded) return;
                        for (Method method : c.getMethods()) {
                            if (isExcluded(method)) continue;
                            if (method.getParameterTypes().length == 1 && contains(method.getParameterTypes()[0], event.getClass())) {
                                method.invoke(c.newInstance(), event);
                            }
                        }
                    } else if (bus.getOptions().isUnRestricted()) {
                        for (Object l : bus.getRegisteredEvents()) {
                            boolean isExcluded = l.getClass().isAnnotationPresent(ExcludeListener.class);
                            if (isExcluded) return;
                            for (Method method : l.getClass().getMethods()) {
                                if (isExcluded(method)) continue;
                                if (method.getParameterTypes().length == 1 && contains(method.getParameterTypes()[0], event.getClass())) {
                                    method.invoke(l.getClass().newInstance(), event);
                                }
                            }
                        }
                    }
                } catch (InvocationTargetException e) {
                    if (bus.getOptions().getListenerException() == null) {
                        e.printStackTrace();
                        return;
                    }
                    Exception ex = bus.getOptions().getListenerException();
                    try {
                        throw ex;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        e.getCause().printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        if (getDelay(event.getClass()) == 0) {
            t.run();
            return;
        }
        new Timer().schedule(t, (int)getDelay(event.getClass()));
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

    private static boolean isExcluded(Method method) {
        return method.getAnnotation(ExcludeMethod.class) != null;
    }

    private short getDelay(Class<?> eventClass) {
        return (!eventClass.isAnnotationPresent(DelayedEvent.class) ? 0 : eventClass.getAnnotation(DelayedEvent.class).value());
    }

}
