/*
Copyright 2018 github.com/BrokenEarthDev // gitlab.com/BrokenEarth
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
 */
package me.brokenearthdev.eventbus.builders;

import me.brokenearthdev.eventbus.EventBusOptions;
import me.brokenearthdev.eventbus.EventBus;

import java.lang.annotation.Annotation;

/**
 * The EventBusOptionsBuilder implements the {@link EventBusOptions} interface, and
 * is responsible for setting restriction.<br>
 * For more information, go to {@link EventBusOptions}
 *
 * @param <L> The listener class. For more information, go to {@link EventBus}
 */
public final class EventBusOptionsBuilder<L> implements EventBusOptions<L> {
    /**
     * Specifies whether the {@link EventBus} is restricted to annotation
     * or not
     */
    private boolean restrictedToAnnotation = false;
    /**
     * Specifies whether the {@link EventBus} is unrestricted or not. By default,
     * it is true
     */
    private boolean unrestricted = true;
    /**
     * Specifies whether the {@link EventBus} is restricted to class or not
     */
    private boolean restrictedToClass = false;
    /**
     * The restricted class. By default: null
     */
    private Class<?> restrictedClass = null;
    /**
     * The restricted annotation. By default: null
     */
    private Class<? extends Annotation> restrictedAnnotation = null;

    @Override
    public void setRestrictedToAnnotation(Class<? extends Annotation> annotation) {
        setRestricted(true, false, false, annotation);
    }

    @Override
    public void setUnrestricted() {
        setRestricted(false,  false, true, null);
    }

    @Override
    public void setRestrictedToClass(Class<? extends L> class_) {
        setRestricted(false, true, false, class_);
    }

    @Override
    public boolean isRestrictedToClass() {
        return restrictedToClass;
    }

    @Override
    public boolean isUnRestricted() {
        return unrestricted;
    }

    @Override
    public boolean isRestrictedToAnnotation() {
        return restrictedToAnnotation;
    }

    @Override
    public Class<?> getRestrictedClass() {
        return restrictedClass;
    }

    @Override
    public Class<? extends Annotation> getRestrictedAnnotation() {
        return restrictedAnnotation;
    }

    private void setRestricted(boolean restrictedToAnnotation, boolean restrictedToClass, boolean unrestricted, Class restriction) {
        this.restrictedToAnnotation = restrictedToAnnotation;
        this.restrictedAnnotation = ((!restrictedToAnnotation) ? null : restriction);
        this.restrictedToClass = restrictedToClass;
        this.restrictedClass = ((!restrictedToClass) ? null : restriction);
        this.unrestricted = unrestricted;
    }
}