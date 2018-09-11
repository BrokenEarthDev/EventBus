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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.brokenearthdev.eventbus.entities.EventBus;

/**
 * This is a default annotation that all event methods need to have. Initialize
 * through {@link EventBus#EventBus(Class)} to set a specific annotation for the
 * initialized event bus.
 *
 * Annotate this annotation on a method that accepts one parameter and that
 * parameter will require an event object that will be used by an {@link EventBus}.
 * The method will become what is known as an event method. Event methods will be looked
 * for after calling an event if the method's declaring class is registered using
 * {@link EventBus#register(Object)}.
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubscribeEvent {
}
