#!/bin/bash

# sbt clean dist && docker-compose up --scale playapp=2 --remove-orphans

# sbt clean dist && docker-compose up --remove-orphans --force-recreate

sbt clean dist && docker-compose up --scale playapp=2 --remove-orphans --force-recreate