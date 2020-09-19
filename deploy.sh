 #!/bin/bash

FRONTEND=fortunator-frontend
BACKEND=fortunator-backend

PROJECT=$(git log --name-only --oneline --no-merges -1 | sed 1d | grep '/' | cut -d "/" -f1 | head -1)


if [[ "$PROJECT" = "backend" ]]  ; then
    echo "Starting backend deploy..."

    cd backend
    
    heroku container:push web --app=$BACKEND

    heroku container:release web --app=$BACKEND
    

elif [[ "$PROJECT" = "frontend" ]]  ; then
    echo "Starting frontend deploy..."

    cd frontend

    heroku container:push web --arg REACT_APP_API_URL=$REACT_APP_API_URL --app=$FRONTEND 

    heroku container:release web --app=$FRONTEND

fi
