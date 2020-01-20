#!/bin/bash

# Variables:
# AWS_REGION
# DOCKER_IMAGE
# SERVICE_NAME

# Script to get current task definition, and based on that add new ecr image address to old template and remove attributes that are not needed, then we send new task definition, get new revision number from output and update service
# https://github.com/aws/aws-cli/issues/3064#issuecomment-514214738
set -e
TASK_DEFINITION=$(aws ecs describe-task-definition --task-definition "$SERVICE_NAME" --region "$AWS_REGION")
NEW_TASK_DEFINTIION=$(echo $TASK_DEFINITION | jq --arg IMAGE "$DOCKER_IMAGE" '.taskDefinition | .containerDefinitions[0].image = $IMAGE | del(.taskDefinitionArn) | del(.revision) | del(.status) | del(.requiresAttributes) | del(.compatibilities)')
NEW_TASK_INFO=$(aws ecs register-task-definition --region "$AWS_REGION" --cli-input-json "$NEW_TASK_DEFINTIION")
NEW_REVISION=$(echo $NEW_TASK_INFO | jq '.taskDefinition.revision')
aws ecs update-service --cluster ${SERVICE_NAME} \
                       --service ${SERVICE_NAME} \
                       --task-definition ${SERVICE_NAME}:${NEW_REVISION}
