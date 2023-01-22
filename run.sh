#!/bin/bash

declare project_dir=$(dirname "$0")
declare dc_file=${project_dir}/docker-compose.yml

function build_docker_image() {
    ./gradlew jibDockerBuild
}

function build_app() {
    ./gradlew clean build
}

function start_infra() {
    echo "Starting infra...."
    docker-compose --profile infra -f "${dc_file}" up --build --force-recreate -d
    docker-compose --profile infra -f "${dc_file}" logs -f
}

function stop_infra() {
    echo "Stopping infra...."
    docker-compose --profile infra -f "${dc_file}" stop
    docker-compose --profile infra -f "${dc_file}" rm -f
}

function restart_infra() {
    stop_infra
    sleep 3
    start_infra
}

function start() {
    echo "Starting app and dependencies...."
    #build_docker_image
    build_app
    docker-compose --profile infra --profile app -f "${dc_file}" up --build --force-recreate -d
    docker-compose --profile infra --profile app -f "${dc_file}" logs -f
}

function stop() {
    echo 'Stopping all....'
    docker-compose --profile infra --profile app -f "${dc_file}" stop
    docker-compose --profile infra --profile app -f "${dc_file}" rm -f
}

function restart() {
    stop
    start
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$@
fi

eval "${action}"
