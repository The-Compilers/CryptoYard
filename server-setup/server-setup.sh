#!/bin/bash

# This is a setup script which you can run on a bare Ubuntu 20.04 server to set up all necessary packages and config for the project

# Check if we run the script ar root
if [ "$(id -u)" != "0" ]; then
   echo "This script must be run as root" 1>&2
   exit 1
fi

# Change these if necessary - the domain name for the server, email which will receive HTTPS renewal notifications
DOMAIN=cryptoyard.appdev.cloudns.ph
ADMIN_EMAIL=girts.strazdins@gmail.com

apt update
apt upgrade --yes

# Nginx , Git
apt install --yes git nginx

# Docker and docker-compose
apt install --yes ca-certificates curl gnupg lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
apt update
apt install --yes docker-ce docker-ce-cli containerd.io
apt install --yes docker-compose

# HTTP Certificates with "Let's Encrypt" Certbot
apt install --yes certbot python3-certbot-nginx
sed -i "s/server_name _/server_name $DOMAIN/" /etc/nginx/sites-available/default
nginx -t && sudo nginx -s reload
certbot --nginx -d $DOMAIN -m $ADMIN_EMAIL --non-interactive --agree-tos
echo "0 12 * * * /usr/bin/certbot renew --quiet" >> /etc/crontab
crontab /etc/crontab

# Clone GIT
cd /home/ubuntu
sudo -u ubuntu git clone https://github.com/The-Compilers/CryptoYard.git ./crypto-yard

echo "Boom! Done!"
