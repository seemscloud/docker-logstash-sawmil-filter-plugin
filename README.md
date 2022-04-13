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