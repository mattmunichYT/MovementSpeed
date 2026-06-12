# MovementSpeed

A lightweight Paper/Spigot plugin that allows players and administrators to quickly modify walking and flying speeds.

## Features

* Change fly speed
* Change walk speed
* Change both with a single command
* Modify your own speed
* Modify other players' speed
* Reset speeds to default values
* Multi-language support (English and French)
* No external dependencies

## Supported Platforms

* Paper 1.21+
* Spigot 1.21+

## Commands

| Command                       | Description                             |
| ----------------------------- | --------------------------------------- |
| `/flyspeed <speed>`           | Set your fly speed                      |
| `/flyspeed <player> <speed>`  | Set another player's fly speed          |
| `/flyspeed reset`             | Reset your fly speed                    |
| `/walkspeed <speed>`          | Set your walk speed                     |
| `/walkspeed <player> <speed>` | Set another player's walk speed         |
| `/walkspeed reset`            | Reset your walk speed                   |
| `/speed <speed>`              | Set both walk and fly speed             |
| `/speed <player> <speed>`     | Set another player's walk and fly speed |
| `/speed reset`                | Reset both speeds                       |

## Permissions

### Fly Speed

```text
movementspeed.command.flyspeed.self
movementspeed.command.flyspeed.others
```

### Walk Speed

```text
movementspeed.command.walkspeed.self
movementspeed.command.walkspeed.others
```

### General Speed

```text
movementspeed.command.speed.self
movementspeed.command.speed.others
```

### Wildcards

```text
movementspeed.command.*
movementspeed.*
```

## Configuration

Language files are stored inside:

```text
plugins/MovementSpeed/lang/
```

Included translations:

```text
en_US.yml
fr_FR.yml
```

## Building

Requirements:

* Java 21
* Maven

Build the project:

```bash
mvn clean package
```

The compiled JAR will be generated in:

```text
target/
```

## License

This project is licensed under the MIT License.
