.PHONY: default all build down test backend database

default: all

build:
	@docker-compose build --no-cache

all:
	@docker-compose up

down:
	@docker-compose down --rmi all --remove-orphans

test:
	@docker-compose up test-backend

backend:
	@docker-compose up backend

database:
	@docker-compose up -d postgres
