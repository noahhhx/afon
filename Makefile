.PHONY: build-all run-playground help

build-all: ## Build whole project
	./mvnw clean install

run-playground: ## Compile and run playground in terminal
	./mvnw -q compile exec:java -pl afon-examples/playground

help: ## Show available targets
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf " %-20s %s\n", $$1, $$2}'