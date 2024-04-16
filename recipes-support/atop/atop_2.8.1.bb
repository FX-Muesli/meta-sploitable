SUMMARY = "Monitor for system resources and process activity"
DESCRIPTION = "Atop is an ASCII full-screen performance monitor for Linux that \
is capable of reporting the activity of all processes (even if processes have \
finished during the interval), daily logging of system and process activity for \
long-term analysis, highlighting overloaded system resources by using colors, \
etc. At regular intervals, it shows system-level activity related to the CPU, \
memory, swap, disks (including LVM) and network layers, and for every process \
(and thread) it shows e.g. the CPU utilization, memory growth, disk \
utilization, priority, username, state, and exit code."
HOMEPAGE = "http://www.atoptool.nl"
SECTION = "console/utils"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=393a5ca445f6965873eca0259a17f833"

DEPENDS = "ncurses zlib"

SRC_URI = "http://www.atoptool.nl/download/${BP}.tar.gz \
           "
SRC_URI[md5sum] = "2475c596c7f22171bb1cabdeeadc31ee"
SRC_URI[sha256sum] = "970058a19b79c3444f971f854572a0e63891dc2d5f7037ff837570854ce0da1e"

CVE_CHECK_IGNORE += "\
    CVE-2011-3618 \
"

do_compile() {
    oe_runmake all
}

do_install() {
    make DESTDIR=${D} VERS=${PV} sysvinstall
    rm -f ${D}${sysconfdir}/init.d/atopacct

    # /var/log/atop will be created in runtime
    rm -rf ${D}${localstatedir}/log
    rmdir --ignore-fail-on-non-empty ${D}${localstatedir}

    # remove atopacct related files
    rm -rf ${D}${sbindir} ${D}${mandir}/man8
}

inherit systemd

SYSTEMD_SERVICE:${PN} = "atop.service atopgpu.service"
SYSTEMD_AUTO_ENABLE = "disable"

FILES:${PN} += "${systemd_unitdir}/system-sleep"

RDEPENDS:${PN} = "procps"
