gcloud functions deploy test \
   --region asia-east2 \
   --runtime java11 \
   --trigger-http \
   --memory 256MB \
   --source=build/deploy \
   --entry-point handler.PlainTextHandler \
   --allow-unauthenticated