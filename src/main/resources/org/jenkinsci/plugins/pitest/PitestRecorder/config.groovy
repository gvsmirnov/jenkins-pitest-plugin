package org.jenkinsci.plugins.pitest.PitestRecorder

namespace(lib.FormTagLib).with {
    entry(title: 'Path to pitest reports', field: 'reportsGlob') {
        textbox(value: h.defaulted(instance?.reportsGlob, "build/reports/pitest/index.html"))
    }
}