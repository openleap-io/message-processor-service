# message-processor-service

The `Message processor service` or also referred as `MPS` is responsible for listening on new messages sent by the `MRS`
on RabbitMq and based on the message channel type to sent it to the corresponding receiver


# Design

```mermaid
graph LR
    Sender --> MRS[Message Receiver Service]
    subgraph Communication Service
        MRS --> RabbitMq
        RabbitMq --> MPS[Message Processing Service]
    end
    MPS --> Receiver

```

# Message Receiver Service

## Dataflow

```mermaid
stateDiagram
    state MPS {
        [*] --> ListenForMessages
        ListenForMessages --> ProcessMessage
        ProcessMessage --> ValidateMessage
        ValidateMessage --> SentWithCorrespondingAdapter
        SentWithCorrespondingAdapter --> [*]
    }
```
