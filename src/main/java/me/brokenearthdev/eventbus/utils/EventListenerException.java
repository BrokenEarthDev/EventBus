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

/**
 * A default {@link EventListenerException} for
 * the default event bus options.
 * This exception is thrown when you are trying to
 * call an event and one of the event method
 * causes and exception to be thrown
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 2.0
 */
public class EventListenerException extends Exception {

    public EventListenerException() {
        super();
    }

    public EventListenerException(String message) {
        super(message);
    }
}
