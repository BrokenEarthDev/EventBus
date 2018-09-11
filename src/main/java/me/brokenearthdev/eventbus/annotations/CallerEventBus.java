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
 * Annotate a non-static and non-final uninitialized {@link EventBus} object with this annotation
 * in an event class in where an {@link EventBus} would call. When called by an {@link EventBus},
 * the field will be initialized with the value equal to the event bus caller.
 *
 * However, {@link EventBusException} will be thrown when one of the conditions is not met:
 * <ul>
 *  <li>The annotated object is not of type {@link EventBus}</li>
 *  <li>The annotated object is static</li>
 *  <li>The annotated object is final</li>
 * </ul>
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CallerEventBus {
}
