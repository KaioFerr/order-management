#!/usr/bin/env bash

FILEPATH="${2:-../src/main/resources/db/migration}"

function timestamp() {
    echo $(date +"%Y%m%d%H%M%S")
}

function timestamped_filename() {
    echo "$FILEPATH/V$(timestamp)__${1}.sql"
}

[[ -z "$1" ]] && echo "Usage: $0 migration_name" && exit 1

touch "$(timestamped_filename $1)"