FROM node:18

WORKDIR /som-front

# package.json과 package-lock.json 복사 후 의존성 설치
COPY package*.json ./
RUN npm install -g @vue/cli
RUN npm install npm@latest -g
RUN npm install -g @vue/cli-service
RUN npm install @vue/babel-preset-app --save-dev

# CMD 변경
CMD ["npm", "run", "serve"]