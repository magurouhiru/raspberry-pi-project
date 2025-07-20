FROM sbtscala/scala-sbt:eclipse-temurin-17.0.15_6_1.11.3_2.13.16

RUN apt update \
    && apt upgrade -y \
    && apt install sqlite3 -y
RUN mkdir -p /var/lib/sqlite3 \
    && echo > /var/lib/sqlite3/playdb
