package com.itechpro.compressor

class Compression {
    internal val constraints: MutableList<Constraint> = mutableListOf()

    fun constraint(constraint: Constraint) {
        constraints.add(constraint)
    }
}