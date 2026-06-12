DEBUG_PORT ?= 5005

.PHONY: build-all run-playground run-playground-debug help

build-all: ## Build whole project
	./mvnw clean install

run-playground: ## Compile and run playground in terminal (DEBUG_PORT=5005)
	MAVEN_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:$(DEBUG_PORT)" \
			./mvnw -q compile exec:java -pl afon-examples/playground

help: ## Show available targets
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf " %-20s %s\n", $$1, $$2}'