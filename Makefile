.PHONY: default all build down

default: all

build:
	@docker-compose build --no-cache

all:
	@docker-compose up

down:
	@docker-compose down --rmi all --remove-orphans