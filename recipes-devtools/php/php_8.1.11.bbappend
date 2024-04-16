# SPDX-FileCopyrightText: 2024 Felix Mues <felix.mues@tu-dortmund.de>
# SPDX-License-Identifier: MIT
# add apache2
PACKAGECONFIG:append = " apache2"
DEPENDS:append = " apache2"

do_install:prepend() {
        mkdir -p ${D}${sysconfdir}/apache2/
        printf "\nLoadModule dummy_module modules/mod_dummy.so\n" > ${D}${sysconfdir}/apache2/httpd.conf
}

do_install:append () {
    rm -f ${D}${sysconfdir}/apache2/httpd.conf
}