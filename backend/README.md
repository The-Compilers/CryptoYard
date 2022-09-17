# CryptoYard backend

The backend is a SpringBoot application composed of services. Services communicate by exchanging messages.

## Messaging architecture

![](C:\Sync\CryptoYard\backend\doc\messaging.png)

The base class for all messages is `Message`. Messages are divided into two types, based on semantics:

* `Event` - something has happened. The receiver of the event must decide how to react on it.
* `Command` - the sender commands the receiver to do execute a particular command. In this case the sender implicitly
  defines the intention of the command.

Services define their custom messages by extending the `Event` or `Command` classes. All event-type messages must have
prefix `E` in their name: `EAppLifecycle`, etc. All child classes of command must have prefix `C` in the
name: `CApiSubscribe`, etc.

All services must extend the `CYService` base class. `CyService` provides the mechanisms for message exchange through
a `MessageDispatcher`. `MessageDispatcher` is a singleton object which maintains a list of subscriptions and distributes
messages to necessary subscribers. Any service can subscribe to a specific topic.

## Startup procedure

When the SpringBoot app has successfully started, an `EAppLifecycle` event is sent to all services, with `type=STARTED`.
The `onStart` method is called for each service. Each service then must subscribe to necessary events and send necessary
commands to other services.

## Message processing

Some message processing is done in the parent class `CYService`, while some will be done in specific service child
classes. Whenever a service the `onEvent` method (for events) or `onCommand` method (for commands) is called. To make
sure the same message is not processed twice - a class which has processes an incoming message must return `true` from
the `onEvent` or `onCommand` method which has processed it. Whenever a class receives an unknown event or command inside
the `onEvent` or `onCommand` method, it must return false signalizing that the message was not understood and must be
processed by a child class.

Whenever a `EAppLifecycle` is received by a service, it is processed by the `CYService` base class and `true` is
returned from the method `CYService.onEvent`, therefore, a child class overriding the `onEvent` method must NOT process
the received `EAppLifecycle` event.

## Direct service-service communication

Services are allowed to call methods for each other directly, as long as method calls are immutable - read-only
operations which don't make any changes to the state of the service. For example, a `TraderService` could call a method
from `TickerPriceService` to find actual price for a specific coin.

## Current state and possible future extensions

Currently, message class is used as a topic, but later there could be hierarchical topics.

Inter-service communication with messages could be extended by adding a inter-process message bus, such as Apache Kafka.
Currently, all the messages are exchanged internally, inside the same process, on the same thread!