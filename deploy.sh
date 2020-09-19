 #!/bin/bash

FRONTEND=fortunator-frontend
BACKEND=fortunator-backend

was_backend_already_deployed=0
was_frontend_already_deployed=0

for PROJECT in $(git log --name-only --oneline --no-merges -1 | sed 1d | grep '/' | cut -d "/" -f1); do
    echo "Modified folder -> $PROJECT"

    if [[ "$PROJECT" = "backend" ]] && [[ $was_backend_already_deployed -eq 0 ]] ; then
        echo "Starting backend deploy..."

        cd backend
        
        heroku container:push web --app=$BACKEND

        heroku container:release web --app=$BACKEND

        was_backend_already_deployed=1

        cd ..

    elif [[ "$PROJECT" = "frontend" ]] && [[ $was_frontend_already_deployed -eq 0 ]]  ; then
        echo "Starting frontend deploy..."

        cd frontend

        heroku container:push web --arg REACT_APP_API_URL=$REACT_APP_API_URL --app=$FRONTEND 

        heroku container:release web --app=$FRONTEND

        was_frontend_already_deployed=1

        cd ..
    fi

done
