#!/usr/bin/sh
dnf -y install java-1.8.0-openjdk-devel
curl https://bintray.com/sbt/rpm/rpm > bintray-sbt-rpm.repo
mv bintray-sbt-rpm.repo /etc/yum.repos.d/
dnf -y install sbt
