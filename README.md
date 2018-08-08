## What is EventBus?
EventBus is an api that allows creating events easier.

## How can I get the EventBus?
To get the event bus, go to the <a href="https://github.com/BrokenEarthDev/EventBus/releases/tag/v1.0">releases section</a>

## What are some Examples?
To create an EventBus object, 

```java
EventBus<Event, Listener> e = new EventBusBuilder<>();
```
For **Event**: You can create an Event class and use it as you saw in the type parameters. I recommend you to make other "sub" events to inherit from that event.

For **Listener**: You can create an Event listener that all event classes need to inherit. If you don't want to create an event listener, then use **Object**

### Registering an event

```java
e.register(new HiEventListenerClass);
```

### Trigerring an event. You can trigger events when a certain condition is met

```java
public class Test extends Listener {

  private static EventBus<Event, Listener> e = new EventBusBuilder();
  
  private static Test instance = new Test();
  
  public static void main(String[] args) throws Exception {
    e.register(instance);
    if (isWhatever()) {
      e.trigger(new Event());
    }
  }
  
  public void eventListener(Event e) {
    System.out.println(true);
  }
  
  private static boolean isWhatever() {
    return true;
  }
}
```

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
