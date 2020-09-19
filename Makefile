.PHONY: default all build down test

default: all

build:
	@docker-compose build --no-cache

all:
	@docker-compose up

down:
	@docker-compose down --rmi all --remove-orphans

test:
	@docker-compose up test-backend
