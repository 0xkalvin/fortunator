 #!/bin/bash

FRONTEND=fortunator-frontend
BACKEND=fortunator-backend

IS_BACKEND=$(git log --no-merges --name-only -p -1 origin/master | cut -d/ -f1 | grep back | head -1)
IS_FRONTEND=$(git log --no-merges --name-only -p -1 origin/master | cut -d/ -f1 | grep front | head -1)


if [[ "$IS_BACKEND" = "backend" ]]  ; then
    echo "Starting backend deploy..."

    cd backend
    
    heroku container:push web --app=$BACKEND

    heroku container:release web --app=$BACKEND
    

elif [[ "$IS_FRONTEND" = "frontend" ]]  ; then
    echo "Starting frontend deploy..."

    cd frontend

    heroku container:push web --app=$FRONTEND

    heroku container:release web --app=$FRONTEND

fi


