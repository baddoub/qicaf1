#!/usr/bin/sh
curl https://bintray.com/sbt/rpm/rpm > bintray-sbt-rpm.repo
mv bintray-sbt-rpm.repo /etc/yum.repos.d/
dnf -y install java-1.8.0-openjdk-devel sbt postgresql-server

# Configure postgresql
postgresql-setup --initdb --unit postgresql
cp -v /vagrant/pg_hba.conf /var/lib/pgsql/data/pg_hba.conf
systemctl enable postgresql
systemctl start postgresql
sudo -u postgres psql postgres <<EOF
CREATE ROLE QICAF1 LOGIN;
CREATE SCHEMA AUTHORIZATION QICAF1;
EOF
