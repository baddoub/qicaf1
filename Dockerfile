FROM fedora:latest

RUN curl https://bintray.com/sbt/rpm/rpm > /etc/yum.repos.d/bintray-sbt-rpm.repo
RUN dnf -y install java-1.8.0-openjdk-devel sbt && dnf -y clean all

COPY . /app
WORKDIR /app

RUN sbt stage

EXPOSE 80

CMD ["target/universal/stage/bin/qicaf1", "-Dplay.http.secret.key=bliblablooom", "-Dhttp.port=80"]
