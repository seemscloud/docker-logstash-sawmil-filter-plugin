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

## Example

### [Sawmill Pipelines](/pipelines/files/pipelines/sawmill)

### Usage
![image](https://user-images.githubusercontent.com/9096064/163799186-c75e1feb-df8b-4cb4-b392-7f96885c9103.png)

### Output
![image](https://user-images.githubusercontent.com/9096064/163799127-5a2a26f4-66cc-4017-90d7-bf78a395eb0b.png)
