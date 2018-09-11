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
package me.brokenearthdev.eventbus.exceptions;

/**
 * This exception inherits from {@link EventBusException} which inherits from
 * {@link RuntimeException}, which means that try-catch block is unnecessary.
 * This exception could be thrown when there is something wrong with a event listener.
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 */
public class EventListenerException extends EventBusException {

    public EventListenerException() {
        super();
    }

    public EventListenerException(String message) {
        super(message);
    }

    public EventListenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventListenerException(Throwable cause) {
        super(cause);
    }

    protected EventListenerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
