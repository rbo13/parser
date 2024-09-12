# Parser
This repo contains a code for parsing data. Currently it has support for [NEM12 Format](https://aemo.com.au/-/media/files/electricity/nem/retail_and_metering/market_settlement_and_transfer_solutions/2022/mdff-specification-nem12-nem13-v25.pdf?la=en)

An example NEM12 file is provided: [meter_readings.csv](./meter_readings.csv)

# Pre-requisites
1. Docker with `docker compose` command available.
2. Java and OpenJDK.
3. Maven, if you don't have `mvn` command, a `mvn` wrapper is provided. Use `./mvnw` command.
4. Git Bash (Optional, only if running on Windows).

# Dependencies:
Here are the dependencies used for this project:
1. Spring Boot
2. Flyway
3. PostgreSQL Client Library
4. HikariCP

## Cloning the repo
```git
git clone https://github.com/rbo13/parser.git
cd parser
```

## Get the postgresql image via docker compose:
```shell
# compose.yaml
docker compose up --build -d
```

## Running tests:
```shell
# on linux or mac
./mvnw clean test

# on windows (using Powershell)
.\mvnw clean test
```

## Running the application:
```shell
# on linux or mac
./mvnw clean install
java -jar ./target/parser-0.0.1-SNAPSHOT.jar ./meter_readings.csv

# on windows (using Powershell)
.\mvnw clean install
java -jar .\target\parser-0.0.1-SNAPSHOT.jar .\meter_readings.csv
```

## Conclusion:
If all is up and running, you should be able to see this log statements being printed on your terminal.
![image](https://github.com/user-attachments/assets/b8723994-c5ba-4722-8a71-aca9dc6cc32b)
