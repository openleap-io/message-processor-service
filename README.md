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

# Starting up all services using docker compose

To be able to run the services you need:

1. Docker and Docker Compose installed on your machine.
2. set `keycloak-web` in your `/etc/hosts` file to point to `127.0.0.1` so that you can access Keycloak at `http://keycloak-web:8090`

2. The `message-processor-service` is using Microsoft Graph API to send messages, so you need to have a valid Microsoft Graph API application setup with the necessary permissions to send messages. You can find more information on how to set up the Microsoft Graph API application in the [Microsoft documentation](https://docs.microsoft.com/en-us/graph/auth-v2-user).
 
To run the services, you need to have the following variables set:

in [message-processor-service-email.msgraph.yml](docker/conf/message-processor-service-email.msgraph.yml) set 

```yaml
microsoft:
  azure:
    client-id:
    client-secret:
    tenant-id:
    sender-id:
```

in [message-processor-service-telegram.botapi.yml](docker/conf/message-processor-service-telegram.botapi.yml) set

```yaml
telegram:
  botapi:
    token:
```

Navigate to `docker` directory and run the following command to start the services:

```bash
docker compose up
```

this will start the `message-receiver-service`, `message-processor-service`, `rabbitmq`, `keycloak`, `swagger-ui`,
`registry-service`, `config-service` and `gateway-service` services.

```mermaid
graph LR
    Sender --> Gateway[Gateway Service]
    Gateway[Gateway Service] --> KEY[Keycloak]
    Gateway[Gateway Service] --> MRS[Message Receiver Service]
    KEY[Keycloak]
    subgraph SC[Spring Cloud Services]
        Registry[Registry Service]
        Config[Config Service]
    end
    subgraph COMMS[Communication Services]
        MRS --> RabbitMq
        RabbitMq --> MPS[Message Processing Service]
    end
    SC --> COMMS
    MPS --> Receiver

```

# Sending messages

## Authenticate and aquire token

### Request

```bash
curl --location 'http://keycloak-web:8090/realms/openleap-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_secret=RukGiq7ENJsUyb3y36MiQELEbKwLf58t' \
--data-urlencode 'grant_type=client_credentials' \
--data-urlencode 'scope=mrs_send' \
--data-urlencode 'client_id=message-receiver-service-client'
```
### Response

```json
{
    "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2VlpkSDV2TUQ0N2w4VnVWMnVuQVc4X2FYTkJhalo3N2lRU21WS0RzNmpJIn0.eyJleHAiOjE3NDk4MDU3OTQsImlhdCI6MTc0OTgwNTQ5NCwianRpIjoiNTQ3MTBiMTQtODkyNy00NGM3LWFmYTQtZWZiYWM1YzU4ZmYwIiwiaXNzIjoiaHR0cDovL2tleWNsb2FrLXdlYjo4MDkwL3JlYWxtcy9vcGVubGVhcC1yZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI5YzFhNmRmMy03OTY0LTQyNzAtYjBkZC01YzY4OTFjYmYyNjgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJtZXNzYWdlLXJlY2VpdmVyLXNlcnZpY2UtY2xpZW50IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1vcGVubGVhcC1yZWFsbSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoibXJzX3NlbmQgZW1haWwgcmVwb3J0LXNlcnZpY2UgcHJvZmlsZSIsImNsaWVudEhvc3QiOiIxOTIuMTY4LjY1LjEiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1tZXNzYWdlLXJlY2VpdmVyLXNlcnZpY2UtY2xpZW50IiwiY2xpZW50QWRkcmVzcyI6IjE5Mi4xNjguNjUuMSIsImNsaWVudF9pZCI6Im1lc3NhZ2UtcmVjZWl2ZXItc2VydmljZS1jbGllbnQifQ.Kv4BQFvAihG-SF5EXgpXPRmAJ3pIiaeA02L67QtlUOX1QKiwLCVt8W4uGFid3fwmXHYLCWPQyHaJH90Ou-RjbIhUgYAB0OkqIzYv_O15tzINS_4nu2HY0UKYS2uSCln9UDrJmfMHRQHSOng2pkBiwqHjOnx71Yh4cfWnT5E_HWlYTh3jssagjy6M3TRNS-aW0Ry69pSSuz175yFSFKJ9n9nrM99k2MwB-rs8PZqlZP84GCQybqCSRoT6H33sVRpaCUhfU0tOstghiN98eWd4eiiPChMSh8Ujfhl_CZ6S5l6kVdzYPy9MPrH8Or8dl_qXmQcbxJj6jhkn6MagpS9-qA",
    "expires_in": 300,
    "refresh_expires_in": 0,
    "token_type": "Bearer",
    "not-before-policy": 0,
    "scope": "mrs_send email report-service profile"
}
```

## Sending email

### Request



```run
curl --location 'localhost:8086/gw/mrs/message' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ2VlpkSDV2TUQ0N2w4VnVWMnVuQVc4X2FYTkJhalo3N2lRU21WS0RzNmpJIn0.eyJleHAiOjE3NDk4MDU3OTQsImlhdCI6MTc0OTgwNTQ5NCwianRpIjoiNTQ3MTBiMTQtODkyNy00NGM3LWFmYTQtZWZiYWM1YzU4ZmYwIiwiaXNzIjoiaHR0cDovL2tleWNsb2FrLXdlYjo4MDkwL3JlYWxtcy9vcGVubGVhcC1yZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI5YzFhNmRmMy03OTY0LTQyNzAtYjBkZC01YzY4OTFjYmYyNjgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJtZXNzYWdlLXJlY2VpdmVyLXNlcnZpY2UtY2xpZW50IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIiwiZGVmYXVsdC1yb2xlcy1vcGVubGVhcC1yZWFsbSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoibXJzX3NlbmQgZW1haWwgcmVwb3J0LXNlcnZpY2UgcHJvZmlsZSIsImNsaWVudEhvc3QiOiIxOTIuMTY4LjY1LjEiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1tZXNzYWdlLXJlY2VpdmVyLXNlcnZpY2UtY2xpZW50IiwiY2xpZW50QWRkcmVzcyI6IjE5Mi4xNjguNjUuMSIsImNsaWVudF9pZCI6Im1lc3NhZ2UtcmVjZWl2ZXItc2VydmljZS1jbGllbnQifQ.Kv4BQFvAihG-SF5EXgpXPRmAJ3pIiaeA02L67QtlUOX1QKiwLCVt8W4uGFid3fwmXHYLCWPQyHaJH90Ou-RjbIhUgYAB0OkqIzYv_O15tzINS_4nu2HY0UKYS2uSCln9UDrJmfMHRQHSOng2pkBiwqHjOnx71Yh4cfWnT5E_HWlYTh3jssagjy6M3TRNS-aW0Ry69pSSuz175yFSFKJ9n9nrM99k2MwB-rs8PZqlZP84GCQybqCSRoT6H33sVRpaCUhfU0tOstghiN98eWd4eiiPChMSh8Ujfhl_CZ6S5l6kVdzYPy9MPrH8Or8dl_qXmQcbxJj6jhkn6MagpS9-qA' \
--data-raw '{
  "recipients": [
    {
      "id": "kirilarsov@gmail.com",
      "channel": {
        "channelType": "EMAIL"
      }
    }
  ],
  "message": {
    "messageType": "TEMPLATE",
    "name": "Email message template",
    "templateParams": {
      "param1": "value1",
      "param2": "value2"
    },
    "attachments": [
      {
        "name": "host-A.log",
        "contentType": "text/plain",
        "base64Data": "dGVzdA==",
        "url": "https://example.com/test.txt"
      }
    ]
  }
}'
```

### Response

202 Accepted