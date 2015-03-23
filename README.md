To start server: ./gradlew :grpc-lab2:createPollServer
To start client: ./gradlew :grpc-lab2:createPollClient
Client Application is deployed on Port 8080 and Accepts the following REST API:
POST /moderators/{moderator_id}/polls
HTTP Headers:
Content-type: application/json
{
"question": "What type of smartphone do you have?",
"started_at": "2015-02-23T13:00:00.000Z",
"expired_at" : "2015-02-24T13:00:00.000Z",
"choice": [ "Android", "iPhone" ]
}
Response:
HTTP Code: 201
{
"id" : "1ADC2FZ"   # Convert long/int to Base 36 for readability
}
