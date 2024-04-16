SUMMARY = "Example recipe for using inherit useradd"
DESCRIPTION = "This recipe adds mutillidae files to the image. It also adds a separate user under which the container will be started"
SECTION = "main"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
DEPENDS += " docker-ce"
RDEPENDS:${PN} += " bash"
SRC_URI = "git://github.com/webpwnized/mutillidae-docker.git;branch=master;protocol=https \
		   file://0001-set-mysql-to-arm64v8.patch \
		   file://0001-set-ip.patch \
		   file://mutillidae.list \
		   file://mutillidae.tar.gz.dont-unpack \
           file://mutillidae-start-docker \
          "
SRCREV = "23f4d2ee82e1633db3d94db847c0ce842476a1b0"
SRC_URI[sha256sum] = "b4c0627b94103a0260e140ee55212fc3f520edf6600fa132017cdf01e444919b"

PV = "8.11.1"
S = "${WORKDIR}/git"

EXCLUDE_FROM_WORLD = "1"

inherit useradd

# You must set USERADD_PACKAGES when you inherit useradd. This
# lists which output packages will include the user/group
# creation code.
USERADD_PACKAGES = "${PN}"

# You must also set USERADD_PARAM and/or GROUPADD_PARAM when
# you inherit useradd.

# USERADD_PARAM specifies command line options to pass to the
# useradd command. Multiple users can be created by separating
# the commands with a semicolon. Here we'll create two users,
# user1 and user2:
USERADD_PARAM:${PN} = "--no-create-home --no-user-group -G docker -r -s /sbin/nologin mutillidae;"


do_install () {
	install -d -m 755 ${D}${base_prefix}/opt/mutillidae-docker/
    cp --preserve=mode,timestamps -R ${S}/* ${D}${base_prefix}/opt/mutillidae-docker/

	install -d -m 755 ${D}${base_prefix}/opt/mutillidae-docker/prebuilt-containers
	install -o mutillidae ${WORKDIR}/mutillidae.list ${D}${base_prefix}/opt/mutillidae-docker/prebuilt-containers/mutillidae.list
	install -m 644 -o mutillidae ${WORKDIR}/mutillidae.tar.gz.dont-unpack ${D}${base_prefix}/opt/mutillidae-docker/prebuilt-containers/mutillidae.tar.gz
	gunzip ${D}${base_prefix}/opt/mutillidae-docker/prebuilt-containers/mutillidae.tar.gz
	chown -R mutillidae ${D}${base_prefix}/opt/mutillidae-docker

    # install init script to set up database
    install -d ${D}/${sysconfdir}/init.d
    
    install -m 0755 ${WORKDIR}/mutillidae-start-docker ${D}/${sysconfdir}/init.d/
	
	# set up for run at boot
	install -d ${D}/${sysconfdir}/rc5.d
	ln -s -r ${D}/${sysconfdir}/init.d/mutillidae-start-docker ${D}/${sysconfdir}/rc5.d/mutillidae-start-docker 
}

FILES:${PN} = "${base_prefix}/opt/* \
               /etc/init.d/mutillidae-start-docker \
			   /etc/rc5.d/mutillidae-start-docker"

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
