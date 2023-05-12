#Function to create queues and dlq-queues
createQueue(){

  queueName=$1
  dlqName=$2

  if ! test -z "$dlqName"
  then
    dlqUrl=$(awslocal sqs create-queue --queue-name $dlqName | jq .QueueUrl | tr -d '"')
    echo "DLQ_URL: $dlqUrl"
    dlqArn=$(awslocal sqs get-queue-attributes --queue-url $dlqUrl --attribute-names QueueArn | jq .Attributes.QueueArn | tr -d '"')
    echo "DLQ_ARN: $dlqArn"
    queueUrl=$(awslocal sqs create-queue --queue-name $queueName | jq .QueueUrl | tr -d '"')
    echo "QUEUE_URL: $queueUrl"
    awslocal sqs set-queue-attributes \
    --queue-url $queueUrl \
    --attributes '{
        "RedrivePolicy": "{\"deadLetterTargetArn\":\"'$dlqArn'\",\"maxReceiveCount\":\"1000\"}",
        "DelaySeconds": "0",
        "ReceiveMessageWaitTimeSeconds": "10",
        "VisibilityTimeout": "60"
    }'
  else
    queueUrl=$(awslocal sqs create-queue --queue-name $queueName | jq .QueueUrl | tr -d '"')
    echo "QUEUE_URL: $queueUrl"
  fi
}

createQueue teste2