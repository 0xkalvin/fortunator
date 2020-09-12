 #!/bin/sh

FRONTEND=fortunator-frontend
BACKEND=fortunator-backend

# BACKEND
heroku container:push web --app=$BACKEND

heroku container:release web --app=$BACKEND


# FRONTEND
heroku container:push web --app=$FRONTEND

heroku container:release web --app=$FRONTEND

