SUMMARY = "Deploy DVWA"
DESCRIPTION = "This recipe adds DVWA to the image."
SECTION = "vulnerabilities"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
RDEPENDS:${PN} += " apache2 mariadb"

SRC_URI = "git://github.com/digininja/DVWA.git;branch=master;protocol=https \
           file://dvwa-db-setup \
          "

SRCREV = "3663d72c4c4f51ac5bcd9f0c8a16b9d890383347"
SRC_URI[sha256sum] = "b4c0627b94103a0260e140ee55212fc3f520edf6600fa132017cdf01e444919b"

PV = "2.1"
S = "${WORKDIR}/git"

FILES:${PN} = "${datadir_native}/apache2/default-site/htdocs/* \
               /etc/init.d/dvwa-db-setup \
               /etc/rc5.d/dvwa-db-setup"

APACHE_HTTP_DIR = "${D}/${datadir_native}/apache2/default-site/htdocs/"

do_install () {
	install -d -m 755 ${APACHE_HTTP_DIR}
    cp --preserve=mode,timestamps -R ${S}/* ${APACHE_HTTP_DIR}
    sed "s/'impossible';/'low';/" ${APACHE_HTTP_DIR}config/config.inc.php.dist > ${APACHE_HTTP_DIR}config/config.inc.php

    # install init script to set up database
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/dvwa-db-setup ${D}/${sysconfdir}/init.d/

	# set up for run at boot
	install -d ${D}/${sysconfdir}/rc5.d
	ln -s -r ${D}/${sysconfdir}/init.d/dvwa-db-setup ${D}/${sysconfdir}/rc5.d/dvwa-db-setup
}