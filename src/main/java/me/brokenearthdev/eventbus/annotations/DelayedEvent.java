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

/**
 * Annotate this annotation on an event class if you want all event methods
 * expecting the event method (or it's superclass or above) will run delayed.
 * By default, all event's delay is 0 millis.
 * Delay is measured in millis.
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DelayedEvent {

    /**
     * The delay specified. Delay is measured in millis. All event methods requiring
     * the event class annotated with this annotation will run delayed by the delay
     * specified.
     *
     * @return The delay set
     */
    short value();

}
