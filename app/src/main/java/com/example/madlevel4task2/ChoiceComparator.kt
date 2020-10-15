package com.example.madlevel4task2

class ChoiceComparator private constructor() : Comparator<Choice> {
    override fun compare(o1: Choice, o2: Choice): Int {
        return when(o1) {
            Choice.ROCK -> when (o2) {
                Choice.ROCK -> 0
                Choice.PAPER -> -1
                Choice.SCISSORS -> 1
            }
            Choice.PAPER -> when (o2) {
                Choice.ROCK -> 1
                Choice.PAPER -> 0
                Choice.SCISSORS -> -1
            }
            Choice.SCISSORS -> when (o2) {
                Choice.ROCK -> -1
                Choice.PAPER -> 1
                Choice.SCISSORS -> 0
            }
        }
    }

    companion object {
        val instance: ChoiceComparator by lazy { ChoiceComparator() }
    }
}