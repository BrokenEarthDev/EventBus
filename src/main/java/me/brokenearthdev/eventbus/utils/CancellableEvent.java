/*
Copyright 2018 github.com/BrokenEarthDev // gitlab.com/BrokenEarth
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
 */
package me.brokenearthdev.eventbus.utils;

import java.lang.annotation.*;
import me.brokenearthdev.eventbus.EventBus;

/**
 * Any event class annotated with this annotation will be cancellable.
 * Cancellable events can be cancelled by {@link EventBus#cancelEvent(Class)}
 * and can be uncancelled by {@link EventBus#unCancelEvent(Class)}
 * cancelled event won't be called.
 * If the event class annotated with with this annotation is cancelled,
 * all subclasses of the cancelled event class will also be cancelled
 *
 * Any event class inherited from the cancellable event class will also
 * be a cancellable event
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 2.0
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CancellableEvent {
}
