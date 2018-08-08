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

import java.lang.annotation.Annotation;

/**
 * This class is responsible for managing restriction
 * options for the {@link EventBus}.
 * Setting a restriction level will change which methods will
 * run.<br>For example:
 * <pre>
 *     public static void main(String[] args) {
 *         // Only registered event methods will run within the restricted class
 *         setRestrictedToClass(Test.class);
 *         // Only registered annotated event methods with the following annotation will run
 *         setRestrictedToAnnotation(AnnotationForMethods.class);
 *     }
 * </pre>
 *
 * @param <L> The listener class. For more information, see {@link EventBus}
 */
public interface EventBusOptions<L> {
    /**
     * Sets the event methods restricted to the following annotation
     * ie. Only registered event listeners with the specified annotation would run
     *
     * @param annotation the annotation required for the event listener methods to run
     */
    void setRestrictedToAnnotation(Class<? extends Annotation> annotation);
    /**
     * Sets the event methods unrestricted. Any event listener method would
     * run as long as the event listener class is registered
     */
    void setUnrestricted();
    /**
     * Sets the event methods restricted to a specified class meaning that
     * the event would be triggered only in the specified class. If the listener class
     * is registered
     *
     * @param event The class in which the restriction will be set to
     */
    void setRestrictedToClass(Class<? extends L> event);
    /**
     * @return Whether if the {@link EventBus} is restricted to class or not
     */
    boolean isRestrictedToClass();
    /**
     * @return Whether if the {@link EventBus} is unrestricted - meaning that all registered
     * event methods would run
     */
    boolean isUnRestricted();
    /**
     * @return Whether if the {@link EventBus} is restricted to annotation or not - meaning that all
     * registered event methods would run if they are annotated with a specified annotation
     */
    boolean isRestrictedToAnnotation();
    /**
     * @return This method will return null if the {@link EventBus} is unrestricted or restricted to annotation
     *         or would return the restricted class if the {@link EventBus} is restricted to class
     */
    Class<?> getRestrictedClass();
    /**
     * @return this method will return null if the {@link EventBus} is unrestricted or restricted to class
     *         or would return the restricted annotation if the {@link EventBus} is restricted to annotation
     */
    Class<? extends Annotation> getRestrictedAnnotation();
}
