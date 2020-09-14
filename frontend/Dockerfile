# Build stage
FROM node:13.12.0-alpine as builder

WORKDIR /frontend

ENV PATH /frontend/node_modules/.bin:$PATH

COPY package.json yarn.lock ./

RUN npm install --silent

COPY . ./

RUN npm run build

# Production environment
FROM nginx:1.17-alpine

RUN apk --no-cache add curl

RUN curl -L https://github.com/a8m/envsubst/releases/download/v1.1.0/envsubst-`uname -s`-`uname -m` -o envsubst && \
    chmod +x envsubst && \
    mv envsubst /usr/local/bin

COPY ./nginx.config /etc/nginx/nginx.template

CMD ["/bin/sh", "-c", "envsubst < /etc/nginx/nginx.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]

COPY --from=builder /frontend/build /usr/share/nginx/html