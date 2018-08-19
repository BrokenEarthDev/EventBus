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

/**
 * Any event class annotated with this annotation with
 * the value set to more than 0 will be considered as delayed events.
 * If the value is less than 0, an exception will be thrown
 *
 * Delayed events will be called after a specified milliseconds in the
 * {@link #value()}
 *
 * Any event class inherited from an event class annotated with this
 * annotation will not inherit this trait.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DelayedEvent {

    short value();

}
