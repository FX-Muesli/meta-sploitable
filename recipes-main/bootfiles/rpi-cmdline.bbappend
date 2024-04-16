# SPDX-FileCopyrightText: 2024 Felix Mues <felix.mues@tu-dortmund.de>
# SPDX-License-Identifier: MIT

# load old cmdline, trim the already existing newline, add selinux parameters and override
do_compile:append() {
    echo -n "$(tr -d '\n' < ${WORKDIR}/cmdline.txt)"' selinux=1 enforcing=0' > "${WORKDIR}/cmdline.txt"
}