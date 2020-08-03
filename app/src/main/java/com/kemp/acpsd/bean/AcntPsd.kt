package com.kemp.acpsd.bean

data class AcntPsd(var name: String, var account: String, var psd: String) {

    constructor(name: String, account: String, psd: String, mark: String) : this(name, account, psd)
}