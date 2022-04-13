# Sawmill Logstash Filter Plugin

## Development 

### Start logstash
```bash
docker-compose up --force-recreate --build --remove-orphans logstash
```

### Build Sawmill Logstash filter plugin
```bash
docker-compose up --force-recreate --build --remove-orphans build
```

### Copy pipelines to Logstash volume
```bash
docker-compose up --force-recreate --build --remove-orphans pipelines
```

### Send messages in loop to Logstash
```bash
docker-compose up --force-recreate --build --remove-orphans bot
```