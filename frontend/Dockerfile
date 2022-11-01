# pull official base image
FROM node:18.12.0-bullseye-slim
ENV NODE_ENV=production

# set working directory
WORKDIR /usr/src/app

# add packages and install them
COPY ["package.json", "package-lock.json*", "npm-shrinkwrap.json*", "./"]
RUN npm ci --only=production

# add app
COPY . .

# start app
EXPOSE 3000
RUN chown -R node /usr/src/app
USER node
CMD ["npm", "start"]
