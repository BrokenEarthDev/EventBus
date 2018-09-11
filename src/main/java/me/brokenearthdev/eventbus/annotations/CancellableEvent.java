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
package me.brokenearthdev.eventbus.annotations;

import java.lang.annotation.*;
import me.brokenearthdev.eventbus.entities.EventBus;
import me.brokenearthdev.eventbus.exceptions.EventBusException;

/**
 * Annotate an event class with this annotation if you want an event to be cancellable.
 * Cancellable events can be cancelled by using {@link EventBus#cancelEvent(Class)} and
 * uncancelled by using {@link EventBus#uncancelEvent(Class)}.
 * {@link EventBus#isCancellable(Class)} will return true if the specified event class
 * is annotated with {@link CancellableEvent}.
 *
 * Cancelled events won't get called by {@link EventBus#callEvent(Object)}.
 * When trying to cancel a non-cancellable event, {@link EventBusException} will be thrown.
 * The same happens when trying to uncancel a non-cancellable event.
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CancellableEvent {
}
