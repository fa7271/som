FROM node:12.18
WORKDIR /som-front

#download할 module목록
COPY package.json .

#현재 폴더에 있는 모든 폴더와 파일을 ADD 를 통해 가져옴
ADD . .
#package.json 에 정의한 moduel download
RUN npm install -g @vue/cli
RUN npm install vue@3.2.13
RUN npm install @vue/complier-sfc
#최종적으로 실행할 command
CMD ["npm", "run", "serve"]