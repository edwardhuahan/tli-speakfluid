# pull official base image
FROM node:18.12.0-alpine as build
# set working directory
WORKDIR /app/frontend
# add app
COPY . ./
RUN npm install -g create-react-app
# add packages and install them
RUN npm ci
RUN npm run build

# start app
FROM nginx:stable-alpine
COPY nginx/nginx.conf /etc/nginx/conf.d/configfile.template
COPY --from=build /app/frontend/build /usr/share/nginx/html

CMD sh -c "envsubst '\$PORT' < /etc/nginx/conf.d/configfile.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"