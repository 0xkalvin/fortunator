FROM node:13.12.0-alpine as development

WORKDIR /frontend

ENV PATH /frontend/node_modules/.bin:$PATH

COPY package.json ./

RUN npm install --silent
RUN npm install react-scripts@3.4.3 -g --silent
RUN echo -e "REACT_APP_API_URL=https//default.dev.api.com" >> .env

COPY . ./

EXPOSE 3000