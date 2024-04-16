# SPDX-FileCopyrightText: 2024 Felix Mues <felix.mues@tu-dortmund.de>
# SPDX-License-Identifier: MIT

do_install:append () {
    rm ${D}${sysconfdir}/network/interfaces
}