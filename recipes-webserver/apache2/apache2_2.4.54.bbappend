# SPDX-FileCopyrightText: 2024 Felix Mues <felix.mues@tu-dortmund.de>
# SPDX-License-Identifier: MIT
do_install:append() {
    printf "\n#PHP config\nLoadModule php_module /usr/libexec/apache2/modules/libphp.so\n\
<FilesMatch \\.php$>\n  SetHandler application/x-httpd-php\n</FilesMatch>\n" >> ${D}/${sysconfdir}/${BPN}/httpd.conf
    sed -i 's/ServerName localhost:80/ServerName 192.168.31.2:80/' ${D}/${sysconfdir}/${BPN}/httpd.conf
    sed -i 's/User daemon/User www-data/' ${D}/${sysconfdir}/${BPN}/httpd.conf
    sed -i 's/Group daemon/Group www-data/' ${D}/${sysconfdir}/${BPN}/httpd.conf
    # set port to 100080 to avoid conflict with mutilidae
    sed -i 's/80/10080/' ${D}/${sysconfdir}/${BPN}/httpd.conf
    # set directory index to prefer index.php
    sed -i 's/DirectoryIndex index.html/DirectoryIndex index.php index.html/' ${D}/${sysconfdir}/${BPN}/httpd.conf
}
