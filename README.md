## What is EventBus?
EventBus is an api that allows creating events easier.

## How can I get the EventBus?
To get the latest event bus, go to the <a href="https://github.com/BrokenEarthDev/EventBus/releases/tag/v2.0">releases section</a>

> ## EventBus is now updated!
EventBus is now updated to version 2.0

## What are some Examples?
To create an EventBus object, 

```java
EventBus<Event> e = new EventBusBuilder<>();
```
For **Event**: You can create an Event class and use it as you saw in the type parameters. I recommend you to make other "sub" events to inherit from that event.

### Registering an event

```java
e.register(new HiEventListenerClass);
```

### Calling an event

```java
public class Test {

  private static EventBus<Event> e = new EventBusBuilder();
  
  private static Test instance = new Test();
  
  public static void main(String[] args) {
    e.register(instance);
    if (isWhatever()) {
      e.callEvent(new Event());
    }
  }
  @SubscribeEvent
  public void eventListener(Event e) {
    System.out.println(true);
  }
  
  private static boolean isWhatever() {
    return true;
  }
}
```

## Can I exclude methods or listeners?
Yes, you can. Using the **@ExcludeListener** annotation to exclude a registered listener and the **@ExcludeMethod** annotation to exclude an event method

## Can I cancel events?
As long as the event you want to cancel is cancellable, you can.
To make an event **cancellable**, annotate the event class with **@CancellableEvent**.
To cancel a cancellable event:
```java
eventbus.cancelEvent(Event.class)
```
Cancelling an event will also cancel all event subclasses of the event class
To uncancel an event:
```java
eventbus.unCancelEvent(Event.class);
```
uncancelling an event will also uncancel all event subclasses of the event class

## Can I delay an event?
Yes, you can, too. To make an event run delayed, annotate the event class with
```java
@DelayedEvent(millis)
```
The value in the delayed event will be in millis.

## Default options
In EventBus, there are several default options and you can edit them.
To get the default options, use
```java
eventbusobject.getDefaultOptions();
```
The code above will get the default options for all EventBusses.
**By default, EventBus is restricted to __@SubscribeEvent__, meaning that you need to annotate
@SubscribeEvent over the event methods.**

## Can I restrict event listeners?
Yes - you can! Using the ```EventBusOptions```. To get the EventBusOptions use
```java
e.getOptions();
```

**e.getOptions()** will return the **EventBusOptions**. 

## How can I?

There are several methods in **EventBusOptions**. For example, If you want to restrictToAnnoation (make only event methods run if they have a specified annotations), you can use 
```java
 e.getOptions().setRestrictedToAnnotation(YourDesiredAnnotation.class);
```

And now, the previous event listener example won't get `true` to be printed out.
To make it run, annotate the method with `YourDesiredAnnotation`

Another method called **setRestrictedToClass** will make only event methods run in a specified class.
For example

```java
e.getOptions().setRestrictedToClass(DesiredClass.class);
```
And finally, there is another method called **setUnrestricted** that will make all event methods run. By default the EventBus is **unrestricted**

## How can I check if an EventBus is unrestricted, restricted to annotation, or restricted to class ?

There are several booleans in the **EventBusOptions** class

1 - isRestrictedToClass: returns whether if the **EventBus** is restricted to class

2 - isUnRestricted: returns whether if the **EventBus** is unrestricted. **NOTE**: By default, EventBusses are **unrestricted**

3 - isRestrictedToAnnotation: returns whether if the **EventBus** is restricted to annotation.

## How can I get the restricted class or annotation?

There are several methods that does that in the **EventBusOptions** class

1 - getRestrictedClass: gets the restricted class. If there aren't any, it would return null.

2 - getRestrictedAnnotation: gets the restricted annotation. If there aren't any, it would return null.


### How can I report bugs or errors?

You can report bugs or errors at the <a href="https://github.com/BrokenEarthDev/EventBus/issues">issue tracker</a>
