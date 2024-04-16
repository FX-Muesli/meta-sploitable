# SPDX-FileCopyrightText: 2024 Felix Mues <felix.mues@tu-dortmund.de>
# SPDX-License-Identifier: MIT

SUMMARY = "Script to set up my home internet connection"
HOMEPAGE = ""
SECTION = "optional"
LICENSE="CLOSED"
LIC_FILES_CHKSUM=""
SRC_URI = "file://interfaces \
           file://wpa_supplicant.conf"
FILES:${PN} += "${THISDIR}/connection-config_1.0/raspberrypi4-64"


do_install () {
	install -d ${D}${sysconfdir}/network/
	install -m 0644 ${WORKDIR}/interfaces ${D}${sysconfdir}/network/interfaces
	install -m 0644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}/wpa_supplicant.conf
}