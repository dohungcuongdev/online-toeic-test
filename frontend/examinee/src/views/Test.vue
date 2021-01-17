<template>
  <div class="container">
    <div class="row" v-if="testData.audioLink != null">
      <div class="col-sm-2"></div>
      <div class="col-sm-8">
        <audio controls :style="audioStyle" id="listeningAudio">
          <source
            :src="testData.audioLink"
            type="audio/mp3"
          />Your browser does not support the audio element.
        </audio>
      </div>
    </div>
    <keep-alive>
      <part :part="1" :partData="testData.parts['1']" v-if="testProgress==1 && testData.parts['1'] != null"></part>
      <part :part="2" :partData="testData.parts['2']" v-if="testProgress==2 && testData.parts['2'] != null"></part>
      <part :part="3" :partData="testData.parts['3']" v-if="testProgress==3 && testData.parts['3'] != null"></part>
      <part :part="4" :partData="testData.parts['4']" v-if="testProgress==4 && testData.parts['4'] != null"></part>
      <part :part="5" :partData="testData.parts['5']" v-if="testProgress==5 && testData.parts['5'] != null"></part>
      <part :part="6" :partData="testData.parts['6']" v-if="testProgress==6 && testData.parts['6'] != null"></part>
      <part :part="7" :partData="testData.parts['7']" v-if="testProgress==7 && testData.parts['7'] != null"></part>
    </keep-alive>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import Part from "../components/Part.vue";

export default {
  components: {
    part: Part
  },
  data() {
    return {
      testData: {
        parts: {}
      },//declare an object
      audioStyle: ''//declare a string
    };
  },
  computed: {
    ...mapGetters({
      loggedIn: "getLoggedIn",//use getter to get state.
      testProgress: "getTestProgress",
      testSubmitted: "getTestSubmitted",
      playAudioFlag: "getPlayAudioFlag"
    })
  },
  created() {
    if (!this.loggedIn) {
      this.$router.push("/login");
    } else {
      this.$store.dispatch("updateTestProgress", 1);//call action - parameter = 1
      this.$store.dispatch("updatePlayAudioFlag", true);
      if(this.$route.params.achievementId != null) {//achievement
        this.audioStyle = 'width: 100%';
        axios.get(`${process.env.API_URL}/generate-test-achievement/${this.$route.params.achievementId}`).then(response => {
          this.testData = response.data;
          console.log(this.testData);
          this.storeAllAnswers();
          this.$store.dispatch("updateSelectedOptions", this.testData.examineeSelectedOptions);
          this.$store.dispatch("updateTestSubmitted", true);
        });
      } else { //Test
        this.audioStyle = 'display: none';
        axios.get(`${process.env.API_URL}/generate-test/${this.$route.params.testId}`).then(response => {
          this.testData = response.data;
          console.log(this.testData);
          this.storeAllAnswers();
        });
      }
    }
  },
  updated() {
    if(this.$route.params.achievementId == null && !this.testSubmitted && this.playAudioFlag) {
      let playPromise = document.getElementById("listeningAudio").play();
      var vm = this;
      if (playPromise !== undefined) {
        playPromise.then(function() {
          // Automatic playback started!
        }).catch(function(error) {
          vm.$router.go(0);
        });
      }
      // test && not submitted -> playAudio
    }
  },
  watch: {
    '$route' (to, from) {
      console.log(to)
      console.log(from)
      if(to.name == from.name && to.params.testId != from.params.testId || to.params.achievementId != from.params.achievementId) {
        this.$router.go(0);
      }
    }
  },
  beforeRouteLeave(to, from, next) {
    if (this.loggedIn) {
      if(from.params.achievementId != null) {
        this.reset();
        next();
      } else {
        const confirm = window.confirm("Do you really want to leave?");
        if (confirm) {
          this.reset();
          next();
        } else {
          next(false);
        }
      }
    } else {
      next();
    }
  },
  // If it is achievement user can move, if in the test ask before exit
  methods: {
    reset() {
      this.$store.dispatch("updateTestProgress", 0);
      this.$store.dispatch("updateTestSubmitted", false);
      this.$store.dispatch("updateSelectedOptions", []);
      this.$store.dispatch("updateAnswers", []);
      this.$store.dispatch("updateExamineeAnswers", []);
    },
    storeAllAnswers() {
        this.$store.dispatch("updateAnswers", []);
        //clear answers
        this.$store.dispatch("addAnswersFromQuestions", this.testData.parts['1'].questions);
        this.$store.dispatch("addAnswersFromQuestions", this.testData.parts['2'].questions);
        this.$store.dispatch("addAnswersFromQuestions", this.testData.parts['3'].questions);
        this.$store.dispatch("addAnswersFromQuestions", this.testData.parts['4'].questions);
        this.$store.dispatch("addAnswersFromQuestions", this.testData.parts['5'].questions);
        this.$store.dispatch("addAnswersFromQuestionGroups", this.testData.parts['6'].questionGroups);
        this.$store.dispatch("addAnswersFromQuestionGroups", this.testData.parts['7'].questionGroups);
    }
  }
};
</script>
