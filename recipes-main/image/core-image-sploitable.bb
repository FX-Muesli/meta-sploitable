# SPDX-FileCopyrightText: 2024 Felix Mues <felix.mues@tu-dortmund.de>
# SPDX-License-Identifier: MIT

SUMMARY = "A console-only image that fully supports the target device \
hardware."

IMAGE_FEATURES += "splash ssh-server-openssh "
# core packages
IMAGE_INSTALL = " ${CORE_IMAGE_BASE_INSTALL} packagegroup-core-full-cmdline init-ifupdown ntp "
# docker and docker_compose
IMAGE_INSTALL:append = " git docker-ce python3-setuptools python3-docker-compose "
# mutillidae
IMAGE_INSTALL:append = " mutillidae"
# XAMPP stack
IMAGE_INSTALL:append = " apache2 mariadb php php-cli phpmyadmin"
# extra packages for dvwa
IMAGE_INSTALL:append = " gd dvwa"
# local network configuration
IMAGE_INSTALL:append = " connection-config"
# selinux
IMAGE_INSTALL:append = " packagegroup-core-selinux libselinux-python selinux-init selinux-labeldev selinux-python setools "
# trace generation
IMAGE_INSTALL:append = " atop"
LICENSE = "MIT"


inherit selinux-image
# inherit core-image

# set up users

inherit extrausers

# set root pw: LetsAssumeThisIsSecure0815
# set msfuser pw: msfuser
EXTRA_USERS_PARAMS = "\
    usermod -p '\$5\$81VSp4tmBkIKP\$AuIAyETzZra5.qqRdAjn8.GjbHEC38YeZoVzUfrwLI9' root; \
    useradd -p '\$5\$5jBt3x8Mw\$Opu7MebsgXtA02heHOAtXpNztyBJ62kbDCbeZLKiMi9'-m -s /bin/bash msfuser; \
    "