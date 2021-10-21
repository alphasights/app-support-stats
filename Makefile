.PHONY: build watch test lint clean serve up install

build:
	./gradlew clean ktlintFormat build -x test

watch:
	./gradlew build -x test -x ktlintFormat --continuous

test:
	./gradlew clean test

lint:
	./gradlew ktlintFormat ktlintCheck

clean:
	./gradlew clean

serve: build
	java -jar build/libs/app-support-stats-0.0.1-SNAPSHOT.jar \
    --spring.profiles.active=local

up: build
	docker-compose up

install:
	./gradlew ktlintApplyToIdea addKtlintCheckGitPreCommitHook


