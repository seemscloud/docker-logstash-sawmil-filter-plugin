# Sawmill Logstash Filter Plugin

## Development

### Copy Logstash / Sawmill pipelines to Logstash volume

```bash
docker-compose up --force-recreate --build --remove-orphans pipelines
```

### Start Logstash and wait for plugin

```bash
docker-compose up --force-recreate --build --remove-orphans logstash
```

### Build Sawmill Logstash filter plugin copy to logstash volume

```bash
docker-compose up --force-recreate --build --remove-orphans build
```

### Send messages in loop to Logstash

```bash
docker-compose up --force-recreate --build --remove-orphans bot
```

## TODO

- Problem to initialize GeoIP

```java
Pipeline.Factory factory = new Pipeline.Factory(geoIpConfiguration);
```

```java
io.logz.sawmill.exceptions.SawmillException: failed to load processor io.logz.sawmill.processors.GeoIpProcessor
```

- Reload `Sawmill Pipeline/GeoIP DB` in object SawmillPipeline if files changed
- Delete `Sawmill Pipeline` if file deleted