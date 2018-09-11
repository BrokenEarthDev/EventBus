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
package me.brokenearthdev.eventbus.utils;

/**
 * A simple interface for managing functions. For more information
 * refer to {@link java.util.function.Consumer}
 *
 * @param <T> The type in where the {@link #accept(Object)} method
 *           will require
 *
 * @author BrokenEarth // BrokenEarthDev
 * @version 1.0
 * @since 4.0
 * @see java.util.function.Consumer
 */
public interface Consumer<T> {

    /**
     * You can pass in an object to that method and call it.
     * You can add your desired code when implementing this method.
     *
     * @param object The object to pass in. Must be of same type as
     *               {@link T}
     */
    void accept(T object);

}
