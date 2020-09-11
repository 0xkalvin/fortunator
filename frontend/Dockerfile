FROM node:13.12.0-alpine

WORKDIR /frontend

ENV PATH /frontend/node_modules/.bin:$PATH

COPY package.json ./

RUN npm install --silent
RUN npm install react-scripts@3.4.3 -g --silent

COPY . ./

EXPOSE 3000

CMD ["npm", "start"]