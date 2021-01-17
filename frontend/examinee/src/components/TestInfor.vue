<template>
  <div class="col-sm-4">
    <timer :timeInSecond="timeInSecond" v-if="!testSubmitted && timeInSecond != 0"></timer>
    <result :totalQuestions="totalQuestions" v-if="testSubmitted && totalQuestions != 0"></result>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import Timer from "./Timer.vue";
import Result from "./Result.vue";

export default {
  data() {
    return {
      timeInSecond: 0,
      totalQuestions: 0
    };
  },
  components: {
    timer: Timer,
    result: Result
  },
  computed: {
    ...mapGetters({
      loggedIn: "getLoggedIn",
      testProgress: "getTestProgress",
      testSubmitted: "getTestSubmitted"
    })
  },
  created() {
    if (!this.loggedIn) {
      return;
    }
    let testId = this.$route.params.testId;
    this.initData(testId);
  },
  methods: {
      initData(testId) {
        if (this.$route.name == "test" || this.$route.name == "test-achievement" && testId != null) {
          axios.get(`${process.env.API_URL}/test-information/${testId}`).then(response => {
            console.log(response.data);
            if(this.$route.params.achievementId == null) {
              this.timeInSecond = response.data.timeInSecond;
            }
            this.totalQuestions = response.data.totalQuestions;
          });
        } else {
          this.timeInSecond = 0;
          this.totalQuestions = 0;
        }
      }
  },
  watch: {
    $route (to, from){
      let testId = to.params.testId;
      this.initData(testId);
    }
  }
};
</script>
