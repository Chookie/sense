package com.chookie.sense.client.mood

import spock.lang.Specification

import spock.lang.Ignore

import static com.chookie.sense.client.mood.Mood.HAPPY
import static com.chookie.sense.client.mood.Mood.SAD

class MoodChartDataSpec extends Specification {
    @Ignore
    def 'should increment happy slice'() {
        given:
        def moodChartData = new MoodChartData()

        when:
        moodChartData.onMessage(new TweetMood([HAPPY] as Set))

        then:
        moodChartData.happyPortion.pieChartData.pieValue == 1
    }

    @Ignore
    def 'should increment sad slice'() {
        given:
        def moodChartData = new MoodChartData()

        when:
        moodChartData.onMessage(new TweetMood([SAD] as Set))

        then:
        moodChartData.sadPortion.pieChartData.pieValue == 1
    }

    @Ignore
    def 'should increment all slices if all moods present'() {
        given:
        def moodChartData = new MoodChartData()

        when:
        moodChartData.onMessage(new TweetMood([HAPPY, SAD] as Set))

        then:
        moodChartData.happyPortion.pieChartData.pieValue == 1
        moodChartData.sadPortion.pieChartData.pieValue == 1
        moodChartData.confusedPortion.pieChartData.pieValue == 1
    }

}