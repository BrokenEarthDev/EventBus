EventBus is an api that allows creating events easier.


> ## EventBus is now updated!
> EventBus is now updated to 4.0
> To get the lastest EventBus, go to the <a href="https://github.com/BrokenEarthDev/EventBus/releases/tag/4.0">releases section in github</a>

## Using the EventBus

### Creating an instance

> The EventBus is supposed to have the main event class specified in the "T" generic<br>
> The main event class is a class that all other event classes will inherit from<br>

```java
public static final EventBus<Event> EVENT_BUS = new EventBus<>();
```

### Understanding listeners and event methods

An **event method** is a method with certain unique characterists that will be called when a certain
event is called using:

```java
EVENT_BUS.callEvent(eventObject);
```

For a method to be an event method, they must have certain characteristics:

<ul>
   <li>The method is annotated with the specified event annotation</li>
   <li>The method is public and not static</li>
   <li>They only have one parameter</li>
   <li>That one parameter is requiring an event object. If you call that event
    (or any event that is a subclass of the specified event), that method will run</li>
</ul>

> An event annotation is an annotation in which a method would to annotated by it to have one of
> the characteristics of an event method.
> The default event annotation is **SubscribeEvent**. You can set the event annotation when you initialize
> the EventBus by using the constructor that requires Class<?> as their parameter

An **event listener** is a class that contains **0 or more event methods**.

### Registering and unregistering an event listener

> An event listener is a class that contains 0 or more event method.

By registering an event listener, methods within the event listener might be called when a **certain**
event is called

To register an event listener:

```java
EVENT_BUS.register(listenerObject);
```

You can also register multiple event listeners on one line:

```java
EVENT_BUS.register(listenerObject1, listenerObject2, listenerObject3, ...);
```

> Unregistering an event listener won't have its event methods called when a certain event is called

To unregister an event listener

```java
EVENT_BUS.unregister(listenerObject);
```

You can also unregister multiple event listeners on one line:

```java
EVENT_BUS,unregister(listenerObject1, listenerObject2, listenerObject3, ...);
```

### Calling an event

By calling an event, event methods are searched and called if they have their parameter requiring an
object equal to the event called or their parameter requiring an object that is a superclass (or above) 
of the called event.

To call an event:

```java
EVENT_BUS.callEvent(eventObject);
```

> Any method requiring an event object the same as the one specified in the EventBus generic as their parameter will always be called
> since it is a superclass of all events

### Cancelling and uncancelling events

To cancel an event, the event should be **CANCELLABLE**

To make an event cancellable (can be cancelled), annotate the event class with **CancellableEvent**

By cancelling an event, that event (and their subclasses) won't get called when the EventBus calls it.

To cancel an event:

```java
EVENT_BUS.cancelEvent(DesiredEvent.class);
```

To uncancel an event:

```java
EVENT_BUS.uncancelEvent(DesiredEvent.class);
```

To check if an event class is cancellable:

```java
boolean isCancellable = EVENT_BUS.isCancellable(DesiredEvent.class);
```

To check if an event is cancelled:

```java
boolean isCancelled = EVENT_BUS.isCancelled(DesiredEvent.class);
```

### Delaying an event

> Delaying an event will delay all event methods expecting this event (as their parameter) by 
> the specified milliseconds

To make an event delayed, annotate the event class with **DelayedEvent** and specify the time in the
parameter.
To value specified will be measured as **milliseconds**

### Getting the caller EventBus

In an event class, where it might be called, you can get the caller EventBus easily.

Annotate a field with **CallerEventBus** in your event class.

```java
@CallerEventBus EventBus event_bus;
```

When the event is called, the **event_bus** variable will be initialized to the caller EventBus

### ListenerList

ListenerList is useful for getting cancelled events, registered listeners, and the event annotation for an EventBus.

```java
public static final ListenerList LISTENER_LIST = new ListenerList(EVENT_BUS);
```

To get the cancelled events:

```java
List<Class<?>> cancelled = LISTENER_LIST.getCancelledEvents();
```

To get the registered listeners:

```java
List<Object> registered = LISTENER_LIST.getRegisteredListeners();
```

And to get the event annotation:

```java
Class<? extends Annotation> eventAnnotation = LISTENER_LIST.getEventAnnotation();
```

### ModifiableEventBus

A **ModifiableEventBus** is an EventBus that can have its settings (such as its event annotations) modified by the **EventBusModifier**.
The ModifiableEventBus inherits from the **EventBus**.

To create an instance of **ModifiableEventBus**
```java
public static final ModifiableEventBus<Event> MODIFIABLE_EVENT_BUS = new ModifiableEventBus<>();
```

To modify a ModifiableEventBus, create an instance of the **EventBusModifier**
```java
public static final EventBusModifier MODIFIER = new EventBusModifier(MODIFIABLE_EVENT_BUS); 
```

To check if an **EventBus** is modifiable:

```java
boolean isModifiable = EVENT_BUS.isModifiable();
```

If it returns true, you are safe to use:

```java
ModifiableEventBus<Event> casted = (ModifiableEventBus<Event>) EVENT_BUS;
```

## Planned Features:

<ul>
    <li>Use consumers as event listeners, too</li>
    <li>EventBusAction, which can be queued and have a consumer specified,
    that consumer will be called when a certain action is done such as registering</li>
</ul>
